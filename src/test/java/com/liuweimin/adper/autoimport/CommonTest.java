package com.liuweimin.adper.autoimport;

import cn.hutool.core.util.RandomUtil;
import org.junit.Test;

public class CommonTest {
    @Test
    public void test(){
        for (int i = 0; i < 100; i++) {
            System.out.println(1000+ RandomUtil.randomInt(100,200));
        }
    }

}
