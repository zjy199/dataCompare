package com.thxh.datacompare.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author YYY
 * @date 2021年12月23日11:31
 */
@Configuration
@MapperScan(basePackages = "com.thxh.datacompare.dao.comp2dao",sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class SlaveDataSourceConfig {

    private static final String MAPPER_LOCATION = "classpath*:mapper/mapper2/*.xml";

    /**
     * 返回 Master 副数据源，对应 comp2 数据库
     * @return
     */
    @Bean(name = "slaveSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 返回 副数据库 对应的数据库回话模板，对应 comp2
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "slaveSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("slaveSource") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
        configuration.setCallSettersOnNulls(true);
        bean.setConfiguration(configuration);

        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SlaveDataSourceConfig.MAPPER_LOCATION));

        return bean.getObject();
    }

    /**
     * 返回 comp2 数据库的事务
     * @param dataSource
     * @return
     */
    @Bean(name = "slaveTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("slaveSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

}
