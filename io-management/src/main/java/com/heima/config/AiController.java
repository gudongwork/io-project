package com.heima.config;

import com.heima.vo.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.aiUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiController {
    private final ChatClient chatClient;
    @GetMapping
    public String aiInputByString(String input){
        return this.chatClient.prompt()
                .user(input)
                .call().content();
    }
    @GetMapping("/session_id")
    public Result getSessionId(){
        Result result = new Result();
        result.setData(aiUtil.getUUID());
        return result;
    }
}
