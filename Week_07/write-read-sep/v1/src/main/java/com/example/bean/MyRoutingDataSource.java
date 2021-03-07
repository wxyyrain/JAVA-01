package com.example.bean;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyRoutingDataSource extends AbstractRoutingDataSource {

    /**
     * 操作数据库时mybatis会通过该方法，拿到需要使用的数据源
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }
}
