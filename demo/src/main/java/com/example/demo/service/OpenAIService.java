package com.example.demo.service;

import com.example.demo.model.PostEntity;
import com.example.demo.repository.PostRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class OpenAIService {
    @Autowired
    private PostRepository postRepository;
    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    public String chatWithGPT(String userMessage) throws IOException {
        StringBuilder context = new StringBuilder("Bạn là một chatbot hỗ trợ của mạng xã hội Devers do team 2 lớp OOP Thầy Sơn làm ra. Thành viên trong nhóm có" +
                " Hoàng Hải Long, Nguyễn Thị Ngân, Đoàn Thảo Vân, Trần Khánh Nhật, Nguyễn Đình Phúc. " +
                "Mạng xã hội này được lập ra để giải đáp các thắc mắc của người dùng khi code. " +
                "Người dùng có thể nhắn tin thời gian thực với người đã đăng bài để hỏi trực tiếp về vấn đề còn thắc mắc." +
                " Người dùng có thể tìm kiếm và xem các nội dung đã được đăng, " +
                "được đề xuất theo nhu cầu quan tâm của họ (Thông qua các tags đã chọn lúc đăng ký tài khoản)." +
                " Người dùng cũng có thể đăng những giải pháp với những vấn đề mà mình đã giải quyết được. " +
                "Tên mạng xã hội này là DEVERS, viết tắt cho DEVELOPERS"+
                "Đây là các bài viết hiện có, nếu người dùng viết vào 1 lỗi, hãy phân tích lỗi đó nằm ở lĩnh vực nào và đề xuất các bài viết có trùng lĩnh vực "+
                "Nếu người dùng yêu cầu liệt kê hay tìm kiếm gì, chỉ trả lời tối đa 3 ý, không cần tìm hết. " +
                "Nội dung trả lời nên ngắn gọn sao cho dễ hiểu nhất, trả lời ngắn!");
        for (PostEntity postEntity : postRepository.findAll()){
            context.append(postEntity.getTitle());
        }

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode messages = mapper.createArrayNode();
        messages.add(mapper.createObjectNode().put("role", "system").put("content", context.toString()));
        messages.add(mapper.createObjectNode().put("role", "user").put("content", userMessage));

        String jsonBody = mapper.createObjectNode()
                .put("model", "gpt-4")
                .put("max_tokens", 500)
                .set("messages", messages)
                .toString();

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

            JsonNode jsonResponse = mapper.readTree(response.body().string());
            JsonNode choices = jsonResponse.get("choices");
            if (choices == null || choices.isEmpty()) {
                throw new IOException("No choices found in the response.");
            }
            return choices.get(0).get("message").get("content").asText();
        }
    }
}