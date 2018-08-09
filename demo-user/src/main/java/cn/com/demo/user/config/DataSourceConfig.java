package cn.com.demo.user.config;

import com.alibaba.druid.pool.DruidDataSource;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource({
        "classpath:jdbc.properties"
})
//@MapperScan(basePackages = "cn.com.demo.user.dao.mapper", sqlSessionFactoryRef = "yunSqlSessionTemplate")
@MapperScan(basePackages = "cn.com.demo.user.dao.mapper")
public class DataSourceConfig {
    @Value("${jdbc.driver}")
    private String driverClass;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;

    /**
     * 默认数据源配置
     * @return
     */
    @Bean
    @Primary
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(userName);
        dataSource.setUrl(url);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClass);
        return dataSource;

    }

    /*
     * 分页插件，自动识别数据库类型
     * 多租户，请参考官网【插件扩展】
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

//    @Bean(name = "yunMybatisSqlSessionFactoryBean")
    @Bean
    public MybatisSqlSessionFactoryBean yunSqlSessionFactoryBean(DruidDataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/cn/com/demo/user/dao/*/mapper/*Mapper.xml"));

        List<Interceptor> interceptors = new ArrayList<>();
        Interceptor[] interceptorsArr = new Interceptor[1];
        interceptorsArr[0] = paginationInterceptor();
        interceptors.add(paginationInterceptor());
        mybatisSqlSessionFactoryBean.setPlugins(interceptorsArr);

        return mybatisSqlSessionFactoryBean;
    }



    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager( DruidDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean(name = "yunSqlSessionTemplate")
//    public SqlSessionTemplate yunSqlSessionTemplate( MybatisSqlSessionFactoryBean yunSqlSessionFactoryBean) throws Exception{
//
//        SqlSessionFactory sqlSessionFactory = yunSqlSessionFactoryBean.getObject();
//        sqlSessionFactory = (org.apache.ibatis.session.SqlSessionFactory) sqlSessionFactory;
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }


}
