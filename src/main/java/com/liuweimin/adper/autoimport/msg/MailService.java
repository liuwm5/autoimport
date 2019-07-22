package com.liuweimin.adper.autoimport.msg;

/**
 * @author liuwm
 * @description
 * @date 7/8/2019 5:02 PM
 */
public interface MailService {
    /**
     * 短信发送服务
     *
     * @param subject 主题
     * @param content 内容
     * @param to      目标地址,可以为数组
     */
    public void sendTextMailService(String subject, String content, String... to);

}
