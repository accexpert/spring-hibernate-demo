package com.acc.hibernate.config;

import com.acc.hibernate.utils.ConstantsUtils;
import com.acc.hibernate.utils.ProfilesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;
import java.util.Properties;

import static com.acc.hibernate.utils.ConfigPropertiesUtils.*;

/**
 * Author: Cristi Ando Ciupav
 * Date: 12/10/2016.
 * Email: accexpert@gmail.com
 */

@Configuration
@PropertySource(value = {ApplicationConfig.DATABASE_CONFIG_FILE, ApplicationConfig.APPLICATION_CONFIG_FILE})
@ComponentScan(value = "com.acc.hibernate", excludeFilters = @ComponentScan.Filter(value = {Configuration.class}))
@EnableTransactionManagement
public class ApplicationConfig {
    public static final String DATABASE_CONFIG_FILE = "classpath:database-config.properties";
    public static final String APPLICATION_CONFIG_FILE = "classpath:application-config.properties";
    public static final String PERSISTENCE_UNIT_NAME = "hibernatePU";

    @Autowired
    Environment env;

    //------------- START MYSQL SECTION -------------
    @Bean
    @Profile(ProfilesUtils.PROFILE_HIBERNATE_MYSQL)
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(ConstantsUtils.MYSQL_DRIVER_CLASS_NAME);
        dataSource.setUrl("jdbc:mysql://"+env.getProperty(DB_URL)+":"+env.getProperty(DB_PORT)+"/"+env.getProperty(DB_NAME));
        dataSource.setUsername(env.getProperty(DB_USER));
        dataSource.setPassword(env.getProperty(DB_PASS));
        return dataSource;
    }

    @Bean
    @Profile(value = {ProfilesUtils.PROFILE_HIBERNATE_USE_CACHE_LEVEL_2, ProfilesUtils.PROFILE_HIBERNATE_USE_QUERY_CACHE})
    public Properties hibernateProperties() {
        Properties props = new Properties();
        props.put("hibernate.connection.isolation","2");
        props.put("hibernate.connection.release_mode","on_close");

        props.put("hibernate.order_updates","false");
        props.put("hibernate.order_inserts","false");
        props.put("hibernate.cache.use_structured_entries","true");
        props.put("hibernate.jdbc.batch_size","50");

        //cache level 2
        props.put("hibernate.cache.use_second_level_cache","true");
        props.put("hibernate.cache.use_query_cache","true");
        props.put("hibernate.cache.provider_class","org.hibernate.cache.EhCacheProvider");
        props.put("hibernate.cache.region.factory_class","org.hibernate.cache.ehcache.EhCacheRegionFactory");

        props.put("hibernate.temp.use_jdbc_metadata_defaults","false");
        props.put("hibernate.current_session_context_class","jta");

        //debug
        props.put("hibernate.show_sql",env.getProperty(HIBERNATE_DEBUG_MODE));
        props.put("hibernate.format_sql",env.getProperty(HIBERNATE_DEBUG_MODE));
        props.put("hibernate.use_sql_comments",env.getProperty(HIBERNATE_DEBUG_MODE));
        props.put("hibernate.generate_statistics",env.getProperty(HIBERNATE_DEBUG_MODE));
        return props;
    }

    /**
     * <p>
     *
     *     SharedCacheMode explained:
     *     SharedCacheMode.ALL - means every entity will be cached in L2 no matter if the Cacheable annotation is set or not
     *     SharedCacheMode.ENABLE_SELECTIVE - entity will be cached in L2 only if Cacheable annotation is set to true. All other entities will not be cached.
     *     SharedCacheMode.DISABLE_SELECTIVE - Caching is enabled for all entities except those for which Cacheable(false) is specified. Entities for which
     *                                         Cacheable(false) is specified are not cached.
     *     SharedCacheMode.NONE - Caching is disabled for the persistence unit

     * </p>
     * @param dataSource
     * @param hibernateJpaDialect
     * @param hibernateProperties
     * @param hibernateJpaVendorAdapterMysql
     * @return
     */
    @Bean
    @Profile(ProfilesUtils.PROFILE_HIBERNATE_MYSQL)
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource, HibernateJpaDialect hibernateJpaDialect, Properties hibernateProperties,
                                                                           HibernateJpaVendorAdapter hibernateJpaVendorAdapterMysql) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        entityManagerFactoryBean.setPackagesToScan("com.acc.hibernate.repository");
        entityManagerFactoryBean.setJpaProperties(hibernateProperties);
        entityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapterMysql);
        entityManagerFactoryBean.setJpaDialect(hibernateJpaDialect);
        entityManagerFactoryBean.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        return entityManagerFactoryBean;
    }

    @Bean
    @Profile(ProfilesUtils.PROFILE_HIBERNATE_MYSQL)
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapterMysql() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return hibernateJpaVendorAdapter;
    }

    //------------- END MYSQL SECTION -------------
    //------------- START MYCROSOFT SQL SECTION -------------

    @Bean
    @Profile(ProfilesUtils.PROFILE_HIBERNATE_MICROSOFTSQL)
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapterMicrosoft() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.SQLServer2012Dialect");
        return hibernateJpaVendorAdapter;
    }
    //------------- END MYCROSOFT SQL SECTION -------------

    @Bean
    public HibernateJpaDialect hibernateJpaDialect() {
        return new HibernateJpaDialect();
    }

    @Bean
    public JpaTransactionManager transactionManager(DataSource dataSource, HibernateJpaDialect hibernateJpaDialect) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource);
        transactionManager.setJpaDialect(hibernateJpaDialect);
        transactionManager.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        transactionManager.setDefaultTimeout(15000);    //15 seconds
        return transactionManager;
    }

}
