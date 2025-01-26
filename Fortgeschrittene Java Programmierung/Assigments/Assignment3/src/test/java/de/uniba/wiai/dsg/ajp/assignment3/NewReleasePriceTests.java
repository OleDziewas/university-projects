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

public class NewReleasePriceTests {
    private NewReleasePrice price;

    @BeforeEach
    public void setUp() throws Exception {
        price = new NewReleasePrice();
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
        return List.of(Arguments.of(1,3.0),Arguments.of(3,9.0), Arguments.of(4, 12.0));
    }

    @Test
    public void getChargeTestThrowsExceptionForIllegalArguments(){
        int daysRented = 0;
        assertThrows(IllegalArgumentException.class, ()-> price.getCharge(daysRented));
    }

    @Test
    public void getPriceCodeShouldReturnChildrens(){
        assertEquals(price.getPriceCode(), Movie.PriceCodes.NEW_RELEASE);
    }

    @ParameterizedTest
    @MethodSource("differentDaysRentedForRenterPoints")
    public void getFrequentRenterPointsReturnsCorrectValues(int daysRented, int result){
        double points = price.getFrequentRenterPoints(daysRented);
        assertEquals(result, points);
    }
    public static List<Arguments> differentDaysRentedForRenterPoints(){
        return List.of(Arguments.of(1,1),Arguments.of(2,2), Arguments.of(3, 2), Arguments.of(0,0));
    }
}
