package com.cf.tool_renter;

import java.util.logging.Logger;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.*;

@SpringBootApplication
public class ToolRentalApplication {
    private static final Logger LOGGER = Logger.getLogger(ToolRentalApplication.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Application is starting!");
        SpringApplication.run(ToolRentalApplication.class, args);
        LOGGER.info("Application Started!");
        //fetchToolRecrods();
    }

}
