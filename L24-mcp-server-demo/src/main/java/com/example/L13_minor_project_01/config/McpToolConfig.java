package com.example.L13_minor_project_01.config;

import com.example.L13_minor_project_01.mcp.AdminMcpTools;
import com.example.L13_minor_project_01.mcp.CustomerMcpTools;
import com.example.L13_minor_project_01.mcp.SellerMcpTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolConfig {

    @Bean
    public ToolCallbackProvider adminTools(AdminMcpTools adminMcpTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(adminMcpTools)
                .build();
    }

    @Bean
    public ToolCallbackProvider sellerTools(SellerMcpTools sellerMcpTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(sellerMcpTools)
                .build();
    }

    @Bean
    public ToolCallbackProvider customerTools(CustomerMcpTools customerMcpTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(customerMcpTools)
                .build();
    }
}

