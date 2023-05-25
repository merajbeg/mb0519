package com.cf.tool_renter.service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.tool_renter.exception.ToolRentalException;
import com.cf.tool_renter.repository.ToolTypeRepository;
import com.cf.tool_renter.value_object.Tool;
import com.cf.tool_renter.value_object.ToolType;

import jakarta.annotation.PostConstruct;

@Service
public class ToolRentalService {

    private final ToolTypeRepository toolTypeRepository;
    private Map<String, ToolType> toolTypes;

    //private final ToolRentalApplication toolRentalApplication;

    @Autowired
    public ToolRentalService(ToolTypeRepository toolTypeRepository) {
        this.toolTypeRepository = toolTypeRepository;
    }
    
    @PostConstruct
    public void init() {
        toolTypes = new HashMap<>();
        toolTypeRepository.getToolTypes().forEach((key, toolType) -> toolTypes.put(key, toolType));

    }

    public ToolType getToolType(String id) {
        return toolTypes.get(id);
    }

    public Map<String, ToolType> getAllToolTypes() {
        return toolTypes;
    }

    /* @Autowired
    public ToolRentalService(ToolRentalApplication toolRentalApplication) {
        this.toolRentalApplication = toolRentalApplication;
    } */

    public RentalAgreement rentTool(String toolCode, int rentalDays, LocalDate checkoutDate, double discountPercent)
            throws ToolRentalException {
        // Validate the rental request
        if (rentalDays < 1) {
            throw new ToolRentalException("Rental days should be at least 1.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new ToolRentalException("Discount percent should be between 0 and 100.");
        }

        // Rent the tool
        try {
            return rentToolService(toolCode, rentalDays, checkoutDate, discountPercent);
        } catch (Exception e) {
            throw new ToolRentalException("Error renting the tool.", e);
        }
    }

    public String runTest()
            throws ToolRentalException {
        // Rent the tool
        try {
            return runTestService();
        } catch (Exception e) {
            throw new ToolRentalException("Error running tests.", e);
        }
    }    

    public RentalAgreement rentToolService(String toolCode, int rentalDays, LocalDate checkoutDate, double discountPercent) {
        if(toolTypes.size() <= 0)
            //fetchToolRecrods();

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

    public String runTestService() {
        // Rent multiple tools
        System.out.println();
        System.out.println("################BELOW ARE THE TEST RESULTS################");
        System.out.println("#####TEST1 RESULTs: Tool code JAKR, Checkout date 9/3/15, Rental days 5, Discount 101%");
        rentToolService("JAKR", 5, LocalDate.of(2015, 9, 3), 101);
        System.out.println();
        System.out.println("#####TEST2 RESULTs: Tool code LADW, Checkout date 7/2/20, Rental days 3, Discount 10%");
        rentToolService("LADW", 3, LocalDate.of(2020, 7, 2), 10);
        System.out.println();
        System.out.println("#####TEST3 RESULTs: Tool code 7/2/20, Checkout date 7/2/15, Rental days 5, Discount 25%");
        rentToolService("CHNS", 5, LocalDate.of(2015, 7, 2), 25);
        System.out.println();
        System.out.println("#####TEST4 RESULTs: Tool code JAKD, Checkout date 9/3/15, Rental days 6, Discount 0%");
        rentToolService("JAKR", 6, LocalDate.of(2015, 9, 3), 0);
        System.out.println();
        System.out.println("#####TEST5 RESULTs: Tool code JAKR, Checkout date 7/2/15, Rental days 9, Discount 0%");
        rentToolService("JAKR", 9, LocalDate.of(2015, 7, 2), 0);
        System.out.println();
        System.out.println("#####TEST6 RESULTs: Tool code JAKR, Checkout date 7/2/20, Rental days 5, Discount 50%");
        rentToolService("JAKR", 4, LocalDate.of(2020, 7, 2), 50);
        System.out.println();
        System.out.println("################TEST RESULTS ENDED################");
        return "Test successful";
    }   


}

