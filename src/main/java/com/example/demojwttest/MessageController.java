package com.example.demojwttest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
	@GetMapping(path = "messages")
	public List<String> getMessages() {
		return List.of("hello", "world");
	}
}
