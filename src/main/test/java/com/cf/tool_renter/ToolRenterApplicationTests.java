package com.cf.tool_renter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cf.tool_renter.service.ToolRentalService;


@SpringBootTest
class ToolRenterApplicationTests {

	

	@Autowired
    ToolRentalService toolRentalService = ToolRentalService();

	@Test
	void contextLoads() {
	}

	@Test
    public void testRentalAgreementCalculationForInvalidDiscount() {
		System.out.println("1st Test");
        Assertions.assertThrows(ToolRentalException.class, () -> {
            toolRentalService.rentToolService("JAKR", LocalDate.of(2015, 9, 3), 5, 101);
        });
		System.out.println("1st Test ended");

    }

    @Test
    public void testRentalAgreementCalculationForValidDiscount() {
        RentalAgreement rentalAgreement = toolRentalService.checkout("LADW", LocalDate.of(2020, 7, 2), 3, 10);
        Assertions.assertEquals("LADW", rentalAgreement.getToolCode());
    }

    @Test
    public void testRentalAgreementCalculationForZeroRentalDays() {
        RentalAgreement rentalAgreement = toolRentalService.checkout("CHNS", LocalDate.of(2015, 7, 2), 56, 25);
        Assertions.assertEquals("CHNS", rentalAgreement.getToolCode());
    }

    @Test
    public void testRentalAgreementCalculationForZeroDiscount() {
        RentalAgreement rentalAgreement = toolRentalService.checkout("JAKR", LocalDate.of(2015, 7, 2), 9, 0);
        Assertions.assertEquals("JAKR", rentalAgreement.getToolCode());
    }

    @Test
    public void testRentalAgreementCalculationForValidValues() {
        RentalAgreement rentalAgreement = toolRentalService.checkout("JAKR", LocalDate.of(2020, 7, 2), 4, 50);
        Assertions.assertEquals("JAKR", rentalAgreement.getToolCode());
    }
}
