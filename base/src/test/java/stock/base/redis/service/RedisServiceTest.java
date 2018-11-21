package stock.base.redis.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author cgl
 * @date 2018/11/14 0:00
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisServiceTest {


    @Autowired
    RedisService redisService;

    @Test
    public void save() {

        redisService.save("2","2");
    }

    @Test
    public void save1() {
    }
}