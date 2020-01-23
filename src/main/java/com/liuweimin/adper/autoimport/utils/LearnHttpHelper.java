package com.liuweimin.adper.autoimport.utils;

import com.liuweimin.adper.autoimport.constants.LearnConstants;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author liuwm
 * @description 请求学习服务
 * @date 2020/1/24 0:23
 */
@Slf4j
@Component
public class LearnHttpHelper {

    public static void main(String args[]) throws Exception {

    }

    public static void addStudyTime(String courseId, String studyTime, String cookies) {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        String content = "courseId=" + courseId + "&studyTime=" + studyTime;
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url(LearnConstants.ADDSTUDYTIME_URL)
                .post(body)
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Origin", "http://study.teacheredu.cn")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.9")
                .addHeader("Cookie", cookies)
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String msg = response.body().string();
            log.info("应答报文为:" + msg);
            if (StringUtils.equals(msg, "0")) {
                log.info("更新时间失败,原因是你打开多个浏览器同时学习，只能记录第一次学习的时间。");
            } else {
                log.info("学习成功,学习时间为:{}s", studyTime);
            }
        } catch (IOException e) {
            log.info("请求接口报错:{}", e.getMessage());
        }

    }

}
