package stock.base.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stock.base.redis.service.RedisService;

import java.util.Calendar;

/**
 * @author cgl
 * @date 2018/11/17 22:46
 */
@Service
@Slf4j
public class ExecService {

    @Autowired
    RedisService redisService;

    /**
     * 周一到周五 9点到12点  13点到16点执行
     */
    public boolean exec(Calendar calendar) {
        Boolean result = false;
        int houres = calendar.get(Calendar.HOUR_OF_DAY);
        int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
        if (week > 0 && week < 7) {
            if ((houres >= 9 && houres < 12) || (houres >= 13 && houres <= 16)) {
                result = true;
            }
        }
        return result;
    }

}
