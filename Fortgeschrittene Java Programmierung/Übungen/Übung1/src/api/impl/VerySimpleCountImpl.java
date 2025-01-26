package api.impl;

import api.CountResult;
import api.VerySimpleCount;

public class VerySimpleCountImpl implements VerySimpleCount {

	@Override
	public CountResult countOccurrences(String haystack, String needle) {
		int index = -1;
		for (int i = 0; i < 2; i++) {
			index = haystack.indexOf(needle, index+1);
			if (index == -1) {
				if (i == 0) {
					return CountResult.ZERO;
				} else{
					return CountResult.ONE;
				}
			}
		}
		return CountResult.MORE;
	}

}
