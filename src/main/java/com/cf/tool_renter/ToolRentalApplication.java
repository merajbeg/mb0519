package com.cf.tool_renter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ToolRentalApplication {
    private static final String TOOL_TYPES_FILE_PATH = "tool_types.json";
    private static final Logger LOGGER = Logger.getLogger(ToolRentalApplication.class.getName());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yy");


    public static void main(String[] args) {
        try {
            LOGGER.info("Application Started!");
            Map<String, ToolType> toolTypes = readToolTypesFromFile(TOOL_TYPES_FILE_PATH);
            LOGGER.info("Tool info json file successfully read");
            ToolRentalApplication application = new ToolRentalApplication(toolTypes);
            application.run();
        } catch (IOException e) {
            //TODO: for Internaltionalization, better control and best practice, below messages should come from bundle/resource files instead of hardcoding here.
            LOGGER.info("Error reading tool types from file: " + e.getMessage());
            System.out.println("Application is having a difficulty processing your request. Please reach out to the support with code: 123456, for further help");

        }
    }

    private final Map<String, ToolType> toolTypes;

    public ToolRentalApplication(Map<String, ToolType> toolTypes) {
        this.toolTypes = toolTypes;
    }

    private void run() {
        // Rent multiple tools
        rentTool("JAKR", 5, LocalDate.of(2015, 9, 3), 101);
        rentTool("LADW", 3, LocalDate.of(2020, 7, 2), 10);
        rentTool("CHNS", 5, LocalDate.of(2015, 7, 2), 25);
        rentTool("JAKR", 6, LocalDate.of(2015, 9, 3), 0);
        rentTool("JAKR", 9, LocalDate.of(2015, 7, 2), 0);
        rentTool("JAKR", 4, LocalDate.of(2020, 7, 2), 50);
    }

    private void rentTool(String toolCode, int rentalDays, LocalDate checkoutDate, double discountPercent) {
        ToolType toolType = toolTypes.get(toolCode);
        if (toolType != null) {
            Tool tool = new Tool(toolCode, toolType.getType(), toolType.getBrand(), toolType.getDailyRentalCharge(),
            toolType.isWeekdayCharge(), toolType.isWeekendCharge(), toolType.isHolidayCharge());

            RentalAgreement rentalAgreement = new RentalAgreement(tool, rentalDays, checkoutDate, discountPercent);
            rentalAgreement.printRentalAgreement();
            System.out.println(); // Add empty line between rental agreements
        } else {
            System.out.println("Tool type not found for tool code: " + toolCode);
        }
    }

    private static Map<String, ToolType> readToolTypesFromFile(String filePath) throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonContent, new TypeReference<HashMap<String, ToolType>>() {});
    }

    private static class ToolType {
        private String type;
        private String brand;
        private double dailyRentalCharge;
        private boolean weekdayCharge;
        private boolean weekendCharge;
        private boolean holidayCharge;

        // Getters and setters omitted for brevity

        public String getType() {
            return type;
        }

        public String getBrand() {
            return brand;
        }

        public double getDailyRentalCharge() {
            return dailyRentalCharge;
        }

        public boolean isWeekdayCharge() {
            return weekdayCharge;
        }

        public boolean isWeekendCharge() {
            return weekendCharge;
        }

        public boolean isHolidayCharge() {
            return holidayCharge;
        }

    }
}
