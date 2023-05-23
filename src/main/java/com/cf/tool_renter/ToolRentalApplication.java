package com.cf.tool_renter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.time.format.DateTimeFormatter;

import com.cf.tool_renter.service.RentalAgreement;
import com.cf.tool_renter.value_object.Tool;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import lombok.*;

@SpringBootApplication
@ComponentScan (basePackages = "com.cf.tool_renter")
public class ToolRentalApplication {
    private static final String TOOL_TYPES_FILE_PATH = "tool_types.json";
    private static final Logger LOGGER = Logger.getLogger(ToolRentalApplication.class.getName());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");


    public static void main(String[] args) {
        SpringApplication.run(ToolRentalApplication.class, args);
        //fetchToolRecrods();
    }

    private void fetchToolRecrods() {
        try {
            LOGGER.info("Application Started!");
            Map<String, ToolType> toolTypes = readToolTypesFromFile(TOOL_TYPES_FILE_PATH);
            LOGGER.info("Tool info json file successfully read");
            ToolRentalApplication application = new ToolRentalApplication(toolTypes);
            //application.run();
        } catch (IOException e) {
            //TODO: for Internaltionalization, better control and best practice, below messages should come from bundle/resource files instead of hardcoding here.
            LOGGER.info("Error reading tool types from file: " + e.getMessage());
            System.out.println("Application is having a difficulty processing your request. Please reach out to the support with code: 123456, for further help");

        }
    }

    static Map<String, ToolType> toolTypes;
    static {
        try {
        toolTypes = readToolTypesFromFile(TOOL_TYPES_FILE_PATH);
    } catch (IOException e) {
        //TODO: for Internaltionalization, better control and best practice, below messages should come from bundle/resource files instead of hardcoding here.
        LOGGER.info("Error reading tool types from file: " + e.getMessage());
        System.out.println("Application is having a difficulty processing your request. Please reach out to the support with code: 123456, for further help");

    }
    }

    public ToolRentalApplication(Map<String, ToolType> toolTypes) {
        this.toolTypes = toolTypes;
    }

    public String runTest() {
        // Rent multiple tools
        System.out.println();
        System.out.println("################BELOW ARE THE TEST RESULTS################");
        System.out.println("#####TEST1 RESULTs: Tool code JAKR, Checkout date 9/3/15, Rental days 5, Discount 101%");
        rentTool("JAKR", 5, LocalDate.of(2015, 9, 3), 101);
        System.out.println();
        System.out.println("#####TEST2 RESULTs: Tool code LADW, Checkout date 7/2/20, Rental days 3, Discount 10%");
        rentTool("LADW", 3, LocalDate.of(2020, 7, 2), 10);
        System.out.println();
        System.out.println("#####TEST3 RESULTs: Tool code 7/2/20, Checkout date 7/2/15, Rental days 5, Discount 25%");
        rentTool("CHNS", 5, LocalDate.of(2015, 7, 2), 25);
        System.out.println();
        System.out.println("#####TEST4 RESULTs: Tool code JAKD, Checkout date 9/3/15, Rental days 6, Discount 0%");
        rentTool("JAKR", 6, LocalDate.of(2015, 9, 3), 0);
        System.out.println();
        System.out.println("#####TEST5 RESULTs: Tool code JAKR, Checkout date 7/2/15, Rental days 9, Discount 0%");
        rentTool("JAKR", 9, LocalDate.of(2015, 7, 2), 0);
        System.out.println();
        System.out.println("#####TEST6 RESULTs: Tool code JAKR, Checkout date 7/2/20, Rental days 5, Discount 50%");
        rentTool("JAKR", 4, LocalDate.of(2020, 7, 2), 50);
        System.out.println();
        System.out.println("################TEST RESULTS ENDED################");
        return "Test successful";
    }

    public RentalAgreement rentTool(String toolCode, int rentalDays, LocalDate checkoutDate, double discountPercent) {
        if(toolTypes.size() <= 0)
            fetchToolRecrods();

        System.out.println("tool code=" + toolCode);
        ToolType toolType = toolTypes.get(toolCode);
        RentalAgreement rentalAgreement = null;
        if (toolType != null) {
            Tool tool = new Tool(toolCode, toolType.getType(), toolType.getBrand(), toolType.getDailyRentalCharge(),
            toolType.isWeekdayCharge(), toolType.isWeekendCharge(), toolType.isHolidayCharge());

            rentalAgreement = new RentalAgreement(tool, rentalDays, checkoutDate, discountPercent);
            rentalAgreement.printRentalAgreement();
            System.out.println(); // Add empty line between rental agreements
        } else {
            System.out.println("Tool type not found for tool code: " + toolCode);
        }
        return rentalAgreement;
    }

    private static Map<String, ToolType> readToolTypesFromFile(String filePath) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonContent, new TypeReference<HashMap<String, ToolType>>() {});
    }

    /**
     * Inner class with lombok support for getters
     */
    @Getter
    private static class ToolType {
        private String type;
        private String brand;
        private double dailyRentalCharge;
        private boolean weekdayCharge;
        private boolean weekendCharge;
        private boolean holidayCharge;
    }
}
