package generics.impl;

import generics.IndexPrepender;

import java.util.ArrayList;
import java.util.List;

public class SimpleIndexPrepender implements IndexPrepender {

	@Override
	public List<String> prependIndices(List<String> inputList) {
		List<String> ls = new ArrayList<>();
		int i = 0;
		for (String next : inputList){
			ls.add("" + i + " "+ next);
			i++;
		}
		return ls;
	}

}
