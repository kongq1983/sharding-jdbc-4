package com.kq.sharding.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author: kq
 * @date: 2024-05-20 01:53:25
 * @since: 1.0.0
 * @description:
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@EnableConfigurationProperties(MybatisPlusProperties.class)
@AutoConfigureAfter({ MysqlConfig.class})
public class MybatisPlusAutoExtendConfiguration  {

    private final MybatisPlusProperties properties;

    private final Interceptor[] interceptors;

    private final TypeHandler[] typeHandlers;

    private final LanguageDriver[] languageDrivers;

    private final ResourceLoader resourceLoader;

    private final DatabaseIdProvider databaseIdProvider;

    private final List<ConfigurationCustomizer> configurationCustomizers;

    private final List<MybatisPlusPropertiesCustomizer> mybatisPlusPropertiesCustomizers;

    private final ApplicationContext applicationContext;


    public MybatisPlusAutoExtendConfiguration(MybatisPlusProperties properties,
                                        ObjectProvider<Interceptor[]> interceptorsProvider,
                                        ObjectProvider<TypeHandler[]> typeHandlersProvider,
                                        ObjectProvider<LanguageDriver[]> languageDriversProvider,
                                        ResourceLoader resourceLoader,
                                        ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                                        ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
                                        ObjectProvider<List<MybatisPlusPropertiesCustomizer>> mybatisPlusPropertiesCustomizerProvider,
                                        ApplicationContext applicationContext) {
        this.properties = properties;
        this.interceptors = interceptorsProvider.getIfAvailable();
        this.typeHandlers = typeHandlersProvider.getIfAvailable();
        this.languageDrivers = languageDriversProvider.getIfAvailable();
        this.resourceLoader = resourceLoader;
        this.databaseIdProvider = databaseIdProvider.getIfAvailable();
        this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
        this.mybatisPlusPropertiesCustomizers = mybatisPlusPropertiesCustomizerProvider.getIfAvailable();
        this.applicationContext = applicationContext;
    }



    @Bean
    public MybatisPlusAutoConfiguration getMybatisPlusAutoConfiguration(MybatisPlusProperties properties,
                                                                        ObjectProvider<Interceptor[]> interceptorsProvider,
                                                                        ObjectProvider<TypeHandler[]> typeHandlersProvider,
                                                                        ObjectProvider<LanguageDriver[]> languageDriversProvider,
                                                                        ResourceLoader resourceLoader,
                                                                        ObjectProvider<DatabaseIdProvider> databaseIdProvider,
                                                                        ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider,
                                                                        ObjectProvider<List<MybatisPlusPropertiesCustomizer>> mybatisPlusPropertiesCustomizerProvider,
                                                                        ApplicationContext applicationContext) {

        return new MybatisPlusAutoConfiguration(properties,
                 interceptorsProvider,typeHandlersProvider,
                languageDriversProvider,resourceLoader,
                databaseIdProvider,configurationCustomizersProvider,
                mybatisPlusPropertiesCustomizerProvider,applicationContext);

    }

}
