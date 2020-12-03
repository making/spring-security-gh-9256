package com.example.demojwttest;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(properties = "spring.security.oauth2.resourceserver.jwt.issuer-uri=https://uaa.run.pcfone.io/oauth/token", controllers = MessageController.class)
class MessageControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Test
	void getMessages() throws Exception {
		this.mockMvc.perform(get("/messages")
				.with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_message:read"))))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").value("hello"))
				.andExpect(jsonPath("$[1]").value("world"));
	}

	@Test
	@WithMockUser(authorities = "SCOPE_message:read")
	void getMessagesWebTestClientWithMockUser() {
		final WebTestClient testClient = MockMvcWebTestClient.bindTo(this.mockMvc)
				.build();
		testClient.get()
				.uri("/messages")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$[0]").isEqualTo("hello")
				.jsonPath("$[1]").isEqualTo("world"); ;
	}

	@Test
	void getMessagesWebTestClient() {
		final WebTestClient testClient = MockMvcWebTestClient.bindTo(this.mockMvc)
				.build();
		testClient.mutateWith(mockJwt().authorities(new SimpleGrantedAuthority("SCOPE_message:read")))
				.get()
				.uri("/messages")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$[0]").isEqualTo("hello")
				.jsonPath("$[1]").isEqualTo("world"); ;
	}
}