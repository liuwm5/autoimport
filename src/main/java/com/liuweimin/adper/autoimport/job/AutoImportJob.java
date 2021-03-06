package com.liuweimin.adper.autoimport.job;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.liuweimin.adper.autoimport.ShutdownManager;
import com.liuweimin.adper.autoimport.config.CacheManager;
import com.liuweimin.adper.autoimport.constants.MailConstants;
import com.liuweimin.adper.autoimport.enums.ExceptionEnum;
import com.liuweimin.adper.autoimport.exception.BizException;
import com.liuweimin.adper.autoimport.msg.MailService;
import com.liuweimin.adper.autoimport.remote.domain.shopSearchQuery.response.Items;
import com.liuweimin.adper.autoimport.service.AutoImportService;
import com.liuweimin.adper.autoimport.utils.AutoImportHttpHelper;
import com.liuweimin.adper.autoimport.utils.TraceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author liuwm
 * @description 自动导入功能 定时任务
 * @date 7/8/2019 11:26 AM
 */
@Slf4j
@Component
public class AutoImportJob extends QuartzJobBean {
    @Autowired
    MailService mailService;
    @Autowired
    AutoImportService autoImportService;
    @Autowired
    AutoImportHttpHelper autoImportHttpHelper;
    @Autowired
    ShutdownManager shutdownManager;


    @Value("#{'${target.mail_addr}'.split(',')}")
    List<String> addrs;
    @Value("#{'${error.notify.target.mail_addr}'.split(',')}")
    List<String> errorNotifyAddrs;
    public static AtomicInteger count = new AtomicInteger(0);
    public static AtomicInteger queryCount = new AtomicInteger(0);


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始处理扫描店铺情况");
        try {
            String expire = CacheManager.getExpire(ExceptionEnum.limitException.name());
            if (StringUtils.isNotBlank(expire)) {
                log.warn("限流前共执行了扫描次数:" + count.get());
                log.warn("限流了,还在休眠时间,请稍后重试,时间为:" + expire);
                count = new AtomicInteger(0);
                return;
            }
            Items item = autoImportService.getShopSearchRspBean();
            count.getAndIncrement();
            queryCount.getAndIncrement();
            if (item == null) {
                log.info("本次查询未能匹配到待导入数据,任务共查询次数为:" + queryCount.get());
                return;
            }
            TraceUtil.setCorrelationID(JSONUtil.toJsonPrettyStr(item));
            log.warn("匹配成功,开始进行导入处理,到导入数据为:" + JSONUtil.toJsonStr(item) + "任务共查询次数为:" + queryCount.get());
            String importToPrivateRes = autoImportHttpHelper.importToPrivate(item.getShopId());
            log.warn("导入结果为:" + importToPrivateRes);
            autoImportService.processImportToPrivate(importToPrivateRes);
            sendSuccessEmail(item);
        } catch (BizException ex) {
            if (ex.getCode().equals(ExceptionEnum.limitException)) {
                log.warn("查询频繁异常，休眠1分钟");
                CacheManager.setData(ExceptionEnum.limitException.name(), ExceptionEnum.limitException, 60);
            }
            log.warn("处理时,发生异常,异常原因为:" + JSONUtil.toJsonStr(ex));

            if (ex.getCode().equals(ExceptionEnum.httpException)) {
                sendErrorEmail(ex.getMessage());
                log.warn("停止服务");
                shutdownManager.initiateShutdown(0);
            }
            if (ex.getCode().equals(ExceptionEnum.importException)) {
                sendErrorEmail(ex.getMessage() + "未导入成功店铺为:" + TraceUtil.getCorrelationID());
            }

        } catch (Exception e) {
            log.warn("异常原因：" + e.getLocalizedMessage());
        } finally {
            TraceUtil.clear();
        }
    }

    private void sendSuccessEmail(Items item) {
        log.info("恭喜成功导入店铺,店铺信息为:" + JSONUtil.toJsonStr(item));
        String subject = StringUtils.replace(MailConstants.MAIL_SUBJECT_TEMPLATE, "%{shopName}", item.getShopName())
                .replace("%{shopId}", String.valueOf(item.getShopId()));
        String content = StringUtils.replace(MailConstants.MAIL_CONTENT_TEMPLATE, "%{shopName}", item.getShopName())
                .replace("%{shopId}", String.valueOf(item.getShopId()))
                .replace("%{now}", DateUtil.now())
                .replace("%{item}", JSONUtil.toJsonPrettyStr(item));
        mailService.sendTextMailService(subject, content, addrs.stream().toArray(String[]::new));
    }

    private void sendErrorEmail(String errorMsg) {
        String content = StringUtils.replace(MailConstants.ERR_MAIL_SUBJECT_TEMPLATE, "%{errorMsg}", errorMsg);
        mailService.sendTextMailService(MailConstants.ERR_MAIL_CONTENT_TEMPLATE, content, errorNotifyAddrs.stream().toArray(String[]::new));
    }
}
