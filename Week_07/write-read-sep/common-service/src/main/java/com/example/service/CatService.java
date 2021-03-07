package com.example.service;

import com.example.annotation.Master;
import com.example.mapper.CatMapper;
import com.example.pojo.Cat;
import org.apache.shardingsphere.api.hint.HintManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatService {

    @Autowired
    private CatMapper catMapper;

    public int insert(Cat aaa) {
        return catMapper.insert(aaa);
    }

    @Master
    public int save(Cat aaa) {
        return catMapper.insert(aaa);
    }

    public Cat selectByPrimaryKey(Long id) {
        return catMapper.selectByPrimaryKey(id);
    }

    @Master
    public Cat getById(Long id) {
        //  有些读操作必须读主数据库 因为高峰时期主从同步可能延迟
        //  这种情况下就必须强制从主数据读
        HintManager.getInstance ().setMasterRouteOnly();
        return catMapper.selectByPrimaryKey(id);
    }
}
