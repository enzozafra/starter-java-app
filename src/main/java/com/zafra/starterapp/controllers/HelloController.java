package com.zafra.starterapp.controllers;

import com.zafra.starterapp.handlers.questrade.Questrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	private Questrade questrade;

	@Autowired
	public HelloController(Questrade questrade) {
		this.questrade = questrade;
	}

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/handler")
	public String callHandler() {
		return "Check da logs brah";
	}

}
