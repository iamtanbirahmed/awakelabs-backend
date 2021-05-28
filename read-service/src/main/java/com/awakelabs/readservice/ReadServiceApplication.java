package com.awakelabs.readservice;

import com.redislabs.redistimeseries.RedisTimeSeries;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@EnableEncryptableProperties
@SpringBootApplication
public class ReadServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadServiceApplication.class, args);
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

