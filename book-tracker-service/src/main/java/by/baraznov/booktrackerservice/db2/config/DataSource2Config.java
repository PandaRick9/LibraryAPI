package by.baraznov.booktrackerservice.db2.config;

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
 * Configuration class for the secondary database.
 * Enables transaction management and JPA repositories for the second data source.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "secondEntityMangerFactoryBean", basePackages = {
        "by.baraznov.booktrackerservice.db2.repository" }, transactionManagerRef = "secondTransactionManager")
public class DataSource2Config {

    @Autowired
    private Environment environment;
    /**
     * Defines the secondary data source.
     * @return A configured DataSource bean for the second database.
     */
    @Bean(name = "secondDataSource")
    @Primary
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(environment.getProperty("second.datasource.url"));
        dataSource.setDriverClassName(environment.getProperty("second.datasource.driver-class-name"));
        dataSource.setUsername(environment.getProperty("second.datasource.username"));
        dataSource.setPassword(environment.getProperty("second.datasource.password"));
        return dataSource;
    }
    /**
     * Configures the entity manager factory for the second database.
     * @return A LocalContainerEntityManagerFactoryBean instance.
     */
    @Primary
    @Bean(name = "secondEntityMangerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource());
        bean.setPackagesToScan("by.baraznov.booktrackerservice.db2.model");

        JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);

        Map<String, String> props = new HashMap<>();
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.show_sql", "true");

        bean.setJpaPropertyMap(props);

        return bean;
    }
    /**
     * Defines the transaction manager for the second database.
     * @return A configured JpaTransactionManager instance.
     */
    @Bean(name = "secondTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return manager;
    }
}
