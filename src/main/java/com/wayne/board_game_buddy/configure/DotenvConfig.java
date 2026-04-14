package com.wayne.board_game_buddy.configure;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {
    @PostConstruct
    public void init(){
        // 加载 .env 文件
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load();
        // 将 .env 中的变量设置到系统属性中
        dotenv.entries().forEach(entry -> {
            String key = entry.getKey();
            String value = entry.getValue();

            // 设置到系统属性（Spring Boot 会从系统属性读取）
            System.setProperty(key, value);

            // 同时设置到环境变量（可选）
            // 注意：这种方式只对当前进程有效
            System.out.println("✅ 加载环境变量: " + key + " = " +
                    (key.toLowerCase().contains("key") || key.toLowerCase().contains("secret") ?
                            "***" + (value.length() > 3 ? value.substring(value.length() - 3) : "***") :
                            value));
        });


    }
}
