package com.dco.eureka.client.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.druid.pool.DruidDataSourceFactory;

@Configuration
@ComponentScan(basePackages="com.dco.eureka.client.dao")
public class dcoConfig {

	private static final Logger log=LoggerFactory.getLogger(dcoConfig.class);
	
	@Autowired
	Environment env;
	
	@Bean
	public DataSource getDataSource(){
		Properties pro=new Properties();
		pro.setProperty("driverClass", env.getProperty("jdbc.driverClassName"));
		pro.setProperty("url", env.getProperty("jdbc.url"));
		pro.setProperty("username", env.getProperty("jdbc.username"));
		pro.setProperty("password", env.getProperty("jdbc.password"));
		try{
			return DruidDataSourceFactory.createDataSource(pro);
		}catch(Exception e){
			log.error(e.getMessage());;
		}
		return null;
	}
	
	@Bean
	public SqlSessionFactory sqlFactorySession(DataSource ds) throws Exception{
		SqlSessionFactoryBean sqlSessionFactory=new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(ds);
		sqlSessionFactory.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackages"));
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mappingLocations")));
		return sqlSessionFactory.getObject();
}

}