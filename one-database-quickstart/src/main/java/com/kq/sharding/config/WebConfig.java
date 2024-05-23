package com.kq.sharding.config;

import com.kq.sharding.converter.StringToDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

/**
 *
 * @author kongqi
 * @date 2017-10-23 13:36
 * @since
 */
@Configuration
public class WebConfig {

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	@Autowired
	private RestTemplateBuilder builder;

	/**
	 * 增加字符串转日期的功能
	 */
	@PostConstruct
	public void init() {
		ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter
				.getWebBindingInitializer();
		if (initializer!=null && initializer.getConversionService() != null) {
			GenericConversionService genericConversionService = (GenericConversionService) initializer
					.getConversionService();
			if(genericConversionService!=null) {
				genericConversionService.addConverter(new StringToDateConverter());
			}
		}

	}

	@Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }

}
