package org.example;

import com.github.benmanes.caffeine.cache.Cache;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataCacheTest {

    @Autowired
    Cache<String, Object> caffeineCache;
    final Map<String,String> map = new HashMap<String,String>() {{
        put("1","1111");
        put("2","2222");
        put("3","3333");
    }};

    @Test
    public void addUserInfo() {
        System.out.println("创建");
        // 加入缓存
        caffeineCache.put(String.valueOf(1),map);
    }

    @Test
    public void getUserInfo() {
        // 先从缓存读取
        caffeineCache.getIfPresent(1);
        Map userInfo = (Map) caffeineCache.asMap().get(String.valueOf(1));
        if (userInfo != null){
            System.out.println(userInfo.toString());
        }
    }

}
