package com.gdtcs.config;

import javax.sql.DataSource;

import jakarta.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan( basePackages = "com.gdtcs.batch.dataCollectors.**.mapper" , sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisConfig {

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.datasource.hikari.jdbc-url}")
	private String jdbcUrl;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.hikari.maximum-pool-size}")
	private int maximumPoolSize;

	@Value("${spring.datasource.hikari.minimum-idle}")
	private int minimumIdle;

	@Value("${spring.datasource.hikari.idle-timeout}")
	private long idleTimeout;

	@Value("${spring.datasource.hikari.pool-name}")
	private String poolName;

	@Value("${spring.datasource.hikari.max-lifetime}")
	private long maxLifetime;

	@Value("${spring.datasource.hikari.connection-timeout}")
	private long connectionTimeout;

	@Value("${spring.datasource.hikari.leak-detection-threshold}")
	private long leakDetectionThreshold;

	@Value("${spring.datasource.hikari.validation-timeout}")
	private long validationTimeout;

	@Value("${spring.datasource.hikari.connection-test-query}")
	private String connectionTestQuery;

	@Primary
	@Bean
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setMaximumPoolSize(maximumPoolSize);
		dataSource.setMinimumIdle(minimumIdle);
		dataSource.setIdleTimeout(idleTimeout);
		dataSource.setPoolName(poolName);
		dataSource.setMaxLifetime(maxLifetime);
		dataSource.setConnectionTimeout(connectionTimeout);
		dataSource.setLeakDetectionThreshold(leakDetectionThreshold);
		dataSource.setValidationTimeout(validationTimeout);
		dataSource.setConnectionTestQuery(connectionTestQuery);
		return dataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:config/mybatis-config.xml"));
		sessionFactory.setMapperLocations(
			new PathMatchingResourcePatternResolver().getResources("classpath:sqlmap/mapper/*/*.xml")
		);
		return sessionFactory.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name="jobRepository")
	public JobRepository jobRepository() throws Exception {
		JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		factory.setDataSource(dataSource());
		factory.setTransactionManager(transactionManager(dataSource()));
		factory.afterPropertiesSet();
		return factory.getObject();
	}
}
