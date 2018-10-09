//package cn.com.demo.portal.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
//import org.apache.ibatis.plugin.Interceptor;
//import org.mybatis.spring.annotation.MapperScan;
//import org.mybatis.spring.mapper.MapperScannerConfigurer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 必须要声明 sqlsessionfactory。
// * @author: code4fun
// * @date: 2018/9/28:上午11:46
// */
//@Configuration
//@MapperScan(basePackages = "cn.com.demo.portal.dao.*.mapper")
//public class MapperScanConfig {
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
//        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
//        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
//        mybatisSqlSessionFactoryBean.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath:/cn/com/demo/*/dao/*/mapper/*Mapper.xml"));
//
//        List<Interceptor> interceptors = new ArrayList<>();
//        Interceptor[] interceptorsArr = new Interceptor[1];
//        interceptorsArr[0] = paginationInterceptor();
//        interceptors.add(paginationInterceptor());
//        mybatisSqlSessionFactoryBean.setPlugins(interceptorsArr);
//
//        return mybatisSqlSessionFactoryBean;
//    }
//
//
//
//    @Bean
//    public DataSourceTransactionManager dataSourceTransactionManager() {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//
//    /*
//     * 分页插件，自动识别数据库类型
//     * 多租户，请参考官网【插件扩展】
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor() {
//        return new PaginationInterceptor();
//    }
//
//   /*
//    * oracle数据库配置JdbcTypeForNull
//    * 参考：https://gitee.com/baomidou/mybatisplus-boot-starter/issues/IHS8X
//    不需要这样配置了，参考 yml:
//    mybatis-plus:
//      confuguration
//        dbc-type-for-null: 'null'
//   @Bean
//   public ConfigurationCustomizer configurationCustomizer(){
//       return new MybatisPlusCustomizers();
//   }
//
//   class MybatisPlusCustomizers implements ConfigurationCustomizer {
//
//       @Override
//       public void customize(org.apache.ibatis.session.Configuration configuration) {
//           configuration.setJdbcTypeForNull(JdbcType.NULL);
//       }
//   }
//   */
//
//
//}
