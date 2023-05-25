package com.cf.tool_renter.value_object;
import lombok.*;

@Getter
public class ToolType {
    private String type;
    private String brand;
    private double dailyRentalCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;
}
