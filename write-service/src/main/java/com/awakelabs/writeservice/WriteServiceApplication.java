package com.awakelabs.writeservice;

import com.redislabs.redistimeseries.RedisTimeSeries;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WriteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WriteServiceApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public RedisTimeSeries redisTimeSeries() {
        return new RedisTimeSeries("localhost", 6379);
    }


}
