package com.requst.wu;

import com.requst.wu.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;



@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class ReqApplication {
    
    public static void main(String[] args) {
       SpringApplication.run(ReqApplication.class, args);
    
    }
}


