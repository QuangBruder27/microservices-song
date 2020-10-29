package htwb.ai.nguyenkbe.lyricservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


@SpringBootApplication
@EnableEurekaClient
public class LyricServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LyricServiceApplication.class, args);
	}

}
