package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InOrder;

import java.util.LinkedList;
import java.util.List;

import static de.uniba.wiai.dsg.ajp.assignment3.Movie.PriceCodes.*;
import static de.uniba.wiai.dsg.ajp.assignment3.Movie.Resolution.HD;
import static de.uniba.wiai.dsg.ajp.assignment3.Movie.Resolution.ULTRA_HD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


public class CustomerTests {

    private Customer customer;
    private List<Rental> rentalList;


    @BeforeEach
    public void setUp(){
        customer = new Customer("Kunde");
        rentalList = new LinkedList<>();
    }

    @AfterEach
    public void tearDown(){
        customer = null;
        rentalList = null;
    }

    @Test
    public void customerNameShouldBeKunde(){
        assertEquals("Kunde", customer.getName(), "Name of the customer should be 'Kunde'");
    }

    @Test
    public void initializeValueShouldBeAdrian(){
        customer = new Customer("Adrian");
        assertEquals("Adrian", customer.getName(), "Name of the customer should be 'Film'");
    }

    @Test
    public void settedNameShouldBePeter(){
        customer.setName("Peter");
        assertEquals("Peter", customer.getName(), "Name of the customer should be 'Neu'");
    }

    @Test
    public void customerWithoutNameShouldThrowIllegalArgumentException(){
        Executable executable = () -> new Customer("");
        assertThrows(IllegalArgumentException.class, executable, "Empty name of customer should throw IllegalArgumentException");
    }

    @Test
    public void customerWithNullNameShouldThrowIllegalArgumentException(){
        Executable executable = () -> new Customer(null);
        assertThrows(IllegalArgumentException.class, executable, "Empty name of customer should throw IllegalArgumentException");
    }

    @Test
    public void listsOfRentalsShouldBeIdentical(){
        Rental rental1 = new Rental();
        Rental rental2 = new Rental();
        rentalList.add(rental1);
        rentalList.add(rental2);

        customer.setRentals(rentalList);

        assertEquals(rentalList, customer.getRentals(), "The Lists of rentals should equal");
    }

    @Test
    public void rentalWithoutMovieShouldThrowNullPointerExceptionInStatement(){
        Rental mockedRental = mock(Rental.class);
        rentalList.add(mockedRental);
        customer.setRentals(rentalList);

        Executable executable = () -> customer.statement();

        assertThrows(NullPointerException.class, executable, "NullPointerException should be thrown, because of missing movie in rental");
    }

    @Test
    public void rentalWithoutMovieShouldThrowNullPointerExceptionInHtmlStatement(){
        Rental mockedRental = mock(Rental.class);
        rentalList.add(mockedRental);
        customer.setRentals(rentalList);

        Executable executable = () -> customer.htmlStatement();

        assertThrows(NullPointerException.class, executable, "NullPointerException should be thrown, because of missing movie in rental");
    }

    @Test
    public void rentalWithoutDaysRentedSetShouldThrowIllegalArgumentException(){
        Rental rental = new Rental();
        rental.setMovie(new Movie("TITO", REGULAR, HD));
        rentalList.add(rental);
        customer.setRentals(rentalList);

        Executable executable = () -> customer.statement();
        assertThrows(IllegalArgumentException.class, executable, "IllegalArgumentException should be thrown, because of missing amount of days rented");
    }

    @Test
    public void rentalWithoutDaysRentedSetShouldThrowIllegalArgumentExceptionInHtmlStatement(){
        Rental rental = new Rental();
        rental.setMovie(new Movie("TITO", REGULAR, HD));
        rentalList.add(rental);
        customer.setRentals(rentalList);

        Executable executable = () -> customer.htmlStatement();
        assertThrows(IllegalArgumentException.class, executable, "IllegalArgumentException should be thrown, because of missing amount of days rented");
    }

    @Test
    public void statementShouldCallMethodsInCorrectOrder(){
        Movie mockedMovie = mock(Movie.class);
        Rental mockedRental = mock(Rental.class);
        rentalList.add(mockedRental);
        customer.setRentals(rentalList);
        given(mockedMovie.getResolution()).willReturn(HD);
        given(mockedRental.getMovie()).willReturn(mockedMovie);
        InOrder inOrder = inOrder(mockedRental, mockedMovie);

        String output = customer.statement();

        then(mockedRental).should(inOrder).getFrequentRenterPoints();
        then(mockedRental).should(inOrder).getMovie();
        then(mockedMovie).should(inOrder).getTitle();
        then(mockedRental).should(inOrder).getMovie();
        then(mockedMovie).should(inOrder).getResolution();
        then(mockedRental).should(inOrder).getCharge();
        then(mockedRental).should(inOrder).getDiscount();
        then(mockedRental).should(inOrder).getCharge();
        then(mockedRental).shouldHaveNoMoreInteractions();
        then(mockedMovie).shouldHaveNoMoreInteractions();
    }

    @Test
    public void htmlStatementShouldCallMethodsInCorrectOrder(){
        Movie mockedMovie = mock(Movie.class);
        Rental mockedRental = mock(Rental.class);
        rentalList.add(mockedRental);
        customer.setRentals(rentalList);
        given(mockedMovie.getResolution()).willReturn(HD);
        given(mockedRental.getMovie()).willReturn(mockedMovie);
        InOrder inOrder = inOrder(mockedRental, mockedMovie);

        String output = customer.htmlStatement();

        then(mockedRental).should(inOrder).getMovie();
        then(mockedMovie).should(inOrder).getTitle();
        then(mockedRental).should(inOrder).getMovie();
        then(mockedMovie).should(inOrder).getResolution();
        then(mockedRental).should(inOrder).getCharge();
        then(mockedRental).should(inOrder).getDiscount();
        then(mockedRental).should(inOrder).getCharge();
        then(mockedRental).should(inOrder).getFrequentRenterPoints();
        then(mockedRental).shouldHaveNoMoreInteractions();
        then(mockedMovie).shouldHaveNoMoreInteractions();
    }

    @Test
    public void statementShouldCallAllGivenMethods(){
        Rental mockedRental = mock(Rental.class);
        Movie mockedMovie = mock(Movie.class);
        given(mockedRental.getMovie()).willReturn(mockedMovie);
        given(mockedMovie.getResolution()).willReturn(ULTRA_HD);
        rentalList.add(mockedRental);
        customer.setRentals(rentalList);

        String output = customer.statement();

        verify(mockedMovie, times(1)).getTitle();
        verify(mockedMovie, times(1)).getResolution();
        verify(mockedRental, times(1)).getFrequentRenterPoints();
        verify(mockedRental, times(2)).getMovie();
        verify(mockedRental, times(2)).getCharge();
        verify(mockedRental, times(1)).getDiscount();

        then(mockedMovie).shouldHaveNoMoreInteractions();
        then(mockedRental).shouldHaveNoMoreInteractions();
    }

    @Test
    public void htmlStatementShouldCallAllGivenMethods(){
        Rental mockedRental = mock(Rental.class);
        Movie mockedMovie = mock(Movie.class);

        given(mockedRental.getMovie()).willReturn(mockedMovie);
        given(mockedMovie.getResolution()).willReturn(ULTRA_HD);
        rentalList.add(mockedRental);
        customer.setRentals(rentalList);
        String output = customer.htmlStatement();

        verify(mockedMovie, times(1)).getTitle();
        verify(mockedMovie, times(1)).getResolution();
        verify(mockedRental, times(1)).getFrequentRenterPoints();
        verify(mockedRental, times(2)).getMovie();
        verify(mockedRental, times(2)).getCharge();
        verify(mockedRental, times(1)).getDiscount();

        then(mockedMovie).shouldHaveNoMoreInteractions();
        then(mockedRental).shouldHaveNoMoreInteractions();
    }

    @Test
    public void statementFormatShouldBeDisplayedCorrectly(){
        Rental mockedRental = mock(Rental.class);
        Movie mockedMovie = mock(Movie.class);

        given(mockedRental.getMovie()).willReturn(mockedMovie);
        given(mockedMovie.getResolution()).willReturn(ULTRA_HD);
        given(mockedMovie.getTitle()).willReturn("Die Soft 2");
        given(mockedRental.getCharge()).willReturn(3.5);
        given(mockedRental.getFrequentRenterPoints()).willReturn(5);
        given(mockedRental.getDiscount()).willReturn(0.05);

        rentalList.add(mockedRental);
        customer.setRentals(rentalList);

        String output = customer.statement();

        assertEquals("Rental Record for Kunde\n\tDie Soft 2 ULTRA_HD\t3.5 Discount: 5.0%\nAmount owed is 3.5\nYou earned 5 frequent renter points",
                output, "Format of the output-string should be identical to expected string");
    }

    @Test
    public void htmlStatementFormatShouldBeDisplayedCorrectly(){
        Rental mockedRental = mock(Rental.class);
        Movie mockedMovie = mock(Movie.class);

        given(mockedRental.getMovie()).willReturn(mockedMovie);
        given(mockedMovie.getResolution()).willReturn(ULTRA_HD);
        given(mockedMovie.getTitle()).willReturn("Die Soft 3");
        given(mockedRental.getCharge()).willReturn(3.5);
        given(mockedRental.getFrequentRenterPoints()).willReturn(5);
        given(mockedRental.getDiscount()).willReturn(0.05);

        rentalList.add(mockedRental);
        customer.setRentals(rentalList);

        String output = customer.htmlStatement();

        assertEquals("<H1>Rentals for <EM>Kunde</EM></H1><P>\nDie Soft 3 ULTRA_HD: 3.5 Discount: 5.0%<BR>\n<P>You owe <EM>3.5</EM><P>\nOn this rental you earned <EM>5</EM> frequent renter points<P>",
                output, "Format of the output-string should be identical to expected string");
    }




}
