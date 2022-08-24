package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;


//웹설정파일
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
@Value("${file.path}")
private String uploadFolder;


@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		registry.addResourceHandler("/upload/**")
		.addResourceLocations("file:///"+uploadFolder)//jsp페이지에서 /upload/**이런  주소페턴이 나오면 발동 1시간동안발동
		.setCachePeriod(60*10*6)
		.resourceChain(true)
		.addResolver(new PathResourceResolver());
		//jsp페이지에서 /upload/**이런  주소페턴이 나오면 발동 1시간동안발동
	}

}
