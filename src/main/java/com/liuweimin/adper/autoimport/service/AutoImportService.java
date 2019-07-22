package com.liuweimin.adper.autoimport.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.liuweimin.adper.autoimport.constants.DperApiConstants;
import com.liuweimin.adper.autoimport.entity.KeyResult;
import com.liuweimin.adper.autoimport.enums.ExceptionEnum;
import com.liuweimin.adper.autoimport.exception.BizException;
import com.liuweimin.adper.autoimport.remote.domain.importToPrivate.response.ImportToPrivateRspBean;
import com.liuweimin.adper.autoimport.remote.domain.shopSearchQuery.response.Buttons;
import com.liuweimin.adper.autoimport.remote.domain.shopSearchQuery.response.Items;
import com.liuweimin.adper.autoimport.remote.domain.shopSearchQuery.response.ShopSearchQueryRspBean;
import com.liuweimin.adper.autoimport.utils.AutoImportHttpHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @Author liuwm
 * @Description  导入服务
 * @Date 18:43 2019-07-08
 **/
@Service
@Data
@Slf4j
public class AutoImportService {
    @Value("#{'${import.shopName.keys}'.split(',')}")
    public List<String> shopNameKeys;
    @Value("#{'${import.backerCategorie.keys}'.split(',')}")
    public List<String> backerCategories;
    @Autowired
    private AutoImportHttpHelper autoImportHttpHelper;

    private static Map<String, String> backerCategorieMaps;

    /**
     * 获取店铺信息
     *
     * @return
     * @throws Exception
     */
    public Items getShopSearchRspBean() throws Exception {
        String shopSearch = autoImportHttpHelper.getShopSearch();
        JSONObject jsonObject = JSONUtil.parseObj(shopSearch);
        checkResult(jsonObject);

        ShopSearchQueryRspBean shopSearchQueryRspBean = JSONUtil.toBean(shopSearch, ShopSearchQueryRspBean.class);
        if (shopSearchQueryRspBean == null) {
            throw new BizException(ExceptionEnum.httpException, "应答报文为空，请检查");
        }
        if (shopSearchQueryRspBean.getCode() != 200) {
            log.info("返回结果为:" + shopSearch);
            throw new BizException(ExceptionEnum.httpException, shopSearch);
        }
        List<Items> items = shopSearchQueryRspBean.getData().getResult().getItems();
        if (CollUtil.isEmpty(items)) {
            log.info("返回查询列表为空，请稍后重试");
            throw new BizException(ExceptionEnum.noData, "调用接口异常");
        }
        for (Items item : items) {
            log.info("本次处理店铺类型为:" + JSONUtil.toJsonStr(item.getBackerCategories()) +
                    "店铺名称为:" + item.getShopName() +
                    "buttons为:" + JSONUtil.toJsonStr(item.getButtons()) +
                    "店铺状态为:" + item.getShopPoiStatus() +
                    "是否冻结:" + item.getIsFreeze());
            String shopName = item.getShopName();
            if (StringUtils.isBlank(shopName)) {
                log.info("店铺结果为空略过店铺名：" + shopName + "：店铺id:" + item.getShopId());
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
            //4状态应该是尚未营业,之前搞错了,5是正常状态
            if (4 == item.getShopPoiStatus()) {
                log.info("店铺状态尚未开业，不进行导入,店铺名：" + shopName + "：店铺id:" + item.getShopId());
                continue;
            }
            if (item.getIsFreeze()) {
                log.info("店铺状态是冻结状态，不进行导入,店铺名：" + shopName + "：店铺id:" + item.getShopId());
                continue;
            }

            item = getKeyResult(item, shopName);
            if (item == null) {
                continue;
            }
            return item;
            //todo 登记每天新店条数,记录新店分部情况用户分类.
        }
        return null;
    }

    public ImportToPrivateRspBean processImportToPrivate(String response) {
        JSONObject jsonObject = JSONUtil.parseObj(response);
        checkResult(jsonObject);
        ImportToPrivateRspBean importToPrivateRspBean = JSONUtil.toBean(response, ImportToPrivateRspBean.class);
        if (importToPrivateRspBean == null || importToPrivateRspBean.getCode() != 200) {
            throw new BizException(ExceptionEnum.httpException, "调用接口异常");
        }

        if (importToPrivateRspBean.getData() == null || importToPrivateRspBean.getData().getResult() != true) {
            throw new BizException(ExceptionEnum.httpException, "导入店铺时失败");
        }
        return importToPrivateRspBean;

    }

    private void checkResult(JSONObject jsonObject) {
        Object data = jsonObject.get("data");
        if (data instanceof JSONObject) {
            JSONObject jsonObject1 = (JSONObject) data;
            if (StringUtils.equals(jsonObject1.getStr("result"), DperApiConstants.limitMsg)) {
                throw new BizException(ExceptionEnum.limitException, jsonObject1.getStr("result"));
            }
            if (StringUtils.equals(jsonObject1.getStr("result"), DperApiConstants.limitMsg)) {
                throw new BizException(ExceptionEnum.limitException, jsonObject1.getStr("result"));
            }
            if (StringUtils.contains(jsonObject1.getStr("result"), "[Cascade Error]")) {
                throw new BizException(ExceptionEnum.importException, jsonObject1.getStr("result"));
            }
        }
    }

    private Items getKeyResult(Items item, String shopName) {
        loadMap();
        KeyResult keyResult = new KeyResult();

        boolean categoryMatch = false;
        if (!CollectionUtils.isEmpty(item.getBackerCategories())) {
            for (String category : item.getBackerCategories()) {
                if (backerCategorieMaps.containsKey(category)) {
                    log.info("行业批次成功,行业为:" + category);
                    categoryMatch = true;
                    break;
                }
            }
        }
        if (categoryMatch) {
            log.info("行业匹配成功,直接放行通过");
            keyResult.setShopId(String.valueOf(item.getShopId()));
            keyResult.setShopName(shopName);
            return item;
        }
        boolean isHaveKey = false;
        for (String shopNameKey : shopNameKeys) {
            if (StringUtils.contains(shopName, shopNameKey)) {
                log.info("关键字匹配成功，关键字为：" + shopNameKey + ",本次匹配到店名为：" + shopName);
                keyResult.setShopId(String.valueOf(item.getShopId()));
                keyResult.setShopName(shopName);
                isHaveKey = true;
                break;
            }
        }
        if (!isHaveKey) {
            log.info("本条未能匹配到关键字,店铺名：" + shopName + "：店铺id:" + item.getShopId());
            return null;
        }
        return item;
    }

    public void loadMap() {
        if (CollectionUtils.isEmpty(backerCategorieMaps)) {
            backerCategorieMaps = new HashMap<>();
            if (!CollectionUtils.isEmpty(backerCategories)) {
                for (String backerCategory : backerCategories) {
                    backerCategorieMaps.put(backerCategory, backerCategory);
                }
            }
        }

    }
}
