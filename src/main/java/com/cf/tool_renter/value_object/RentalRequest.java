package com.cf.tool_renter.value_object;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
public class RentalRequest {
    private String toolCode;
    private int rentalDays;
    private LocalDate checkoutDate;
    private double discountPercent;

}
