package com.muzimin.config;

import com.muzimin.bean.Pet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author: 李煌民
 * @date: 2024-04-03 15:44
 **/
@Configuration(proxyBeanMethods = false)
public class MyConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                UrlPathHelper urlPathHelper = new UrlPathHelper();
                //不移除;号后面的数据。矩阵变量就可以生效
                urlPathHelper.setRemoveSemicolonContent(false);
                urlPathHelper.setAlwaysUseFullPath(true);
                configurer.setUrlPathHelper(urlPathHelper);
            }

            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter((Converter<String, Pet>) source -> {
                    if (StringUtils.hasText(source)) {
                        String[] split = source.split(",");
                        return new Pet(split[0], split[1]);
                    }
                    return null;
                });
            }
        };
    }
}
