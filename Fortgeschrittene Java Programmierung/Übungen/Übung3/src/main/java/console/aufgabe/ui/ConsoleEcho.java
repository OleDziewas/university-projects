package console.aufgabe.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;

import console.aufgabe.EchoUI;

public class ConsoleEcho implements EchoUI{

	private BufferedReader reader;

	private boolean exit;

	public ConsoleEcho() {
		reader = new BufferedReader(new InputStreamReader(System.in));
		exit = false;
	}

	public void startReadEvalPrint() {
		while (!exit) {
			printMainMenu();
			int option = readOption();
			evalOption(option);
		}
	}

	private void printMainMenu() {
		//TODO: Provide meaningful information to the the user
		System.out.println("Menü:");
		System.out.println("(1) Beenden des Programmes");
		System.out.println("(Zahl != 1) Echo der eingegebenen Zahl");
	}

	private int readOption() {
		//TODO: Read an int, beware of String/char input
		boolean done = false;
		int input =0;
		while(!done) {
			try {
				input = Integer.parseInt(reader.readLine());
				done = true;
			} catch (NumberFormatException | IOException e) {
				System.out.println("Fehlerhafte Eingabe");
			}
		}
		return input;
	}

	private void evalOption(int option) {
		//TODO: choose a proper operation
		if(option == 1){
			exit();
		}else{
			echo(option);
		}
	}

	private void exit() {
		//TODO: end the program and inform the user
		exit = true;
		System.out.println("Programm done");
	}

	private void echo(int input) {
		//TODO: produce and print the echo String
		System.out.println("Echo: " + input);
	}

}
