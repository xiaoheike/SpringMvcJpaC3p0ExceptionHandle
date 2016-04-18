package com.spr.init;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.spr")
@PropertySource(value = { "classpath:application.properties", "classpath:c3p0.properties" })
@EnableJpaRepositories("com.spr.repository")
public class WebAppConfig {
    // c3p0
    private static final String PROPERTY_NAME_C300_DRIVER = "driverClass";
    private static final String PROPERTY_NAME_C300_PASSWORD = "password";
    private static final String PROPERTY_NAME_C300_URL = "jdbcUrl";
    private static final String PROPERTY_NAME_C300_USER = "user";
    private static final String PROPERTY_NAME_C3P0_MINIPOOLSIZE = "miniPoolSize";
    private static final String PROPERTY_NAME_C3P0_MAXPOOLSIZE = "maxPoolSize";
    private static final String PROPERTY_NAME_C3P0_INITIALPOOLSIZE = "initialPoolSize";
    private static final String PROPERTY_NAME_C3P0_MAXIDLETIME = "maxIdleTime";
    private static final String PROPERTY_NAME_C3P0_ACQUIREINCREMENT = "acquireIncrement";
    private static final String PROPERTY_NAME_C3P0_ACQUIRERETRYATTEMPTS = "acquireRetryAttempts";
    private static final String PROPERTY_NAME_C3P0_ACQUIRERETRYDELAY = "acquireRetryDelay";
    private static final String PROPERTY_NAME_C3P0_TESTCONNECTIONONCHECKIN = "testConnectionOnCheckin";
    private static final String PROPERTY_NAME_C3P0_AUTOMATICTESTTABLE = "automaticTestTable";
    private static final String PROPERTY_NAME_C3P0_IDLECONNECTIONTESTPERIOD = "idleConnectionTestPeriod";
    private static final String PROPERTY_NAME_C3P0_CHECKOUTTIMEOUT = "checkoutTimeout";

    // messageSouce
    private static final String PROPERTY_NAME_MESSAGE_SOURCE_BASENAME = "message.source.basename";
    // hibernate
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
    private static final String PROPERTY_NAME_HIBERNATE_HMB2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERANTE_FORMAT_SQL = "hiberante.format_sql";

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() throws Exception {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(env.getRequiredProperty(PROPERTY_NAME_C300_DRIVER));
        comboPooledDataSource.setUser(env.getRequiredProperty(PROPERTY_NAME_C300_USER));
        comboPooledDataSource.setJdbcUrl(env.getRequiredProperty(PROPERTY_NAME_C300_URL));
        comboPooledDataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_C300_PASSWORD));
        comboPooledDataSource.setAcquireIncrement(Integer.valueOf(env.getRequiredProperty(PROPERTY_NAME_C3P0_ACQUIREINCREMENT)));
        comboPooledDataSource.setMaxIdleTime(Integer.valueOf(env.getRequiredProperty(PROPERTY_NAME_C3P0_MAXIDLETIME)));
        comboPooledDataSource.setMinPoolSize(Integer.valueOf(env.getRequiredProperty(PROPERTY_NAME_C3P0_MINIPOOLSIZE)));
        comboPooledDataSource.setMaxPoolSize(Integer.valueOf(env.getRequiredProperty(PROPERTY_NAME_C3P0_MAXPOOLSIZE)));
        comboPooledDataSource.setInitialPoolSize(Integer.valueOf(env.getRequiredProperty(PROPERTY_NAME_C3P0_INITIALPOOLSIZE)));
        comboPooledDataSource.setAcquireRetryAttempts(Integer.valueOf(env.getRequiredProperty(PROPERTY_NAME_C3P0_ACQUIRERETRYATTEMPTS)));
        comboPooledDataSource.setAcquireRetryDelay(Integer.valueOf(env.getRequiredProperty(PROPERTY_NAME_C3P0_ACQUIRERETRYDELAY)));
        comboPooledDataSource.setTestConnectionOnCheckin(Boolean.valueOf(env.getRequiredProperty(PROPERTY_NAME_C3P0_TESTCONNECTIONONCHECKIN)));
        comboPooledDataSource.setAutomaticTestTable(env.getRequiredProperty(PROPERTY_NAME_C3P0_AUTOMATICTESTTABLE));
        comboPooledDataSource.setIdleConnectionTestPeriod(Integer.valueOf(env.getRequiredProperty(PROPERTY_NAME_C3P0_IDLECONNECTIONTESTPERIOD)));
        comboPooledDataSource.setCheckoutTimeout(Integer.valueOf(env.getRequiredProperty(PROPERTY_NAME_C3P0_CHECKOUTTIMEOUT)));
        return comboPooledDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));

        entityManagerFactoryBean.setJpaProperties(hibProperties());

        return entityManagerFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_HMB2DDL_AUTO,
                env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HMB2DDL_AUTO));
        properties.put(PROPERTY_NAME_HIBERANTE_FORMAT_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERANTE_FORMAT_SQL));
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws Exception {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename(env.getRequiredProperty(PROPERTY_NAME_MESSAGE_SOURCE_BASENAME));
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

}
