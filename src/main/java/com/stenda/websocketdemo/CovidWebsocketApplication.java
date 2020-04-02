package com.stenda.websocketdemo;

import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.fasterxml.jackson.core.JsonProcessingException;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;

@SpringBootApplication(scanBasePackages = "com.stenda")
@EnableJpaAuditing
public class CovidWebsocketApplication {
	
	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(CovidWebsocketApplication.class, args);
		
	}
	
	@Bean
	@Description("Thymeleaf Template Resolver")
	public ITemplateResolver templateResolver() {
		
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver(getClass().getClassLoader());
	    templateResolver.setPrefix("/static/views/");
	    templateResolver.setSuffix(".html");
	    templateResolver.setTemplateMode("HTML");
	 
	    return templateResolver;
	}
	
	@Bean
	@Description("Thymeleaf Template Engine")
	public SpringTemplateEngine templateEngine() {
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver());
	    templateEngine.addDialect(new LayoutDialect(new GroupingStrategy()));
	    //templateEngine.setTemplateEngineMessageSource(messageSource());
	    return templateEngine;
	}
	
	@Bean
	@Description("Thymeleaf View Resolver")
	public ThymeleafViewResolver viewResolver() {
	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	    viewResolver.setTemplateEngine(templateEngine());
	    viewResolver.setOrder(1);
	    return viewResolver;
	}	
	
	@Bean
	public AuditorAware<String> auditor(){
		return new AuditorAware<String>() {
			
			@Override
			public Optional<String> getCurrentAuditor() {
				return Optional.of("admin");
			}
		};
	}
	
}
