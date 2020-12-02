package com.chards.committee.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Jackson2Customizer {
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return jacksonObjectMapperBuilder -> {
			//修复：前端js 精度问题
			jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
			jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
			jacksonObjectMapperBuilder.serializerByType(long.class, ToStringSerializer.instance);
			jacksonObjectMapperBuilder.simpleDateFormat(DATE_TIME_FORMAT);
		};
	}
}