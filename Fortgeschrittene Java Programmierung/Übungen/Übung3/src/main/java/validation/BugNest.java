package validation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class BugNest {

	public String myString = "BugNest (tm)";

	public String sayHello() {
		String hello = "Hello";
		return hello;
	}

	public boolean areYouHello(String string) {
		boolean isHello = string.equals("Hello");
		return isHello;
	}

	public void writeHello() throws IOException {
		BufferedWriter writer = Files.newBufferedWriter(Paths.get("hello.txt"));
		writer.write("hello");
		writer.close();
	}

	public void writeHelloWithoutTryFinally() throws IOException {
		try(BufferedWriter writer = Files.newBufferedWriter(Paths.get("hello.txt"))) {
			writer.write("hello");
			Random rand = new Random();
			if (rand.nextBoolean()) {
				throw new IllegalStateException("shut down system!");
			}
		}catch(Exception e){
			System.out.println("Print Fehler");
		}
	}

	public void printHelloMyConstant(String suffix) {
		String hi =suffix.trim();
		System.out.format("Hello %s (%s)%n", myString, suffix);
	}

	public static void main(String[] args) {
		int x = 2;
		int y = 5;
		double value1 = x / (double)y;
		System.out.println("2 / 5 = " + value1);
	}

}
