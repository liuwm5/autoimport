//package com.liuweimin.adper.autoimport.config;
//
//import com.google.common.cache.CacheBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.guava.GuavaCache;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * @author liuwm
// * @description
// * @date 7/8/2019 12:00 PM
// *
// */
//@Configuration
//@EnableCaching
//public class GuavaCacheConfig {
//    /**
//     * cache key
//     */
//    public static final String HOTEL_POSTION = "hotel_position";
//    @Value("${cache.guavaCache.hotelPosition.maxSize}")
//    private long hotelPositionMaxSize;
//    @Value("${cache.guavaCache.hotelPosition.duration}")
//    private long hotelPositionDuration;
//
//    @Bean
//    public GuavaCache buildHotelPositionCache() {
//        return new GuavaCache(HOTEL_POSTION,
//                CacheBuilder.newBuilder()
//                        .recordStats()
//                        .maximumSize(hotelPositionMaxSize)
//                        .expireAfterWrite(hotelPositionDuration, TimeUnit.DAYS)
//                        .build());
//    }
//
//
//}