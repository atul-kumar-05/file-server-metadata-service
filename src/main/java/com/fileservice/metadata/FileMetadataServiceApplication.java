package com.fileservice.metadata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FileMetadataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileMetadataServiceApplication.class, args);
    }

}
