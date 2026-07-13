package com.example.SpringAi;


import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {
	
	private OpenAiChatModel chatmodel;
	
	public Controller(OpenAiChatModel chatmodel) {
		this.chatmodel = chatmodel;
	}
	
	@GetMapping("/{message}")
	public ResponseEntity<String> openAi(@PathVariable String message) {
		
		String response = chatmodel.call(message);
		return ResponseEntity.ok(response);
	}
}
