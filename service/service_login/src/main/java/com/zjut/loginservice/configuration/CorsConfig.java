//package com.zjut.loginservice.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
//@Configuration
//public class CorsConfig extends WebMvcConfigurationSupport {
//
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("**")
//                .allowedOrigins("*")
//                .allowCredentials(true)
//                .allowedMethods("GET","POST","DELETE","PUT")
//                .maxAge(3600);
//    }
//
//    /**
//     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。 需要重新指定静态资源
//     *
//     * @param registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations(
//                "classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
//                "classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations(
//                "classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry);
//    }
//
//    @Bean
//    public InternalResourceViewResolver viewResolver(){
//        return new InternalResourceViewResolver();
//    }
//
//
//
//
//
//}
