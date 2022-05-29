package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "product", shards = 3, replicas = 1)
public class Product {
    @Id
    private Long id;
    // type为text可以分词，可以指定分词器
    @Field(type = FieldType.Text)
    private String title;
    // type为keyword,以整个内容做为词条
    @Field(type = FieldType.Keyword)
    private String category;
    @Field(type = FieldType.Double)
    private Double price;
    // 不能用图片路径进行查询
    @Field(type = FieldType.Keyword, index = false)
    private String images;
}
