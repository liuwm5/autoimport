package com.liuweimin.adper.autoimport.utils;

import java.util.HashMap;
import java.util.Map;

public class TestUtils {
    /**
     * Description: 测试post带请求头不带请求参数
     *
     * @throws Exception
     */
    @org.junit.Test
    public void testPost() throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", "_lxsdk_cuid=16af981c54ac8-09204a9d7ff023-12336d51-fa000-16af981c54bc8; _lxsdk=16af981c54ac8-09204a9d7ff023-12336d51-fa000-16af981c54bc8; _hc.v=d5860800-5e38-5a8b-f426-8f18b77910f1.1558964193; apollo-safe-dot-service_ssoid=eAHjYBSYunomo8LnnvYde3WNxBML8nNy8nWLE9NSdVPyS3SLU4vKMpNTrRRMTVOMTI0MLZMNLIxMzMyNLE3MzbUsLFINExPTTM3STJwMFE59nvf3gK4RQaUWIBsdmKIUzC1MjFJMjI0sErUMTRLNEhNNzBItLFNTjM2SUkwTk8wAgt8vog**eAEFwQkBwEAIAzBNwI5HDgXqX8KSUbM8zvLWqWH3vV11x6xYWTmdxchoQZB6fQAkNV5n4gdmHRLE; ssoid=eAHjYBSYunomo8LnnvYde3WNxBML8nNy8nWLE9NSdVPyS3SLU4vKMpNTrRRMTVOMTI0MLZMNLIxMzMyNLE3MzbUsLFINExPTTM3STJwMFE59nvf3gK4RQaUWIBsdmKIUzC1MjFJMjI0sErUMTRLNEhNNzBItLFNTjM2SUkwTk8wAgt8vog**eAEFwQkBwEAIAzBNwI5HDgXqX8KSUbM8zvLWqWH3vV11x6xYWTmdxchoQZB6fQAkNV5n4gdmHRLE; catBrowserName=catBrowserValue; _lxsdk_cuid_apollo=16af981c54ac8-09204a9d7ff023-12336d51-fa000-16af981c54bc8; _lxsdk_apollo=16af981c54ac8-09204a9d7ff023-12336d51-fa000-16af981c54bc8; _hc.v_apollo=d5860800-5e38-5a8b-f426-8f18b77910f1.1558964193; ssoid_apollo=eAHjYBSYunomo8LnnvYde3WNxBML8nNy8nWLE9NSdVPyS3SLU4vKMpNTrRRMTVOMTI0MLZMNLIxMzMyNLE3MzbUsLFINExPTTM3STJwMFE59nvf3gK4RQaUWIBsdmKIUzC1MjFJMjI0sErUMTRLNEhNNzBItLFNTjM2SUkwTk8wAgt8vog**eAEFwQkBwEAIAzBNwI5HDgXqX8KSUbM8zvLWqWH3vV11x6xYWTmdxchoQZB6fQAkNV5n4gdmHRLE; catBrowserName_apollo=catBrowserValue; JSESSIONID=E8517282192BDA1A29741087201F2BF3; JSESSIONID_apollo=E8517282192BDA1A29741087201F2BF3; _lxsdk_s=16bcb71e50f-6a5-678-d16%7C2512277%7C19; _lxsdk_s_apollo=16bcb71e50f-6a5-678-d16%7C2512277%7C19");
        headers.put("Connection", "keep-alive");
        headers.put("Content-Type", "application/json");
        headers.put("Host","a.dper.com");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
       String jsonStr ="[{\"type\":\"ShopSearch\",\"category\":\"query2\",\"params\":{\"mainCategory\":-1,\"category\":[],\"mainRegion\":-1,\"region\":[],\"ownerType\":0,\"city\":1,\"sortBy\":-1,\"pageIndex\":1,\"pageSize\":10},\"as\":\"result\",\"children\":[]}]";
        HttpClientResult result = HttpClientUtils.doJsonStrPost("https://a.dper.com/shop/__cascade__/ShopSearch.query2", headers, jsonStr);
        System.out.println("应答报文是："+result);
    }

}
