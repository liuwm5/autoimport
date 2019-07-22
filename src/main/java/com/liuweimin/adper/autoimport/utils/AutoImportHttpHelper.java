package com.liuweimin.adper.autoimport.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Splitter;
import com.liuweimin.adper.autoimport.constants.DperApiConstants;
import com.liuweimin.adper.autoimport.entity.KeyResult;
import com.liuweimin.adper.autoimport.enums.ExceptionEnum;
import com.liuweimin.adper.autoimport.exception.BizException;
import com.liuweimin.adper.autoimport.remote.domain.importToPrivate.request.ImportToPrivateBean;
import com.liuweimin.adper.autoimport.remote.domain.shopSearchQuery.request.Params;
import com.liuweimin.adper.autoimport.remote.domain.shopSearchQuery.request.ShopSearchQueryBean;
import com.liuweimin.adper.autoimport.remote.domain.shopSearchQuery.response.Buttons;
import com.liuweimin.adper.autoimport.remote.domain.shopSearchQuery.response.Items;
import com.liuweimin.adper.autoimport.remote.domain.shopSearchQuery.response.ShopSearchQueryRspBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author liuwm
 * @description 请求点评服务
 * @date 7/8/2019 12:51 PM
 */
@Slf4j
@Component
public class AutoImportHttpHelper {
    @Value("${dper.cookies}")
    private String cookies;
    public static List<String> shopNameKeys = new ArrayList<>();

    static {
        log.info("使用默认关键字:" + DperApiConstants.default_keys);
        shopNameKeys = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(DperApiConstants.default_keys);
    }

    public static ShopSearchQueryBean buildShopSearchQueryBean() {
        return buildShopSearchQueryBean(null);
    }

    public static ShopSearchQueryBean buildShopSearchQueryBean(String shopId) {
        ShopSearchQueryBean shopSearchQueryBean = new ShopSearchQueryBean();
        shopSearchQueryBean.setType("ShopSearch");
        shopSearchQueryBean.setCategory("query2");
        shopSearchQueryBean.setAs("result");
        shopSearchQueryBean.setChildren(new ArrayList<>());
        Params params = new Params();
        params.setMainCategory(-1);
        params.setCategory(new ArrayList<>());
        params.setMainRegion(-1);
        params.setRegion(new ArrayList<>());
        params.setOwnerType(0);
        params.setCity(1);
        params.setSortBy(-1);
        params.setPageIndex(1);
        params.setPageSize(3);
        List<String> shopStatus = new ArrayList<>();
        shopStatus.add("newshop");
        params.setShopStatus(shopStatus);
        shopSearchQueryBean.setParams(params);
        shopSearchQueryBean.setKeyword(shopId);
        return shopSearchQueryBean;
    }

    public static ImportToPrivateBean buildImportToPrivateBean(long shopId) {
        ImportToPrivateBean bean = new ImportToPrivateBean();
        bean.setAs("result");
        bean.setCategory("importToPrivate");
        bean.setType("Shop");
        com.liuweimin.adper.autoimport.remote.domain.importToPrivate.request.Params params = new com.liuweimin.adper.autoimport.remote.domain.importToPrivate.request.Params();
        params.setShopId(shopId);
        bean.setParams(params);
        return bean;
    }

    public String importToPrivate(long shopId) throws BizException, Exception {
        Map<String, String> headers = buildDefaultHeaders();
        List<ImportToPrivateBean> importToPrivateBeans = new ArrayList<>();
        importToPrivateBeans.add(AutoImportHttpHelper.buildImportToPrivateBean(shopId));
        HttpClientResult result = HttpClientUtils.doJsonStrPost(DperApiConstants.importPreate, headers, JSONUtil.toJsonStr(importToPrivateBeans));
        if (result == null) {
            throw new BizException(ExceptionEnum.httpException, "返回结果为空，请检查响应报文");
        }
        if (result.getCode() != 200) {
            throw new BizException(ExceptionEnum.httpException, "调用接口异常");
        }
        return result.getContent();
    }

    public String getShopSearch() throws Exception {
        return getShopSearch(null);
    }

    public String getShopSearch(String shopId) throws Exception {
        Map<String, String> headers = buildDefaultHeaders();
        List<ShopSearchQueryBean> shopSearchQueryBeans = new ArrayList<>();
        shopSearchQueryBeans.add(AutoImportHttpHelper.buildShopSearchQueryBean(shopId));
        String requestBody = JSONUtil.toJsonStr(shopSearchQueryBeans);
        log.info("请求参数为:" + requestBody);
        HttpClientResult result = HttpClientUtils.doJsonStrPost(DperApiConstants.ShopSearch, headers, requestBody);
        if (result == null) {
            throw new BizException(ExceptionEnum.httpException, "返回结果为空，请检查响应报文");
        }
        if (result.getCode() != 200) {
            log.info("返回结果为:" + JSONUtil.toJsonStr(result));
            throw new BizException(ExceptionEnum.httpException, "调用接口异常" + JSONUtil.toJsonStr(result));
        }
        return result.getContent();
    }

    public KeyResult getShopSearchRspBean() throws Exception {
        JSONObject jsonObject = JSONUtil.parseObj(getShopSearch());

        Object data = jsonObject.get("data");
        if (data instanceof JSONObject) {
            JSONObject jsonObject1 = (JSONObject) data;
            if (StringUtils.equals(jsonObject1.getStr("result"), DperApiConstants.limitMsg)) {
                throw new BizException(ExceptionEnum.limitException, jsonObject1.getStr("result"));
            }
        }

        ShopSearchQueryRspBean shopSearchQueryRspBean = JSONUtil.toBean(getShopSearch(), ShopSearchQueryRspBean.class);
        if (shopSearchQueryRspBean == null) {
            throw new BizException(ExceptionEnum.httpException, "应答报文为空，请检查");
        }
        if (shopSearchQueryRspBean.getCode() != 200) {
            throw new BizException(ExceptionEnum.httpException, "调用接口异常");
        }
        List<Items> items = shopSearchQueryRspBean.getData().getResult().getItems();
        if (CollUtil.isEmpty(items)) {
            log.info("返回查询列表为空，请稍后重试");
            throw new BizException(ExceptionEnum.noData, "调用接口异常");
        }
        for (Items item : items) {
            log.info("本次处理店铺类型为:" + JSONUtil.toJsonStr(item.getBackerCategories()) +
                    " 店铺名称为:" + item.getShopName() +
                    " buttons为:" + JSONUtil.toJsonStr(item.getButtons()) +
                    " 店铺状态为:" + item.getShopPoiStatus() +
                    " 是否冻结:" + item.getIsFreeze());
            String shopName = item.getShopName();
            if (StringUtils.isBlank(shopName)) {
                log.info("店铺结果为空略过 店铺名：" + shopName + "：店铺id:" + item.getShopId());
                continue;
            }
            List<Buttons> buttons = item.getButtons();
            if (CollectionUtils.isEmpty(buttons)) {
                log.info("导出按钮为空，略过 店铺名：" + shopName + "：店铺id:" + item.getShopId());
                continue;
            }
            boolean isHaveImport = false;
            for (Buttons button : buttons) {
                if (StringUtils.equals(button.getName(), "import")) {
                    isHaveImport = true;
                    break;
                }
            }
            if (!isHaveImport) {
                log.info("遍历本条记录，未匹配到import按钮，略过 店铺名：" + shopName + "：店铺id:" + item.getShopId());
                continue;
            }
//            if (!"4".equals(item.getShopPoiStatus())) {
//                log.info("店铺状态非正常状态，不进行导入,店铺名：" + shopName + "：店铺id:" + item.getShopId());
//                continue;
//            }
            KeyResult keyResult = getKeyResult(item, shopName);
            if (keyResult == null) {
                continue;
            }
            return keyResult;
        }
        return null;
    }

    private static KeyResult getKeyResult(Items item, String shopName) {
        KeyResult keyResult = new KeyResult();

        boolean categoryMatch = false;
        for (String backerCategory : DperApiConstants.backerCategories) {
            if (!CollectionUtils.isEmpty(item.getBackerCategories())) {
                for (String category : item.getBackerCategories()) {
                    if (StringUtils.equals(category, backerCategory)) {
                        log.info("行业批次成功,行业为:" + category);
                        categoryMatch = true;
                        break;
                    }
                }
            }
        }
        if (categoryMatch) {
            log.info("行业匹配成功,直接放行通过");
            keyResult.setShopId(String.valueOf(item.getShopId()));
            keyResult.setShopName(shopName);
            return keyResult;
        }
        boolean isHaveKey = false;
        for (String shopNameKey : shopNameKeys) {
            if (StringUtils.contains(shopName, shopNameKey)) {
                log.info("关键字匹配成功，关键字为：" + shopNameKey + ",本次匹配到店名为：" + shopName);
                keyResult.setShopId(String.valueOf(item.getShopId()));
                keyResult.setShopName(shopName);
                isHaveKey = true;
                continue;
            }
        }
        if (!isHaveKey) {
            log.info("本条未能匹配到关键字,店铺名：" + shopName + "：店铺id:" + item.getShopId());
            return null;
        }
        return keyResult;
    }


    private Map<String, String> buildDefaultHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", cookies);
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/json");
        headers.put("Host", "a.dper.com");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        return headers;
    }

    public static void main(String args[]) throws Exception {
        String shopSearch = new AutoImportHttpHelper().getShopSearch("132853790");
        System.out.println(shopSearch);
//        String importToPrivate = AutoImportHttpHelper.importToPrivate(Integer.parseInt("132852845"));
//        System.out.println(importToPrivate);

    }

}
