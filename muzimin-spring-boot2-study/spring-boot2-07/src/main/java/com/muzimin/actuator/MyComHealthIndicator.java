package com.muzimin.actuator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: 李煌民
 * @date: 2024-04-15 18:21
 * 自定义监控信息，必须要以HealthIndicator进行结尾命名的类
 **/
@Component
public class MyComHealthIndicator extends AbstractHealthIndicator {
    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 检查完成
        if (1 == 2) {
//            builder.up(); //健康
            builder.status(Status.UP);
            map.put("count", 1);
            map.put("ms", 100);
        } else {
//            builder.down();
            builder.status(Status.OUT_OF_SERVICE);
            map.put("err", "连接超时");
            map.put("ms", 3000);
        }


        builder.withDetail("code", 100)
                .withDetails(map);
    }
}
