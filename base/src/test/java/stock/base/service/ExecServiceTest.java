package stock.base.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import stock.base.util.DateUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;

/**
 * @author cgl
 * @date 2018/11/19 21:35
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ExecServiceTest {

    @Autowired
    ExecService execService;

    @Test
    public void exec() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,0 );
        calendar.set(Calendar.HOUR_OF_DAY,16 );
        boolean result = execService.exec(calendar);
        Assert.assertTrue(result);
    }
    @Test
    public void test() throws ParseException {
        String today= DateUtil.getToday();
        String second=DateUtil.format(DateUtil.getSecondDay(new Timestamp(System.currentTimeMillis())));
        Calendar calendar=Calendar.getInstance();
        int houres = calendar.get(Calendar.HOUR_OF_DAY);
        int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        System.out.println(today);
        System.out.println(second);
    }
}