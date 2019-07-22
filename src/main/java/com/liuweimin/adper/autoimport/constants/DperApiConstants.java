package com.liuweimin.adper.autoimport.constants;

import com.google.common.base.Splitter;

import java.util.List;

/**
 * @author liuwm
 * @description
 * @date 7/8/2019 2:09 PM
 */
public class DperApiConstants {
    /**
     * 查询服务接口
     */
    public static final String domain = "https://a.dper.com";
    public static final String service_ssoid = "service_ssoid";
    public static final String service_ssoid_value = "eAHjYBSYunomo8LnnvYde3WNxBML8nNy8nWLE9NSdVPyS3SLU4vKMpNTrRRMTVOMTI0MLZMNLIxMzMyNLE3MzbUsLFINExPTTM3STJwMFE59nvf3gK4RQaUWIBsdmKIUzC1MjFJMjI0sErUMTRLNEhNNzBItLFNTjM2SUkwTk8wAgt8vog**eAEFwQkBwEAIAzBNwI5HDgXqX8KSUbM8zvLWqWH3vV11x6xYWTmdxchoQZB6fQAkNV5n4gdmHRLE";
    public static final String ShopSearch = "https://a.dper.com/shop/__cascade__/ShopSearch.query2";

    /**
     * 导入私海接口
     */
    public static final String importPreate = "https://a.dper.com/shop/__cascade__/Shop.importToPrivate";
    /**
     * 释放私海接口
     */
    public static final String shopRelease = "https://a.dper.com/shop/__cascade__/Shop.release";
    /**
     * 私海剩余总数接口
     */
    public static final String privateConfig = "https://a.dper.com/shop/cascade/TableField.queryConfig__ShopSearch.queryForPrivate__ShopSearchFilter.dynamic";

    public static final String limitMsg = "[Cascade Error] 您访问过于频繁，请稍后访问！";

    public static final List<String> backerCategories = Splitter.on(",").omitEmptyStrings().trimResults()
            .splitToList("宠物,其他宠物");
    /**
     * 默认关键字
     */
    public static final String default_keys = "花行水族馆,宠物,健身,足疗,酒吧,茶馆,DIY,密室,洗衣,吃鸡,宠屋,游泳,团建";

}
