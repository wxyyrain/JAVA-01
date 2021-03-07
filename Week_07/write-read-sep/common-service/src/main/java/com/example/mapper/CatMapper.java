package com.example.mapper;

import com.example.pojo.Cat;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CatMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Cat record);

    int insertSelective(Cat record);

    Cat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Cat record);

    int updateByPrimaryKey(Cat record);
}
