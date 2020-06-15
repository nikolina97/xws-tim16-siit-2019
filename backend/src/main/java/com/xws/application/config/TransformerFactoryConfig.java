package com.xws.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;

@Configuration
public class TransformerFactoryConfig {

	    @Bean
	    public TransformerFactoryImpl transformerFactory() {
	        return new TransformerFactoryImpl();
	    }
}
