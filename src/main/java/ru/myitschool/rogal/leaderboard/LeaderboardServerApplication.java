package ru.myitschool.rogal.leaderboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@SpringBootApplication
public class LeaderboardServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(LeaderboardServerApplication.class, args);
        
        Environment env = context.getEnvironment();
        String protocol = "http";
        String host = env.getProperty("server.address", InetAddress.getLocalHost().getHostAddress());
        String port = env.getProperty("server.port", "8080");
        String contextPath = env.getProperty("server.servlet.context-path", "");
        
        System.out.println("-----------------------------------------------");
        System.out.println("  Сервер таблицы лидеров запущен!");
        System.out.println("  API доступен по адресу: " + protocol + "://" + host + ":" + port + contextPath + "/api/leaderboard");
        System.out.println("-----------------------------------------------");
    }
} 