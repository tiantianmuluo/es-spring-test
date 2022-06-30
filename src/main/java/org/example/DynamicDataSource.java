package org.example;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
@Primary
public class DynamicDataSource extends AbstractRoutingDataSource {
    public static ThreadLocal<String> name = new ThreadLocal<>();

    @Autowired
    DataSource dataSource1;

    @Autowired
    DataSource dataSource2;


    @Override
    protected Object determineCurrentLookupKey() {
        return name.get();
    }

    @Override
    public void afterPropertiesSet() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("W",dataSource1);
        targetDataSources.put("R",dataSource2);
        super.setTargetDataSources(targetDataSources);
        super.setDefaultTargetDataSource(dataSource1);
        super.afterPropertiesSet();
    }
}
