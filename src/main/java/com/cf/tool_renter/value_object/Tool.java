package com.cf.tool_renter.value_object;
import lombok.*;

@Getter
public class Tool {
    private final String toolCode;
    private final String toolType;
    private final String brand;
    private final double dailyRentalCharge;
    private final boolean weekdayCharge;
    private final boolean weekendCharge;
    private final boolean holidayCharge;

    public Tool(String toolCode, String toolType, String brand, double dailyRentalCharge,
                boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
        this.dailyRentalCharge = dailyRentalCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

   
}

