package com.zafra.starterapp.controllers;

import com.zafra.starterapp.handlers.Handler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	private Handler handler;

	@Autowired
	public HelloController(Handler handler) {
		this.handler = handler;
	}

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/handler")
	public String callHandler() {
		return handler.handle();
	}

	@GetMapping("/dependedhandler")
	public String callDependedHandler() {
		return handler.dependedHandlerMessage();
	}

}