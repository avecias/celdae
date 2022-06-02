/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.avecias.celdae.configuration;

/**
 *
 * @author avecias
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;

// @Configuration, this will let spring know this contains bean definitions.
@Configuration
// @EnableWebMVC, this is the same as <mvc:annotation-driven/>
@EnableWebMvc
@ComponentScan(basePackages = {"com.avecias.celdae"})
// Extend the class to use WebMvcConfigurerAdapter. This adds stub implementations from the WebMvcConfigurer interface which is used by @EnableWebMVC. It also gives us a chance to override resources and the default handler.
public class WebInitializer extends WebMvcConfigurerAdapter {

    // Declare our static resources. I added cache to the java config but it’s not required.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
    }

    // Set default servlet handler, this is the same as <mvc:default-servlet-handler/>
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(64 * 1024 * 1024);
        return multipartResolver;
    }

}
