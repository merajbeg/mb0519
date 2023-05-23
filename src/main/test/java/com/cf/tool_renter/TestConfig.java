package com.cf.tool_renter;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public ToolRentalApplication toolRentalApplication() {
        return new ToolRentalApplication();
    }

}
