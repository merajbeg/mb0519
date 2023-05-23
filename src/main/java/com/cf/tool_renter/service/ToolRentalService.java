package com.cf.tool_renter.service;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cf.tool_renter.ToolRentalApplication;
import com.cf.tool_renter.exception.ToolRentalException;

@Service
public class ToolRentalService {

    private final ToolRentalApplication toolRentalApplication;

    @Autowired
    public ToolRentalService(ToolRentalApplication toolRentalApplication) {
        this.toolRentalApplication = toolRentalApplication;
    }

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
            return toolRentalApplication.rentTool(toolCode, rentalDays, checkoutDate, discountPercent);
        } catch (Exception e) {
            throw new ToolRentalException("Error renting the tool.", e);
        }
    }
}

