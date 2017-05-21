package com.ysj.myblog.dao;

import com.ysj.myblog.entity.Blog;
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
public class BlogDaoTest {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private BlogTypeDao blogTypeDao;

    @Test
    public void blogTypesAndCountsByDate() throws Exception {
        List<Blog> list = blogDao.blogTypesAndCountsByDate();
        Assert.assertNotNull("查询结果为null", list);
        for (Blog blog : list) {
            System.out.println(blog.getReleaseDateStr() + ": " + blog.getBlogCount());
        }
    }

    @Test
    public void list() throws Exception {
        /*Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("title", "Java HelloWorld实现");
        List<Blog> list1 = blogDao.list(map1);
        Assert.assertNotNull("查询结果为null", list1);*/

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("typeId", 1);
        List<Blog> list2 = blogDao.list(map2);
        Assert.assertNotNull("查询结果为null", list2);

        /*Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("releaseDateStr", "2016年02月");
        List<Blog> list3 = blogDao.list(map3);
        Assert.assertNotNull("查询结果为null", list3);*/

        /*Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("start", 0);
        map4.put("size", 1);
        List<Blog> list4 = blogDao.list(map4);
        Assert.assertNotNull("查询结果为null", list4);*/
    }

    @Test
    public void getTotal() throws Exception {
        /*Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("title", "Java HelloWorld实现");
        Long list1 = blogDao.getTotal(map1);
        System.out.println("查询结果为" + list1);*/

        /*Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("typeId", 1);
        Long list2 = blogDao.getTotal(map2);
        System.out.println("查询结果为" + list2);*/

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("releaseDateStr", "2016年02月");
        Long list3 = blogDao.getTotal(map3);
        System.out.println("查询结果为" + list3);
    }

    @Test
    public void findById() throws Exception {
        Blog blog = blogDao.findById(58);
        // 注意这里没有将blogType通过typeId查出来，在Service层将其查出放在blog里返回
        System.out.println("blogType: " +blog.getBlogType());
        System.out.println(blog);
    }

    @Test
    public void update() throws Exception {
        Blog blog = blogDao.findById(58);
        System.out.println("blogType: " + blog.getBlogType());
        System.out.println(blogDao.findById(58));
        blog.setTitle("123456");

        blogDao.update(blog);
        Blog blog2 = blogDao.findById(58);
        System.out.println(blog2);
    }

    @Test
    public void getLastBlog() throws Exception {
        Blog blog = blogDao.getLastBlog(40);
        System.out.println(blog);
    }

    @Test
    public void getNextBlog() throws Exception {
        Blog blog = blogDao.getNextBlog(40);
        System.out.println(blog);
    }

    @Test
    public void add() throws Exception {
        Blog blog = blogDao.findById(40);
        blog.setId(null);
        blogDao.add(blog);
        System.out.println(blog);// 注意插入后的主键也返回到blog里了
    }

    @Test
    public void delete() throws Exception {
        blogDao.delete(66);
    }

    @Test
    public void getBlogByTypeId() throws Exception {
        blogDao.getBlogByTypeId(1);
    }

}