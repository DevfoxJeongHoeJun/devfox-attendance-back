package com.attend.attendance_api.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

//[https://velog.io/@iknow/Spring-Security-Kotlin-DSL%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-CORS-%EC%84%A4%EC%A0%95%ED%95%B4%EB%B3%B4%EC%9E%90]
//↑リンクは私が参考したサイトです。
@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:30000") //
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowCredentials(true)
            .exposedHeaders("Set-Cookie");
    }
}