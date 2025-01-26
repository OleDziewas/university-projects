package de.uniba.wiai.dsg.ajp.assignment3;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static de.uniba.wiai.dsg.ajp.assignment3.Movie.PriceCodes.*;
import static de.uniba.wiai.dsg.ajp.assignment3.Movie.Resolution.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerIntegrationTests {

    private Customer customer;
    private List<Rental> rentals;

    @BeforeEach
    public void setUp(){
        customer = new Customer("Kunde");
        rentals = new LinkedList<>();
        Rental rental1 = new Rental();
        Rental rental2 = new Rental();
        Rental rental3 = new Rental();
        Rental rental4 = new Rental();
        rental1.setMovie(new Movie("Deadlier", REGULAR, HD));
        rental2.setMovie(new Movie("Wishtest", NEW_RELEASE, ULTRA_HD));
        rental3.setMovie(new Movie("Cyberpunk the movie", LOW_BUDGET, ULTRA_HD));
        rental4.setMovie(new Movie("Der Spezialist", CHILDRENS, HD));
        rental1.setDiscount(0.2);
        rental3.setDiscount(0.75);
        rental4.setDiscount(0.4);
        rental1.setDaysRented(15);
        rental2.setDaysRented(8);
        rental3.setDaysRented(3);
        rental4.setDaysRented(33);
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        rentals.add(rental4);
    }

    @AfterEach
    public void tearDown(){
        customer = null;
        rentals = null;
    }

    @Test
    public void statementShouldBeCalculatedAndPrintedCorrectly(){
        customer.setRentals(rentals);
        String output = customer.statement();

        assertEquals("Rental Record for Kunde\n\t" +
                        "Deadlier HD\t17.2 Discount: 20.0%\n\t" +
                        "Wishtest ULTRA_HD\t26.0 Discount: 0.0%\n\t" +
                        "Cyberpunk the movie ULTRA_HD\t1.0 Discount: 75.0%\n\t" +
                        "Der Spezialist HD\t27.9 Discount: 40.0%\n" +
                        "Amount owed is 72.1\n" +
                        "You earned 4 frequent renter points",
                output, "Format of the output-string should be identical to expected string");
    }

    @Test
    public void htmlStatementShouldBeCalculatedAndPrintedCorrectly(){
        customer.setRentals(rentals);
        String output = customer.htmlStatement();

        assertEquals("<H1>Rentals for <EM>Kunde</EM></H1><P>\n" +
                        "Deadlier HD: 17.2 Discount: 20.0%<BR>\n" +
                        "Wishtest ULTRA_HD: 26.0 Discount: 0.0%<BR>\n" +
                        "Cyberpunk the movie ULTRA_HD: 1.0 Discount: 75.0%<BR>\n" +
                        "Der Spezialist HD: 27.9 Discount: 40.0%<BR>\n" +
                        "<P>You owe <EM>72.1</EM><P>\n" +
                        "On this rental you earned <EM>4</EM> frequent renter points<P>",
                output, "Format of the output-string should be identical to expected string");
    }
}
