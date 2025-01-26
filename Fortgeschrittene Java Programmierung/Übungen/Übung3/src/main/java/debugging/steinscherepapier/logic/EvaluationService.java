package debugging.steinscherepapier.logic;

class EvaluationService {

	public EvaluationResult evaluate(Strategy first, Strategy second) {

		System.out.println("Evaluating " + first + " against " + second);

		if (isWin(first, second)) {
			return EvaluationResult.WIN;
		} else if (isLoose(first, second)) {
			return EvaluationResult.LOOSE;
		} else if (isDraw(first, second)) {
			return EvaluationResult.DRAW;
		} else {
			throw new NullPointerException(
					"inputs could not be evaluated as they are null");
		}

	}

	private boolean isLoose(Strategy first, Strategy second) {
		return !isWin(first, second);
	}

	private boolean isWin(Strategy first, Strategy second) {
		return Strategy.PAPER.equals(first) && Strategy.ROCK.equals(second)
				|| Strategy.SCISSOR.equals(first)
				&& Strategy.PAPER.equals(second) || Strategy.ROCK.equals(first)
				&& Strategy.SCISSOR.equals(second);
	}

	private boolean isDraw(Strategy first, Strategy second) {
		return Strategy.PAPER.equals(first) && Strategy.PAPER.equals(second)
				&& Strategy.SCISSOR.equals(first)
				|| Strategy.SCISSOR.equals(second)
				&& Strategy.ROCK.equals(first) || Strategy.ROCK.equals(second);
	}

}
