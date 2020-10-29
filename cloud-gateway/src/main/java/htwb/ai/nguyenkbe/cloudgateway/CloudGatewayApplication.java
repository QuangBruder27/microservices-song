package htwb.ai.nguyenkbe.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient

public class CloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder, MyFilter myFilter) {
		return builder.routes()
				.route(r -> r.path("/songs/**")
						//Pre and Post Filters provided by Spring Cloud Gateway
						.filters(f -> f.filter(myFilter.apply(new MyFilter.Config())))
						.uri("http://localhost:8100/")
						.id("song-service"))
				.route(r -> r.path("/songLists/**")
						//Pre and Post Filters provided by Spring Cloud Gateway
						.filters(f -> f.filter(myFilter.apply(new MyFilter.Config())))
						.uri("http://localhost:8100/")
						.id("song-service"))
				.route(r -> r.path("/lyric/**")
						//Pre and Post Filters provided by Spring Cloud Gateway
						.filters(f -> f.filter(myFilter.apply(new MyFilter.Config())))
						.uri("http://localhost:8300/")
						.id("lyric-service"))
				.route(r -> r.path("/auth/**")
						//Pre and Post Filters provided by Spring Cloud Gateway
						//.filters(f -> f.filter(myFilter.apply(new MyFilter.Config())))
						.uri("http://localhost:8200/")
						.id("auth-service"))

				.build();
	}


}
