package htwb.ai.nguyenkbe.songservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.List;

@SpringBootApplication
@EnableEurekaClient

public class SongServiceApplication implements WebMvcConfigurer {


	public static void main(String[] args) {
		SpringApplication.run(SongServiceApplication.class, args);
	}

}
