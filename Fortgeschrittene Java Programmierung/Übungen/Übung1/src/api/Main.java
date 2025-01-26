package api;

import api.impl.VerySimpleCountImpl;

public class Main {

	public static void main(String[] args) {
		checkOccurrences("", "burg", CountResult.ZERO);
		checkOccurrences("salat", "burg", CountResult.ZERO);
		checkOccurrences("hamburger", "burg", CountResult.ONE);
		checkOccurrences("hamburger hamburger", "burg", CountResult.MORE);
		checkOccurrences("hamburger hamburger hamburger", "burg",
				CountResult.MORE);
	}

	private static void checkOccurrences(String haystack, String needle,
			CountResult result) {
		// prepare
		VerySimpleCount count = new VerySimpleCountImpl();

		// test
		CountResult countResult = count.countOccurrences(haystack, needle);

		// verify result
		if (!result.equals(countResult)) {
			System.err.println("ERROR");
			System.err
					.format("counting occurrences of [%s] in [%s] should be %s, but is %s%n",
							needle, haystack, result, countResult);

			// exit on error
			System.exit(1);
		} else {
			System.out.format("counting occurrences of [%s] in [%s] is %s%n",
					needle, haystack, countResult);
		}

	}
}
