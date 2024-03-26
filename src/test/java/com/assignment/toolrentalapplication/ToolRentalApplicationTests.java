package com.assignment.toolrentalapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToolRentalApplicationTests {


    @Test
    public void testGenerateRentalAgreement_ToolJAKR() throws ParseException, Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Tool tool = new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false);
        Date checkoutDate = dateFormat.parse("09/03/2015");
        Checkout checkout = new Checkout(tool, 5, 0, checkoutDate);
        RentalAgreement agreement = checkout.generateRentalAgreement();
        assertEquals("JAKR", agreement.getToolCode());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals("Ridgid", agreement.getToolBrand());
        assertEquals(5, agreement.getRentalDays());
        assertEquals(checkoutDate, agreement.getCheckoutDate());
    }

    @Test
    public void testGenerateRentalAgreement_ToolLADW() throws ParseException, Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Tool tool = new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false);
        Date checkoutDate = dateFormat.parse("07/02/2020");
        Checkout checkout = new Checkout(tool, 3, 10, checkoutDate);
        RentalAgreement agreement = checkout.generateRentalAgreement();
        assertEquals("LADW", agreement.getToolCode());
        assertEquals("Ladder", agreement.getToolType());
        assertEquals("Werner", agreement.getToolBrand());
        assertEquals(3, agreement.getRentalDays());
        assertEquals(checkoutDate, agreement.getCheckoutDate());
    }

    @Test
    public void testGenerateRentalAgreement_ToolCHNS() throws ParseException, Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Tool tool = new Tool("CHNS", "Chainsaw", "Stihl", 1.49, true, false, true);
        Date checkoutDate = dateFormat.parse("07/02/2015");
        Checkout checkout = new Checkout(tool, 5, 25, checkoutDate);
        RentalAgreement agreement = checkout.generateRentalAgreement();
        assertEquals("CHNS", agreement.getToolCode());
        assertEquals("Chainsaw", agreement.getToolType());
        assertEquals("Stihl", agreement.getToolBrand());
        assertEquals(5, agreement.getRentalDays());
        assertEquals(checkoutDate, agreement.getCheckoutDate());
    }

    @Test
    public void testGenerateRentalAgreement_ToolJAKD() throws ParseException, Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Tool tool = new Tool("JAKD", "Jackhammer", "DeWalt", 2.99, true, false, false);
        Date checkoutDate = dateFormat.parse("09/03/2015");
        Checkout checkout = new Checkout(tool, 6, 0, checkoutDate);
        RentalAgreement agreement = checkout.generateRentalAgreement();
        assertEquals("JAKD", agreement.getToolCode());
        assertEquals("Jackhammer", agreement.getToolType());
        assertEquals("DeWalt", agreement.getToolBrand());
        assertEquals(6, agreement.getRentalDays());
        assertEquals(checkoutDate, agreement.getCheckoutDate());
    }

    @Test
    public void testGenerateRentalAgreement_DiscountPercentOutOfRange() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Tool tool = new Tool("LADW", "Ladder", "Werner", 1.99, true, true, false);
        Date checkoutDate = dateFormat.parse("07/02/2020");
        Checkout checkout = new Checkout(tool, 4, 101, checkoutDate);
        try {
            RentalAgreement agreement = checkout.generateRentalAgreement();
            fail("Expected an exception for discount percent out of range.");
        } catch (Exception e) {
            assertEquals("Discount percent must be in the range 0-100", e.getMessage());
        }
    }

    @Test
    public void testGenerateRentalAgreement_InvalidRentalDayCount() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Tool tool = new Tool("JAKR", "Jackhammer", "Ridgid", 2.99, true, false, false);
        Date checkoutDate = dateFormat.parse("07/02/2020");
        Checkout checkout = new Checkout(tool, 0, 10, checkoutDate);
        try {
            RentalAgreement agreement = checkout.generateRentalAgreement();
            fail("Expected an exception for invalid rental day count.");
        } catch (Exception e) {
            assertEquals("Rental day count must be 1 or greater", e.getMessage());
        }
    }


}
