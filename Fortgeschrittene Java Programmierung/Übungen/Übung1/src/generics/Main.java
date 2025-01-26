package generics;

import generics.impl.SimpleIndexPrepender;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		IndexPrepender prepender = new SimpleIndexPrepender();

		List<String> helloList = new ArrayList<>(2);
		helloList.add("Hallo");
		helloList.add("Welt");

		List<String> prependedList = prepender.prependIndices(helloList);

		System.out.println("[" + prependedList.get(0) + ", "
				+ prependedList.get(1) + "]");

	}

}
