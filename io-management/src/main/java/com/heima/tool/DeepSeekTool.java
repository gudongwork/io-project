package com.heima.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class DeepSeekTool {
    @Tool(name = "deepseek_first_tool",description = "第一个工具")
    public String deepSeekFirstTool(){
        return null;
    }
}
