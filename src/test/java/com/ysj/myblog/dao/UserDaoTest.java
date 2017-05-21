package com.ysj.myblog.dao;

import com.ysj.myblog.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yusijia
 * @Description: 脚手架的测试类，表也也有相应的测试表t_user,后期会删掉
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void login() throws Exception {
        User user = new User();
        user.setUserName("root");
        user.setPassword("admin");
        User resultUser = userDao.login(user);
        Assert.assertNotNull("resultUser为空", resultUser);
    }

    @Test
    public void login2() throws Exception {
        User user = new User();
        user.setUserName("root");
        user.setPassword("123456");
        User resultUser = userDao.login(user);
        Assert.assertNotNull("resultUser为空", resultUser);
    }

}