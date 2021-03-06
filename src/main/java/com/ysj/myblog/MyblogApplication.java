package com.ysj.myblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication//等同于 @Configuration @EnableAutoConfiguration @ComponentScan
@MapperScan("com.ysj.myblog.dao")
@ServletComponentScan
// @ImportResource(locations={"classpath:applicationContext.xml"})
public class MyblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}
}
