package com.techie.microservices.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import org.hamcrest.Matchers;

import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");
	
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static{
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String request = """
				{
					"name": "iPhone 14",
					"description": "Apple iPhone 14 with 128GB storage",
					"price": 999.99
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(request)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("iPhone 14"))
				.body("description", Matchers.equalTo("Apple iPhone 14 with 128GB storage"))
				.body("price", Matchers.equalTo(999.99f));

	}

}
