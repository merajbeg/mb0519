package com.cf.tool_renter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cf.tool_renter.exception.ToolRentalException;
import com.cf.tool_renter.service.RentalAgreement;
import com.cf.tool_renter.service.ToolRentalService;
import com.cf.tool_renter.value_object.RentalRequest;

@RestController
@RequestMapping("v1/api/tools")
public class ToolRentalController {

    private final ToolRentalService toolRentalService;

    @Autowired
    public ToolRentalController(ToolRentalService toolRentalService) {
        this.toolRentalService = toolRentalService;
    }

    @PostMapping("/rent")
    public ResponseEntity<RentalAgreement> rentTool(@RequestBody RentalRequest rentalRequest) {
        try {
            System.out.println(rentalRequest.getCheckoutDate() + rentalRequest.getToolCode());
            RentalAgreement rentalAgreement = toolRentalService.rentTool(
                    rentalRequest.getToolCode(),
                    rentalRequest.getRentalDays(),
                    rentalRequest.getCheckoutDate(),
                    rentalRequest.getDiscountPercent()
            );
            return ResponseEntity.ok(rentalAgreement);
        } catch (ToolRentalException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/runTest")
    public ResponseEntity<String> runTest() {
        try {
            System.out.println("inside runTest");
            String returnResult = toolRentalService.runTest();
            return ResponseEntity.ok(returnResult);
        } catch (ToolRentalException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}


