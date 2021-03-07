package com.example.service;


import com.example.pojo.Cat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CatServiceTest {

    @Autowired
    private CatService catService;

    @Test
    public void insert() {
        int insert = catService.insert(null);
    }

    @Test
    public void save() {
        int save = catService.save(null);
    }

    @Test
    public void selectByPrimaryKey() {
        Cat cat = catService.selectByPrimaryKey(null);
    }

    @Test
    public void getById() {
        Cat byId = catService.getById(null);
    }
}