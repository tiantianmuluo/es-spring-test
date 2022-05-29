package org.example;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataEsTest {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ProductDao productDao;

    // 查询所有的索引 地址：http://127.0.0.1:9200/_cat/indices?v
    @Test
    public void createIndex() {
        //创建索引，系统初始化会自动创建索引
        System.out.println("创建索引");
    }

    @Test
    public void deleteIndex() {
        String delete = elasticsearchRestTemplate.delete(Product.class);
        System.out.println("删除索引=" + delete);
    }

    // 查看保存记录地址：http://127.0.0.1:9200/product/_doc/2
    public void save() {
        Product product = new Product();
        product.setId(2L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(2999.0);
        product.setImages("http://www.example.com/hw.jpg");
        productDao.save(product);
    }

    public void update() {
        Product product = new Product();
        product.setId(2L);
        product.setTitle("小米2手机");
        product.setCategory("手机");
        product.setPrice(9999.0);
        product.setImages("http://www.example.com/xm.jpg");
        productDao.save(product);
    }

    public void findById() {
        Product product = productDao.findById(2L).get();
        System.out.println(product);
    }

    public void findAll() {
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void delete() {
        Product product = new Product();
        product.setId(2L);
        productDao.delete(product);
    }

    // 查询所有地址：http://127.0.0.1:9200/product/_search
    public void saveAll() {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setId(Long.valueOf(i));
            product.setTitle("[" + i + "]小米手机");
            product.setCategory("手机");
            product.setPrice(1999.0 + i);
            product.setImages("http://www.example.com");
            productList.add(product);
        }
        productDao.saveAll(productList);
    }

    public void findByPageable() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int currentPage = 0; // 当前页，从2开始，1表示第2页
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize, sort);
        Page<Product> productPage = productDao.findAll(pageRequest);
        for (Product product : productPage.getContent()) {
            System.out.println(product);
        }
    }

    public void termQuery() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category", "手机");
        Iterable<Product> products = productDao.search(termQueryBuilder);
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void termQueryByPage() {
//        Product product = new Product();
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category", "手机");
        int currentPage = 0; // 当前页，从2开始，1表示第2页
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        Iterable<Product> products = productDao.search(termQueryBuilder, pageRequest);
//        String[] conditions = {"category"};
//        productDao.searchSimilar(product, conditions, pageRequest);
        for (Product product : products) {
            System.out.println(product);
        }
    }


}
