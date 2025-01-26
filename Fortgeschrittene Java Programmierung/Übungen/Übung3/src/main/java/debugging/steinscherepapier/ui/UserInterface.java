package debugging.steinscherepapier.ui;

import debugging.steinscherepapier.logic.EvaluationResult;
import debugging.steinscherepapier.logic.GameService;
import debugging.steinscherepapier.logic.Strategy;


public class UserInterface {

	private GameService gameService = new GameService();

	public void start() {
		// TODO create REP cycle
		// TODO add option to exit or start a new game
		newGame();			
	}
	
	private void newGame() {
		printSelection();
		Strategy chosenStrategy = retrieveChosenStrategy();
		evaluateAndPrintResult(chosenStrategy);
	}
	
	private void printSelection() {
		System.out.println("Choose your weapon");
		System.out.println(" (1) Paper");
		System.out.println(" (2) Scissor");
		System.out.println(" (3) Rock");
	}

	private Strategy retrieveChosenStrategy() {
		// TODO implement interaction with user
		return null;
	}
	
	private void evaluateAndPrintResult(Strategy chosenStrategy) {
		EvaluationResult result = gameService.play(chosenStrategy);
		System.out.println(result);
	}


}
