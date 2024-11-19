package com.example.demo.service;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@Service
public class OpenAIService {

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();

    public String chatWithGPT(String userMessage) throws IOException {
        String jsonBody = String.format("""
                    {
                        "model": "gpt-4",
                        "messages": [
                            {"role": "system", "content": "Bạn là một chatbot hỗ trợ của mạng xã hội Devers do team 2 lớp OOP Thầy Sơn làm ra. Thành viên trong nhóm có Hoàng Hải Long, Nguyễn Thị Ngân, Đoàn Thảo Vân, Trần Khánh Nhật, Nguyễn Đình Phúc. Mạng xã hội này được lập ra để giải đáp các thắc mắc của người dùng khi code. Người dùng có thể nhắn tin thời gian thực với người đã đăng bài để hỏi trực tiếp về vấn đề còn thắc mắc. Người dùng có thể tìm kiếm và xem các nội dung đã được đăng, được đề xuất theo nhu cầu quan tâm của họ (Thông qua các tags đã chọn lúc đăng ký tài khoản). Người dùng cũng có thể đăng những giải pháp với những vấn đề mà mình đã giải quyết được. Tên mạng xã hội này là DEVERS, viết tắt cho DEVELOPERS, hãy trả lời các câu hỏi của người dùng dưới 200 token, nếu câu nào mà dài hơn thì có thể tăng thêm chút. Nên nhớ bạn là  chatbot hỗ trợ của mạng xã hội Devers"},
                            {"role": "user", "content": "%s"}
                        ],
                         "max_tokens": 500
                                
                    }
                """, userMessage);

        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Response Code: " + response.code());
                System.out.println("Response Body: " + response.body().string());
                throw new IOException("Unexpected code " + response);
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(response.body().string());
            JsonNode choices = jsonResponse.get("choices");
            if (choices == null || choices.isEmpty()) {
                throw new IOException("No choices found in the response.");
            }
            return choices.get(0).get("message").get("content").asText();
        }
    }
}
