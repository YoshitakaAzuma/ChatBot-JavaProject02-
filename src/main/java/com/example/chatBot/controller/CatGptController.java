package com.example.chatBot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.chatBot.service.ChatGptService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CatGptController {
	private final ChatGptService chatGptService;
	
	@GetMapping("/chat")
	public String start(@RequestParam(value = "message", required = false) String message,
            @RequestParam(value = "role", required = false) String role,Model model) {
		if (message != null) {
			model.addAttribute("message", message);
		}
		if (role != null) {
			model.addAttribute("role", role);
		}
		return "chat";
	}
	
	// フォームからのPOSTリクエストを処理します
    @PostMapping("/chat")
    public String chat(@RequestParam("message") String message,@RequestParam("role")String role, RedirectAttributes attributes) {
        // ChatGPTサービスからのレスポンスを取得
    	String response = chatGptService.getChatGptResponse(role,message);
		attributes.addFlashAttribute("role", role);
        
        // モデルにデータを追加
		attributes.addFlashAttribute("chatGptResponse", response);
        // chat.htmlテンプレートにデータを渡す
        return "redirect:/chat";
    }
	
	
	
	
}
