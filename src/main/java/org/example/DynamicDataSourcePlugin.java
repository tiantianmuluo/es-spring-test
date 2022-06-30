package org.example;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})

public class DynamicDataSourcePlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] objects = invocation.getArgs();
        MappedStatement ms = (MappedStatement) objects[0];
        if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
            DynamicDataSource.name.set("R");
        } else {
            DynamicDataSource.name.set("W");
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
