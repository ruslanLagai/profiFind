package ru.home.profi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.home.profi.dao", entityManagerFactoryRef = "entityManagerFactoryBean")
@PropertySource(value = {"classpath:jpa.properties"})
public class JpaConfig implements EnvironmentAware{

    @Autowired
    Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
                JpaVendorAdapter jpaVendorAdapter, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setJpaProperties(jpaProperties());
        entityManagerFactoryBean.setPackagesToScan("ru.home.profi.entity");
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setDatabasePlatform(environment.getProperty("jpa.dialect"));
        return vendorAdapter;
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getProperty("jpa.dialect"));
        properties.put("hibernate.hbm2ddl.auto",
                environment.getRequiredProperty("jpa.hbm2ddl.auto"));
        properties.put("hibernate.ejb.naming_strategy",
                environment.getRequiredProperty("jpa.ejb.naming_strategy"));
        properties.put("hibernate.show_sql",
                environment.getRequiredProperty("jpa.show_sql"));
        properties.put("hibernate.format_sql",
                environment.getRequiredProperty("jpa.format_sql"));
        return properties;
    }


    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    /*
        транслирует Jpa-специфичные исключения в Spring-based
    */
    @Bean
    public BeanPostProcessor exceptionPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
    /*
        Бин для обработки аннотаций Jpa типа @PersistenceContext
     */
    @Bean
    public PersistenceAnnotationBeanPostProcessor annotationBeanPostProcessor() {
        return new PersistenceAnnotationBeanPostProcessor();
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
