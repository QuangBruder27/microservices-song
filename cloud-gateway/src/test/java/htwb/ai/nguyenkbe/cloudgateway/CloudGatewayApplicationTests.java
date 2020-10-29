package htwb.ai.nguyenkbe.cloudgateway;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = {"httpbin=http://localhost:${wiremock.server.port}"})
@AutoConfigureWireMock(port = 0)
@AutoConfigureWebTestClient
class CloudGatewayApplicationTests {

	@Test
	void main() {
		CloudGatewayApplication.main(new String[] {});
	}

	@Test
	void gatewayRoutes() {

	}

	 int port = 0;
	protected String baseUri;
	@Autowired
	private WebTestClient webClient;


	@Test
	public void contextLoads() {
		//webClient.get().uri("/get").exchange().expectStatus().isOk();
	}

	/*
	@Test
	public void readBodyPredicateStringWorks() {
		webClient.get().uri("/anything/png").header("Host", "www.abc.org").exchange()
				.
				.expectHeader().valueEquals("X-TestHeader", "foobar")
				.expectStatus().isOk();

	}

	 */

	@Autowired
	private ApplicationContext context;

	@Test
	public void testWithoutTokenShould401() {
		WebTestClient client = WebTestClient.bindToApplicationContext(this.context)
				.build();
		client.get().uri("/lyric").exchange().expectStatus().isUnauthorized();
	}


	@Test
	public void testWithVaildTokenShouldRouted() {
		WebTestClient client = WebTestClient.bindToApplicationContext(this.context)
				.build();
		client.get().uri("/lyric").header("Authorization","default-token")
				.exchange()
				.expectStatus().is5xxServerError();
	}




}
