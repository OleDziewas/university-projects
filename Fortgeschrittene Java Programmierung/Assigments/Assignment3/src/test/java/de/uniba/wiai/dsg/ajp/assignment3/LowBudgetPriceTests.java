package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LowBudgetPriceTests {
    private LowBudgetPrice price;

    @BeforeEach
    public void setUp() throws Exception {
        price = new LowBudgetPrice();
    }

    @AfterEach
    public void tearDown() throws Exception {
        price = null;
    }

    @ParameterizedTest
    @MethodSource("differentDaysRented")
    public void getChargeReturnsCorrectValueForLegalArguments(int daysRented, double result){
        double charge = price.getCharge(daysRented);
        assertEquals(result, charge);
    }
    public static List<Arguments> differentDaysRented(){
        return List.of(Arguments.of(1,0.5),Arguments.of(2,1.5), Arguments.of(3, 2.0));
    }

    @Test
    public void getChargeTestThrowsExceptionForIllegalArguments(){
        int daysRented = 0;
        assertThrows(IllegalArgumentException.class, ()-> price.getCharge(daysRented));
    }

    @Test
    public void getPriceCodeShouldReturnChildrens(){
        assertEquals(price.getPriceCode(), Movie.PriceCodes.LOW_BUDGET);
    }
}
