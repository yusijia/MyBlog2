package com.ysj.myblog.dao;

import com.ysj.myblog.entity.Blogger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yusijia
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BloggerDaoTest {

    @Autowired
    private BloggerDao bloggerDao;

    @Test
    public void getByUserName() throws Exception {
        Blogger blogger = bloggerDao.getByUserName("root");
        Assert.assertNotNull("不存在该博主", blogger);
    }

    @Test
    public void find() throws Exception {
        Blogger blogger = bloggerDao.find();
        Assert.assertNotNull("不存在博主", blogger);
    }

    @Test
    public void update() throws Exception {
        Blogger blogger = new Blogger();
        blogger.setNickName("YSJ");
        bloggerDao.update(blogger);
        System.out.println("Blogger: " + bloggerDao.find());
    }

}