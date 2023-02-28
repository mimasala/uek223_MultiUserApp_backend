package com.example.demo;

import com.example.demo.domain.recommender.Gorse;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Log4j2
public class DemoApplication {
  @Bean
  Gorse getGorseClient() {
    log.debug("Creating connection to gorse on: http://localhost:8088");
    return new Gorse("http://localhost:8088", "Depenendcy");
  }
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}