package com.barisd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

	@GetMapping("/auth")
	public ResponseEntity<String> getFallbackAuth() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Auth service şu an hizmet verememektedir");
	}

	@GetMapping("/profile")
	public ResponseEntity<String> getFallbackUserProfile() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Profile service şu an hizmet verememektedir");
	}

	@GetMapping("/email")
	public ResponseEntity<String> getFallbackEmail() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email service şu an hizmet verememektedir");
	}

	@GetMapping("/shopping")
	public ResponseEntity<String> getFallbackShopping() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Shopping service şu an hizmet verememektedir");
	}

	@GetMapping("/cart")
	public ResponseEntity<String> getFallbackCart() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Inventory service şu an hizmet verememektedir");
	}

	@GetMapping("/address")
	public ResponseEntity<String> getFallbackAddress() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Profile service şu an hizmet verememektedir");
	}

	@GetMapping("/urun")
	public ResponseEntity<String> getFallbackUrun() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Inventory service şu an hizmet verememektedir");
	}


}