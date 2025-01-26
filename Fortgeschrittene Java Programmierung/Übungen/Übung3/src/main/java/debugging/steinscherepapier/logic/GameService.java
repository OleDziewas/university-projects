package debugging.steinscherepapier.logic;

public class GameService {

	private EvaluationService evaluationService = new EvaluationService();
	private ArtificialIntelligence artificialIntelligence = new ArtificialIntelligence();

	public EvaluationResult play(Strategy strategy) {
		return evaluationService.evaluate(strategy,
				artificialIntelligence.choose());
	}

}
