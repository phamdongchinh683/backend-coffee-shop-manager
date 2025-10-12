package com.example.coffee_shop_manage_api.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {
  @Bean
  public NewTopic tableStatusTopic() {
    return TopicBuilder.name("table-status")
        .partitions(2)
        .replicas(1) 
        .build();
  }
}
