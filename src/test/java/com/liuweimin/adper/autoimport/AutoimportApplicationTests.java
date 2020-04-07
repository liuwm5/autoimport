package com.liuweimin.adper.autoimport;

import cn.hutool.core.date.DateUtil;
import com.liuweimin.adper.autoimport.constants.MailConstants;
import com.liuweimin.adper.autoimport.msg.MailService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author liuwm
 * @description 单元测试
 * @date 7/8/2019 5:07 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoimportApplicationTests {
    @Autowired
    MailService mailService;

    @Test
    public void testSendTextMail() {
        String subject = StringUtils.replace(MailConstants.MAIL_SUBJECT_TEMPLATE, "%{shopName}", "测试数据")
                .replace("%{shopId}", "测试ID");
        String content = StringUtils.replace(MailConstants.MAIL_CONTENT_TEMPLATE, "%{shopName}", "测试数据")
                .replace("%{shopId}", "测试ID")
                .replace("%{now}", DateUtil.now());
        mailService.sendTextMailService(subject, content, "liuwm@lianlianpay.com");
    }



}
