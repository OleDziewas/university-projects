package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Executable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChildrensPriceTests {
    private ChildrensPrice price;

    @BeforeEach
    public void setUp() throws Exception {
        price = new ChildrensPrice();
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
        return List.of(Arguments.of(1,1.5),Arguments.of(3,1.5), Arguments.of(4, 3.0));
    }

    @Test
    public void getChargeTestThrowsExceptionForIllegalArguments(){
        int daysRented = 0;
        assertThrows(IllegalArgumentException.class, ()-> price.getCharge(daysRented));
    }

    @Test
    public void getPriceCodeShouldReturnChildrens(){
        assertEquals(price.getPriceCode(), Movie.PriceCodes.CHILDRENS);
    }

    @ParameterizedTest
    @ValueSource(ints = {-2,0,1})
    public void getFrequentRenterPointsShouldReturnZero(int daysRented){
        int points = price.getFrequentRenterPoints(daysRented);
        assertEquals(0, points);
    }
}
