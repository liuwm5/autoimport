package com.liuweimin.adper.autoimport.constants;

/**
 * @author liuwm
 * @description 邮件通知内容
 * @date 7/8/2019 5:26 PM
 */
public class MailConstants {
    /**
     * 邮件主题模板
     */
    public static String MAIL_CONTENT_TEMPLATE = "导入店铺成功,店铺名为:%{shopName}，店铺ID为:%{shopId},导入时间为:%{now}。导入前店铺信息为: %{item}";
    /**
     * 邮件内容模板
     */
    public static String MAIL_SUBJECT_TEMPLATE = "导入店铺成功,店铺名为:%{shopName}，店铺ID为:%{shopId}";
    /**
     * 错误邮件通知
     */
    public static String ERR_MAIL_CONTENT_TEMPLATE = "自动导入店铺异常,请检查日志";

    /**
     *
     */
    public static String ERR_MAIL_SUBJECT_TEMPLATE = "自动导入店铺异常,请检查日志,错误日志为:%{errorMsg}";
}
