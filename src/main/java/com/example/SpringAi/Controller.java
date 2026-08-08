package com.example.SpringAi;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openai.models.ChatModel;

@RestController
@RequestMapping("/api")
public class Controller {
	
	private OpenAiChatModel chatmodel;
	private OllamaChatModel ollamaChatModel;
	
    //	Bedrock service
	private final ChatClient chatClient;
	
	public Controller(OpenAiChatModel chatmodel,OllamaChatModel ollamaChatModel, @Qualifier("bedrockProxyChatModel")
    org.springframework.ai.chat.model.ChatModel chatModel) {
		this.chatmodel = chatmodel;
		this.ollamaChatModel = ollamaChatModel;
		 this.chatClient =  ChatClient.builder(chatModel).build();
	}
	
//	Direct use of open-ai key
	@GetMapping("/openai/{message}")
	public ResponseEntity<String> openAi(@PathVariable String message) {
		
		String response = chatmodel.call(message);
		return ResponseEntity.ok(response);
	}

//	Through AWS Bedrock services
	 @GetMapping("/chat/{message}")
	    public ResponseEntity<String> chat(@PathVariable String message) {

	        String response = chatClient.prompt()
	                .user(message)
	                .call()
	                .content();

	        return ResponseEntity.ok(response.toString());
	    }
	 
	 
	 //Ollama which is running in my local
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
