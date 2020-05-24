package io.github.files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

import static io.github.files.TestUtil.createFormattingConversionService;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {"io.github.files"})
@EnableJpaRepositories(basePackages = {"io.github.files"})
@ComponentScan(basePackages = {"io.github.excel", "io.github.files"})
@EnableConfigurationProperties({ApplicationProperties.class})
public class TestAppContainer {


    @Autowired
    private FileTypeRepository fileTypeRepository;

    @Autowired
    private FileTypeService fileTypeService;

    @Autowired
    private FileTypeQueryService fileTypeQueryService;

    @Autowired
    private FileTypeResource fileTypeResource;

    @Autowired
    private FileUploadResource fileUploadResource;

    @Autowired
    @Qualifier("translatedExceptions")
    private TranslatedExceptions translatedExceptions;

    @Bean
    public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public PageableHandlerMethodArgumentResolver pageableArgumentResolver() {
        return new PageableHandlerMethodArgumentResolver();
    }


    @Bean
    public MockMvc restFileUploadMockMvc() {
        return MockMvcBuilders.standaloneSetup(fileUploadResource)
                .setCustomArgumentResolvers(pageableArgumentResolver())
                .setControllerAdvice(translatedExceptions)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter()).build();
//                .setValidator(validator()).build();

    }

    @Bean
    public MockMvc restFileTypeMockMvc() {
        return MockMvcBuilders.standaloneSetup(fileTypeResource)
                .setCustomArgumentResolvers(pageableArgumentResolver())
                .setControllerAdvice(translatedExceptions)
                .setConversionService(createFormattingConversionService())
                .setMessageConverters(jacksonMessageConverter()).build();
//                .setValidator(validator()).build();
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("io.github.files");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:file:~/test");
        dataSource.setUsername("testUser");
        dataSource.setPassword("testUser");
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        return properties;
    }
}
