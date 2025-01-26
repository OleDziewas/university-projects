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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class RentalTest {

    private Rental rental;
    private Movie movie;


    @BeforeEach
    public void setUp() {
        rental = new Rental();
        rental.setDiscount(0.3);
        rental.setDaysRented(5);
        movie = new Movie("Witcher", Movie.PriceCodes.REGULAR, Movie.Resolution.HD);
        rental.setMovie(movie);
    }

    @AfterEach
    public void tearDown() {
        rental = null;
        movie = null;
    }

    @Test
    public void getDiscountShouldBe(){
        assertEquals(0.3, rental.getDiscount());
    }
    @Test
    public void getDaysRentedShouldBe(){
        assertEquals(5, rental.getDaysRented());
    }

    @Test
    public void getMovieShouldBe(){
        assertEquals(movie, rental.getMovie());
    }

    @Test
    public void setDiscountShouldBeZeroPointTwo(){
        rental = new Rental();
        rental.setDiscount(0.2);
        assertEquals(0.2, rental.getDiscount(), "Discount should be 2");
    }

    @Test
    public void setDaysRentedShouldBeFifteen(){
        rental = new Rental();
        rental.setDaysRented(15);
        assertEquals(15,rental.getDaysRented(), "Days rented should be 15");
    }

    @Test
    public void setMovieShouldBeMovie(){
        rental.setMovie(movie);
        assertEquals(movie, rental.getMovie());
    }


    @ParameterizedTest
    @ValueSource(doubles = {-1.0, 2.0})
    public void errorValidateDiscountForFalseDiscount(double discount){
        assertThrows(IllegalArgumentException.class, () -> rental.setDiscount(discount));
    }

    @Test
    public void getChargeShouldCalculateCorrectly(){
        assertEquals(4.55, rental.getCharge());
    }

    @Test
    public void getFrequentRenterPointsShouldBeCalculatedCorrectly(){
        assertEquals(1.0, rental.getFrequentRenterPoints());
    }

    @Test
    public void validateMovieShouldThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> rental.setMovie(null));
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, 0})
    public void setDaysRentedShouldThrowIllegalArgumentExceptionIfNegativeOrZero(int value){
        assertThrows(IllegalArgumentException.class, () -> rental.setDaysRented(value));
    }


    }




