package com.example.demo;

import io.gorse.gorse4j.Gorse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
  @Bean
  Gorse getGorseClient() {
    return new Gorse("http://localhost:8088", "Depenendcy");
  }
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}