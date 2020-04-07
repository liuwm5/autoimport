package com.liuweimin.adper.autoimport.job;

import cn.hutool.core.util.RandomUtil;
import com.liuweimin.adper.autoimport.utils.LearnHttpHelper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * @author liuwm
 * @description 自动学习定时任务
 * @date 7/8/2019 11:26 AM
 */
@Slf4j
@Component
public class AutoLearnJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            log.info("开始处理刘杰的自动学习");
            String courseIds = "1496809,1497579,1497581,1497583,1497581,1497581,1497583,1497583,1496801,1496801,1496803,1496803,1496805,1496805,1496807,1496807,1497593," +
                    "1497589,1496793,1496795,1496795,1496797,1496797,1496799,1496799,1496789,1496789";
            String[] split = courseIds.split(",");
            String studyTime =String.valueOf(1000+RandomUtil.randomInt(100,200));
            String cookies = "JSESSIONID=A11B37102168586C40643C7AE8186956; UM_distinctid=16fd0ee97d254-0b6f248d044f64-4c302978-100200-16fd0ee97d51fa; looyu_id=71d40a4838f71e4d6fe393a475f198a4_20003718%3A3; CAS_SID=f2f4cabe-785f-40c4-a336-7bfdaa2a1d44:1579826030387; _const_cas_assertion_id_=7440467; _const_cas_assertion_=19lyxm4981; tmp_uc0=eNrz4A12DQ729PeL9%2FV3cfUxiKjOTLFSCvFx87aI0o308XB2CnB1NHR3MzB08QjxdPN3MdINCDO2CAo09dZ19bMwVNJJLrEyNDW3tDAyMzA0MTGx1ElMRhPIrbAyqI0CAP61G74%3D; JSESSION_ID=TLFK8Z-YLHCBPEA1GF01DHTIFOD2-PV38RQ5K-EN81; looyu_20003718=v%3Ad0a5046fbcf86af87d42c25df6f3cac0%2Cref%3A%2Cr%3A%2Cmon%3Ahttp%3A//vqf5z.talk99.cn/monitor%2Cp0%3Ahttp%253A//study.teacheredu.cn/proj/studentwork/index_new.htm; lastlogin=\"2020-01-24 00:16\"; tmp_pc0=eNrz4A9wjg8tTi2KDyjKz4r3TLGRZOQNdg0O9vT3i%2Ff1d3H1MYiozkyxUgrxcfO2iNKN9PFwdgpwdTR0dzMwdPEI8XTzdzHSDQgztggKNPXWdfWzMFTSSS6xMjQ1t7QwMjMwNDI3MdBJTIYLGBsYW1jo5FZYGdQKA60uANkaGuwaFO%2FpEu%2BdWhnJwKd6li8gyN8r3sXTOSQkMsA1uMrZQDU5T68kNTE5I7UoNaVUrzhZLyUzuUQvJT83MTNPzwXILqksSJ3PlpKUl5ibypSZwlFckJNZAjSQJTk%2FJZUFJMqSl1%2BSypqTWpaaw5xXmstWXJJYUlrMDpICqSvOLyphTUosSc5gT0nyzEtJreAA2eGTWVzCnlpR4pmXlp%2FAnpoTDxK0Ma70YWBgYHnxJdxQIDQ4COg5U2NzczMjEzNLM6anG5ue71ruN%2FnkuoksiUWpiRP9gABJbzUOveZmlkxP1y57vnyi38SVE9mLS5OyUpNLJoF0c8FiyTOF3dzExMDEzBwUekH5OanxIUCfx1samRtaGDIacsHCFByTgjA1nilQFTZ9C6MApFSgkg%3D%3D; CNZZDATA1258801290=791393536-1579822430-http%253A%252F%252Fcas.study.teacheredu.cn%252F%7C1579822430";
            LearnHttpHelper.addStudyTime(split[RandomUtil.randomInt(split.length)], studyTime, cookies);
            log.info("开始处理丁倩的自动学习");
            courseIds = "1496803,1496803,1496805,1496807,1496697,1496699,1496701,1496703,1496691,1496693,1496695,1496713,1496715,1496717,1496719,1496705";
            split = courseIds.split(",");
            cookies = "JSESSIONID=08DCBDD9F3277C86EF2CAE2BD041E2ED; UM_distinctid=16fd0e28e681fe-0a9370c0ef399f-c383f64-100200-16fd0e28e691d2; looyu_id=91bfc6086ced9648fbb8dc2415974021_20003718%3A1; looyu_20003718=v%3A91bfc6086ced9648fbb8dc2415974021%2Cref%3Ahttp%253A//www.teacheredu.cn/html/hn/%2Cr%3A%2Cmon%3Ahttp%3A//vqf5z.talk99.cn/monitor%2Cp0%3Ahttp%253A//html.study.teacheredu.cn/el/proj_6401/index.html; JSESSIONID=E0333A80474F2D9A85FE44BC8277DEF0; CNZZDATA1258801290=1852010944-1579757269-http%253A%252F%252Fstudy.teacheredu.cn%252F%7C1579784274; JSESSION_ID=TLFK8Z-YLHCBPEA1GF01DHTIFOD2-JERKRQ5K-SO81; tmp_uc0=eNrz4Ap1jg8tTi2K90yJZCjsCOQNdg0O9vT3i%2Ff1d3H1MYiozkyxUgrxcfO2iNKN9PFwdgpwdTR0dzMwdPEI8XTzdzHS9XIN8g4KNPXWDfa3MFTSSS6xMjQ1tzS3MDUzNjIxMdZJTIYLmBibGBrp5FZYGdRGAQBkmiE9; _const_cas_assertion_id_=7440465; _const_cas_assertion_=19lyxm4980; lastlogin=\"2020-01-23 19:50\"; CAS_SID=6b7df5a3-16cb-4e1c-8a0c-fe936ab900eb:1579785673592; tmp_pc0=eNp1UU1u01AQTlO3ripRilixrdRdYtmJ7RdH2UDsEDcmNrajNt08nPemiivHjuyXKqFiwwGQDAdAbKhYUKlbboA4R1UOwAGwEUFsOqvRN9833%2Fz0Hzld7CYRYJNirYEkpHXefXpYgKMMUuykyXlR6TzZeLzm%2Bas5%2FGVuSA88w%2FNMe4hf2LphiSeXIW0f%2BFZv0Dqtj61%2B95ljPJWe90RJ7%2Ftmz9Yb9SPDHbgvlUHds1vSQY2wtqQgDbUUVW6qqlgLyD8ANRVNrs2WbfFN6T4vZxl5hotNHQ9gNa7sHf7Yc1z7COtm1%2FfHjuG97oqHJBYYBGQKKdCFkBGBhoQJNJkFYSzoRc6KDT5u00kczKAa0p1sHoWsaMiRhAJXolycMNiK4AKizXgx285YwBYZX5ZKXpakbGsSMDLl6cSMKSx3Sg8rzBgPS2bGZ8krHiJcgp3myqpUKtzPX8fS%2Fshzi%2BWUJkJqQ1Y1tXp78%2Fbu29Xww%2FfrnAtSCPJhEf9pL%2B%2FRIlWr3n79fHeVD%2FMvOZ8tJudA2PtSvbv%2BnUl5JMuirCq76%2Bv9%2BeTpb79YoOU%3D";
            LearnHttpHelper.addStudyTime(split[RandomUtil.randomInt(split.length)], studyTime, cookies);
        } catch (Exception e) {
            log.warn("异常原因：" + e.getLocalizedMessage());
        } finally {
        }
    }

}
