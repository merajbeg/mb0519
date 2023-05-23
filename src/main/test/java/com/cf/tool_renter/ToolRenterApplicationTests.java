package com.cf.tool_renter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ToolRenterApplicationTests {

	@Autowired
    private ToolRentalApplication toolRentalApplication;
	
	@Test
	void contextLoads() {
	}

	@Test
    public void testRentalAgreementCalculationForInvalidDiscount() {
        ToolRentalApplication toolRentalApplication = new ToolRentalApplication();
		System.out.println("1st Test");
        Assertions.assertThrows(ToolRentalException.class, () -> {
            toolRentalApplication.checkout("JAKR", LocalDate.of(2015, 9, 3), 5, 101);
        });
		System.out.println("1st Test ended");

    }

    @Test
    public void testRentalAgreementCalculationForValidDiscount() {
        ToolRentalApplication toolRentalApplication = new ToolRentalApplication();
        RentalAgreement rentalAgreement = toolRentalApplication.checkout("LADW", LocalDate.of(2020, 7, 2), 3, 10);
        Assertions.assertEquals("LADW", rentalAgreement.getToolCode());
        // Add more assertions to verify the values in the rental agreement
    }

    @Test
    public void testRentalAgreementCalculationForZeroRentalDays() {
        ToolRentalApplication toolRentalApplication = new ToolRentalApplication();
        RentalAgreement rentalAgreement = toolRentalApplication.checkout("CHNS", LocalDate.of(2015, 7, 2), 56, 25);
        Assertions.assertEquals("CHNS", rentalAgreement.getToolCode());
        // Add more assertions to verify the values in the rental agreement
    }

    @Test
    public void testRentalAgreementCalculationForZeroDiscount() {
        ToolRentalApplication toolRentalApplication = new ToolRentalApplication();
        RentalAgreement rentalAgreement = toolRentalApplication.checkout("JAKR", LocalDate.of(2015, 7, 2), 9, 0);
        Assertions.assertEquals("JAKR", rentalAgreement.getToolCode());
        // Add more assertions to verify the values in the rental agreement
    }

    @Test
    public void testRentalAgreementCalculationForValidValues() {
        ToolRentalApplication toolRentalApplication = new ToolRentalApplication();
        RentalAgreement rentalAgreement = toolRentalApplication.checkout("JAKR", LocalDate.of(2020, 7, 2), 4, 50);
        Assertions.assertEquals("JAKR", rentalAgreement.getToolCode());
        // Add more assertions to verify the values in the rental agreement
    }
}
