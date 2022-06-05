package org.example;

import com.github.benmanes.caffeine.cache.Cache;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class Main {

    @Autowired
    Cache<String, Object> caffeineCache;
    final Map<String, String> map = new HashMap<String, String>() {{
        put("1", "1111");
        put("2", "2222");
        put("3", "3333");
    }};

    @GetMapping("addUserInfo")
    public String addUserInfo() {
        System.out.println("创建");
        // 加入缓存
        caffeineCache.put(String.valueOf(1), map);
        return "创建成功";

    }

    @GetMapping("getUserInfo")
    public String getUserInfo() {
        // 先从缓存读取
        caffeineCache.getIfPresent(1);
        Map userInfo = (Map) caffeineCache.asMap().get(String.valueOf(1));
        return userInfo != null ? userInfo.toString() : "无此用户";

    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
