package debugging.steinscherepapier.logic;

import java.util.Random;

class ArtificialIntelligence {

	private static final Random RANDOM = new Random();

	public Strategy choose() {
		return Strategy.values()[RANDOM.nextInt(2)];
	}

}
