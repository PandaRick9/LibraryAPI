package by.baraznov.booktrackerservice.db1.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
/**
 * Configuration class for the primary database.
 * Enables transaction management and JPA repositories for the first data source.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "firstEntityMangerFactoryBean", basePackages = {
        "by.baraznov.booktrackerservice.db1.repository" }, transactionManagerRef = "firstTransactionManager")
public class DataSource1Config {

    @Autowired
    private Environment environment;
    /**
     * Defines the primary data source.
     * @return A configured DataSource bean for the first database.
     */
    @Bean(name = "firstDataSource")
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }
    /**
     * Configures the entity manager factory for the first database.
     * @return A LocalContainerEntityManagerFactoryBean instance.
     */
    @Primary
    @Bean(name = "firstEntityMangerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        bean.setPackagesToScan("by.baraznov.booktrackerservice.db1.model");

        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.show_sql", "true");
        bean.setJpaPropertyMap(props);

        return bean;
    }
    /**
     * Defines the transaction manager for the first database.
     * @return A configured JpaTransactionManager instance.
     */
    @Bean(name = "firstTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return manager;
    }

}