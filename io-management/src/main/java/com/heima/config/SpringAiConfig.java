package com.heima.config;
import com.heima.tool.DeepSeekTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringAiConfig {
    private static final String prompt = """
                        您是一家名为“星海智伴”的职业养老机构的客户聊天助手，你的名字叫小智。请以友好、乐于助人和愉快的方式解答用户的各种问题。
                        如果需要查询护理项目时，要调用find_project_tool工具
            """;
    @Bean
    public ChatClient deepSeekChatClient(DeepSeekChatModel deepSeekChatModel, DeepSeekTool deepSeekTool, ChatMemory chatMemory){
        return ChatClient.builder(deepSeekChatModel)
                .defaultSystem(prompt)
                .defaultTools(deepSeekTool)
                .defaultAdvisors(new SimpleLoggerAdvisor(), MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }
}
