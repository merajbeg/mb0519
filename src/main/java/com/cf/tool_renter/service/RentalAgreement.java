package com.cf.tool_renter.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.cf.tool_renter.value_object.*;
import java.io.Serializable;

public class RentalAgreement implements Serializable {
    private final Tool tool;
    private final int rentalDays;
    private final LocalDate checkoutDate;
    private final double discountPercent;

    public RentalAgreement(Tool tool, int rentalDays, LocalDate checkoutDate, double discountPercent) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.discountPercent = discountPercent;
    }

    private boolean isWeekendCharge() {
        return tool.isWeekendCharge();
    }

    private boolean isHolidayCharge() {
        return tool.isHolidayCharge() && (isIndependenceDay(checkoutDate) || isLaborDay(checkoutDate));
    }

    private boolean isIndependenceDay(LocalDate date) {
        if (date.getMonth().getValue() == 7 && date.getDayOfMonth() == 4) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
        }
        return false;
    }

    private boolean isLaborDay(LocalDate date) {
        return date.getMonth().getValue() == 9 && date.getDayOfWeek() == DayOfWeek.MONDAY &&
                date.getDayOfMonth() <= 7;
    }

    private boolean isWeekdayCharge() {
        return !isWeekendCharge() && !isHolidayCharge();
    }

    private int calculateChargeDays() {
        int chargeDays = 0;
        LocalDate currentDate = checkoutDate.plusDays(1);

        for (int i = 0; i < rentalDays; i++) {
            if (isChargeableDay(currentDate)) {
                chargeDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return chargeDays;
    }

    private boolean isChargeableDay(LocalDate date) {
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return isWeekendCharge() || isHolidayCharge();
        } else {
            return isWeekdayCharge();
        }
    }

    private double calculatePreDiscountCharge() {
        int chargeDays = calculateChargeDays();
        return chargeDays * tool.getDailyRentalCharge();
    }

    private double calculateDiscountAmount() {
        double preDiscountCharge = calculatePreDiscountCharge();
        return preDiscountCharge * (discountPercent / 100);
    }

    public double getFinalCharge() {
        double preDiscountCharge = calculatePreDiscountCharge();
        double discountAmount = calculateDiscountAmount();
        return preDiscountCharge - discountAmount;
    }

    public void printRentalAgreement() {
        System.out.println("Tool code: " + tool.getToolCode());
        System.out.println("Tool type: " + tool.getToolType());
        System.out.println("Tool brand: " + tool.getBrand());
        System.out.println("Rental days: " + rentalDays);
        System.out.println("Check out date: " + checkoutDate.format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        System.out.println("Due date: " + checkoutDate.plusDays(rentalDays).format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        System.out.println("Daily rental charge: $" + String.format("%.2f", tool.getDailyRentalCharge()));
        System.out.println("Charge days: " + calculateChargeDays());
        System.out.println("Pre-discount charge: $" + String.format("%.2f", calculatePreDiscountCharge()));
        System.out.println("Discount percent: " + discountPercent + "%");
        System.out.println("Discount amount: $" + String.format("%.2f", calculateDiscountAmount()));
        System.out.println("Final charge: $" + String.format("%.2f", getFinalCharge()));
    }
}
