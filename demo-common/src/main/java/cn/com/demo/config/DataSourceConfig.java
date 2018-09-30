package cn.com.demo.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据源公共配置
 * @author: code4fun
 * @date: 2018/9/30:上午9:09
 */
@Configuration
@MapperScan(basePackages = {
        "cn.com.demo.*.dao.*.mapper",
        "cn.com.demo.*.dao.mapper"
})
public class DataSourceConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:/cn/com/demo/*/dao/*/mapper/*Mapper.xml"));

        List<Interceptor> interceptors = new ArrayList<>();
        Interceptor[] interceptorsArr = new Interceptor[1];
        interceptorsArr[0] = paginationInterceptor();
        interceptors.add(paginationInterceptor());
        mybatisSqlSessionFactoryBean.setPlugins(interceptorsArr);

        return mybatisSqlSessionFactoryBean;
    }



    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }


    /*
     * 分页插件，自动识别数据库类型
     * 多租户，请参考官网【插件扩展】
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
