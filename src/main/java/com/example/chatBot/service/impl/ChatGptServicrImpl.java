package com.example.chatBot.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.chatBot.service.ChatGptService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatGptServicrImpl implements ChatGptService {

	// APIキーを環境変数または設定ファイルから取得します
	@Value("${openai.api.key}")
	private String apiKey;

	// OpenAIのAPIエンドポイントURL
	private final String API_URL = "https://api.openai.com/v1/chat/completions";
	private final RestTemplate restTemplate;

	// ユーザーからのメッセージをChatGPT APIに送信し、レスポンスを取得します

	@Override
	public String getChatGptResponse(String role, String userMessage) {
		role = role.replace("\n", "");
		userMessage = userMessage.replaceAll("\\r\\n|\\r|\\n", "");
		String message = "";
		if (role.length() > 0) {
			message = role + "になりきって以降の問いかけに返答してください。" + userMessage;
		} else {
			message = userMessage;
		}

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + apiKey); // APIキーをヘッダーに設定
		headers.set("Content-Type", "application/json"); // リクエストのコンテンツタイプを設定

		// APIリクエストのボディを作成
		//String body = String.format("{\"model\":\"gpt-4\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}], \"temperature\": 1.0}", message);
		String body = String.format(
				"{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}], \"temperature\": 0}",
				message);
		//String body = "{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"" + message + "\"}]}";
		//String body = "{\"model\":\"gpt-4\",\"messages\":[{\"role\":\"user\",\"content\":\"" + message + "\"}]}";
		HttpEntity<String> request = new HttpEntity<>(body, headers);

		// APIエンドポイントにPOSTリクエストを送信
		ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);

		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			// APIレスポンスからメッセージコンテンツを抽出
			return root.path("choices").get(0).path("message").path("content").asText();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error: Unable to process the response.";
		}
	}

}
