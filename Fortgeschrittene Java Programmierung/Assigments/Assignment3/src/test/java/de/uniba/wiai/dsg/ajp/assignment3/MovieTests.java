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
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MovieTests {

    private Movie movie;

    @BeforeEach
    public void setUp() throws Exception {
        movie = new Movie("dummyString", Movie.PriceCodes.CHILDRENS, Movie.Resolution.HD);
    }

    @AfterEach
    public void tearDown() throws Exception {
        movie = null;
    }

    @ParameterizedTest
    @MethodSource("differentParametersForConstructors")
    public void constructorHasProvidedValueAsResult(String title, Movie.PriceCodes priceCode, Movie.Resolution resolution) {
        movie = new Movie(title, priceCode, resolution);
        assertEquals(title, movie.getTitle());
        assertEquals(priceCode, movie.getPriceCode());
        assertEquals(resolution, movie.getResolution());
    }
    public static List<Arguments> differentParametersForConstructors() {
        return List.of(Arguments.of("GoodFellas",Movie.PriceCodes.REGULAR,Movie.Resolution.ULTRA_HD),
                Arguments.of("Cars",Movie.PriceCodes.CHILDRENS,Movie.Resolution.HD),
                Arguments.of("Sharknado",Movie.PriceCodes.LOW_BUDGET,Movie.Resolution.HD),
                Arguments.of("Once upon a time in Hollywood",Movie.PriceCodes.NEW_RELEASE,Movie.Resolution.ULTRA_HD));
    }

    @ParameterizedTest
    @MethodSource("differentParametersForConstructorExceptions")
    public void ThrowsIllegalArgumentExceptionInConstructorGivenAnInvalidParameter(String title, Movie.PriceCodes priceCode, Movie.Resolution resolution) {
        assertThrows(IllegalArgumentException.class, () -> movie = new Movie(title, priceCode, resolution));
    }
    public static List<Arguments> differentParametersForConstructorExceptions() {
        return List.of(Arguments.of(null,Movie.PriceCodes.REGULAR,Movie.Resolution.ULTRA_HD),
                Arguments.of("Cars",null,Movie.Resolution.HD),
                Arguments.of("Sharknado",Movie.PriceCodes.LOW_BUDGET,null));
    }

    @ParameterizedTest
    @MethodSource("differentResolutions")
    public void ResolutionShouldBe(Movie.Resolution resolution) {
        movie.setResolution(resolution);
        assertEquals(resolution, movie.getResolution());
    }

    @ParameterizedTest
    @MethodSource("differentResolutions")
    public void setsTheFieldResolutionToParameterPassed(Movie.Resolution resolution) {
        movie.setResolution(resolution);
        assertEquals(resolution, movie.getResolution());

    }
    public static List<Arguments> differentResolutions() {
        return List.of(Arguments.of(Movie.Resolution.ULTRA_HD),Arguments.of(Movie.Resolution.HD));
    }

    @ParameterizedTest
    @MethodSource("differentTitles")
    public void TitleShouldBe(String title) {
        movie.setTitle(title);
        assertEquals(title, movie.getTitle());
    }

    @ParameterizedTest
    @MethodSource("differentTitles")
    public void setsTheFieldTitleToParameterPassed(String title) {
        movie.setTitle(title);
        assertEquals(title, movie.getTitle());
    }

    public static List<Arguments> differentTitles() {
        return List.of(Arguments.of("dummyString"),Arguments.of("GoodFellas"),
                Arguments.of("Cars"), Arguments.of("Sharknado"),
                Arguments.of("Once upon a time in Hollywood"));
    }

    @Test
    public void getChargeReturnTypeIsCorrect() {
        Price price = mock(Price.class);
        given(price.getCharge(anyInt())).willReturn(anyDouble());
    }


    @ParameterizedTest
    @MethodSource("differentParametersNeededForPriceCodeTest")
    public void chargeAmountIsCorrect(String title, Movie.PriceCodes priceCode, Movie.Resolution resolution, int daysRented, double result) {
        movie = new Movie(title, priceCode, resolution);
        assertEquals(result, movie.getCharge(daysRented));
    }

    public static List<Arguments> differentParametersNeededForPriceCodeTest() {
        return List.of(Arguments.of("dummystring",Movie.PriceCodes.CHILDRENS, Movie.Resolution.HD, 2, 1.5),
                Arguments.of("dummystring",Movie.PriceCodes.NEW_RELEASE, Movie.Resolution.HD, 2, 6),
                Arguments.of("dummystring",Movie.PriceCodes.LOW_BUDGET, Movie.Resolution.HD, 2, 1.5),
                Arguments.of("dummystring",Movie.PriceCodes.REGULAR, Movie.Resolution.HD, 2, 2),
                Arguments.of("dummystring",Movie.PriceCodes.CHILDRENS, Movie.Resolution.ULTRA_HD, 7, 7.5+2),
                Arguments.of("dummystring",Movie.PriceCodes.NEW_RELEASE, Movie.Resolution.ULTRA_HD, 7, 21+2),
                Arguments.of("dummystring",Movie.PriceCodes.LOW_BUDGET, Movie.Resolution.ULTRA_HD, 7, 4+2),
                Arguments.of("dummystring",Movie.PriceCodes.REGULAR, Movie.Resolution.ULTRA_HD, 7, 9.5+2)
        );
    }

    @Test
    public void ThrowsIllegalArgumentExceptionInGetChargeGivenAnInvalidParameter() {
        assertThrows(IllegalArgumentException.class, () -> movie.getCharge(-4));
    }

    @ParameterizedTest
    @MethodSource("differentPriceCodes")
    public void PriceCodeShouldBe(Movie.PriceCodes priceCodes) {
        movie.setPriceCode(priceCodes);
        assertEquals(priceCodes, movie.getPriceCode());
    }

    @Test
    public void ThrowsIllegalArgumentInSetPriceCodeGivenAnInvalidParameter() {
        assertThrows(IllegalArgumentException.class,() -> movie.setPriceCode(null));
    }

    public static List<Arguments> differentPriceCodes() {
        return List.of(Arguments.of(Movie.PriceCodes.REGULAR),
                Arguments.of(Movie.PriceCodes.CHILDRENS),
                Arguments.of(Movie.PriceCodes.LOW_BUDGET),
                Arguments.of(Movie.PriceCodes.NEW_RELEASE));
    }

    @ParameterizedTest
    @MethodSource("differentPriceCodes")
    public void checksIfPriceCodeCorrectlySet(Movie.PriceCodes priceCodes) {
        movie.setPriceCode(priceCodes);
        assertEquals(priceCodes, movie.getPriceCode());
    }


    @ParameterizedTest
    @MethodSource("differentRenterPointsParameters")
    public void checksIfRenterPointsCorrect(Movie.PriceCodes priceCodes, int daysRented, int expected) {
    movie.setPriceCode(priceCodes);
    assertEquals(expected,movie.getFrequentRenterPoints(daysRented));
    }

    public static List<Arguments> differentRenterPointsParameters() {
        return List.of(Arguments.of(Movie.PriceCodes.REGULAR, 2, 1),
                Arguments.of(Movie.PriceCodes.CHILDRENS, 2, 0),
                Arguments.of(Movie.PriceCodes.LOW_BUDGET, 2, 1),
                Arguments.of(Movie.PriceCodes.NEW_RELEASE, 2,2),
                Arguments.of(Movie.PriceCodes.REGULAR, 7, 1),
        Arguments.of(Movie.PriceCodes.CHILDRENS, 7, 0),
                Arguments.of(Movie.PriceCodes.LOW_BUDGET, 7, 1),
                Arguments.of(Movie.PriceCodes.NEW_RELEASE, 7,2));
    }

    @Test
    public void ThrowsIllegalArgumentInGetFrequentRenterPointsGivenAnInvalidParameter() {
        assertThrows(IllegalArgumentException.class,() -> movie.getFrequentRenterPoints(-4));
    }

}
