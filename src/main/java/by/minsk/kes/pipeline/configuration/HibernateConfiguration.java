package by.minsk.kes.pipeline.configuration;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class HibernateConfiguration {

  @Value("${event.driver}")
  private String driver;

  @Value("${event.url}")
  private String eventDbUrl;

  @Value("${event.user}")
  private String eventDbUser;

  @Value("${event.password}")
  private String eventDbPassword;

  @Value("${event.pool.max}")
  private String eventPoolSize;

  @Value("${event.dialect}")
  private String eventDbDialect;

  @Value("${event.show_sql}")
  private String showSql;

  @Bean
  public DataSource getDataSource()
  {
    final DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(driver);
    ds.setUrl(eventDbUrl);
    ds.setUsername(eventDbUser);
    ds.setPassword(eventDbPassword);
    return ds;
  }

  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory)
  {
    final HibernateTransactionManager htm = new HibernateTransactionManager();
    htm.setSessionFactory(sessionFactory);
    return htm;
  }

  @Bean
  @Autowired
  public HibernateTemplate getHibernateTemplate(final SessionFactory sessionFactory)
  {
    return new HibernateTemplate(sessionFactory);
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(getDataSource());
    sessionFactory.setPackagesToScan("by.minsk.kes.pipeline.domain");
    sessionFactory.setHibernateProperties(getHibernateProperties());

    return sessionFactory;
  }

  @Bean
  public Properties getHibernateProperties()
  {
    final Properties properties = new Properties();
    properties.put("hibernate.dialect", eventDbDialect);
    properties.put("hibernate.show_sql", showSql);
    properties.put("hibernate.jdbc.lob.non_contextual_creation", true);
    return properties;
  }

}
