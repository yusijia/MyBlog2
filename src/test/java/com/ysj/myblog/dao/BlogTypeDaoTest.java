package com.ysj.myblog.dao;

import com.ysj.myblog.entity.BlogType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yusijia
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogTypeDaoTest {

    @Autowired
    private BlogTypeDao blogTypeDao;

    @Test
    public void blogTypesAndCounts() throws Exception {
        List<BlogType> list = blogTypeDao.blogTypesAndCounts();
        Assert.assertNotNull("查询结果为null", list);
        for (BlogType temp : list) {
            System.out.println(temp.getTypeName() + ": " + temp.getBlogCount());
        }
    }

    @Test
    public void findById() throws Exception {
        BlogType blogType = blogTypeDao.findById(1);
        Assert.assertNotNull("查询结果为null", blogType);
        /*BlogType blogType2 = blogTypeDao.findById(0);
        Assert.assertNotNull("查询结果为null", blogType2);*/
    }

    @Test
    public void list() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", 0);
        map.put("size", 1);
        List<BlogType> list = blogTypeDao.list(map);
        Assert.assertNotNull("查询结果为null", list);
    }

    @Test
    public void getTotal() throws Exception {
        Long data = blogTypeDao.getTotal(null);
        Assert.assertNotNull("查询总数为null", data);
    }

    @Test
    public void add() throws Exception {
        BlogType blogType = new BlogType();
        blogType.setTypeName(null);
        blogType.setOrderNo(1);
        blogType = null;
        Integer data = blogTypeDao.add(blogType);
        System.out.println("插入了" + data + "条数据");
    }

    @Test
    public void update() throws Exception {
        System.out.println(blogTypeDao.findById(31).toString());
        BlogType blogType = new BlogType();
        blogType.setId(31);
        blogType.setTypeName("11111111");
        Integer data = blogTypeDao.update(blogType);
        System.out.println(blogTypeDao.findById(31).toString());
    }

    @Test
    public void delete() throws Exception {
        blogTypeDao.delete(32);
    }

}