package stock.base.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author cgl
 * @date 2018/11/13 23:14
 */
@Component
public class RedisService {

    @Autowired
    private StringRedisTemplate template;

    public void save(String key, String val) {
        template.opsForValue().set(key, val, 10, TimeUnit.MINUTES);
    }

    public String get(String key) {
        return template.opsForValue().get(key);
    }

    /**
     * 查询缓存的是否抓去数据的标记
     *
     * @param execKey
     * @return
     */
    public boolean getExec(String execKey) {
        String exec = template.opsForValue().get(execKey);
        if (exec == null) {
            return true;
        }
        return Boolean.valueOf(exec);
    }
}
