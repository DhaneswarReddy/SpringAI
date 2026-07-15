package com.example.SpringAi;


import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.ollama.api.OllamaModel;
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
	private OllamaChatModel ollamaChatModel;
	
	public Controller(OpenAiChatModel chatmodel, OllamaChatModel ollamaChatModel) {
		this.chatmodel = chatmodel;
		this.ollamaChatModel = ollamaChatModel;
	}
	
	@GetMapping("/openai/{message}")
	public ResponseEntity<String> openAi(@PathVariable String message) {
		
		String response = chatmodel.call(message);
		return ResponseEntity.ok(response);
	}

    @GetMapping("/ollama/{message}")
	public ResponseEntity<String> ollamaChatModel(@PathVariable String message) {
		ChatResponse response = ollamaChatModel.call(
    new Prompt(
        message,
        OllamaChatOptions.builder()
            .model(OllamaModel.LLAMA3_2_3B)
            .temperature(0.4)
            .build()
    ));
		return ResponseEntity.ok(response.getResult().getOutput().getText());
	}
}
