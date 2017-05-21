package com.ysj.myblog.dao;

import com.ysj.myblog.entity.Link;
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
public class LinkDaoTest {

    @Autowired
    private LinkDao linkDao;

    @Test
    public void list() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("start", 0);
        map.put("size", 1);
        List<Link> list1 = linkDao.list(map);
        Assert.assertNotNull("list为null", list1);
    }

    @Test
    public void getTotal() throws Exception {
        Long total = linkDao.getTotal(null);
        Assert.assertNotNull("total为null", total);
    }

    @Test
    public void add() throws Exception {
        Link link = new Link();
        link.setLinkName("asd");
        link.setLinkUrl("#");
        link.setOrderNo(2);
        Integer data = linkDao.add(link);
        Assert.assertNotNull("数据插入失败", data);
    }

    @Test
    public void update() throws Exception {
        Link link = new Link();
        link.setId(1);
        link.setLinkName("java");
        link.setLinkUrl("#");
        link.setOrderNo(1);
        Integer data = linkDao.update(link);
        Assert.assertNotNull("数据更新失败", data);
    }

    @Test
    public void delete() throws Exception {
        Integer data = linkDao.delete(15);
        Assert.assertNotNull("数据删除失败", data);
    }

}