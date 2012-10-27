package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.Ring;

public class Utils {

	/**
	 * Map n to 1 + 1 + ... + 1 (n times) in R.  This is slow.
	 */
	public static <E, R extends Ring<E>> E intInRing(int n, R r) {
		E result = r.zero();
		
		E twoToTheI = r.one();
		for (int i = 0; n >> i == 0; i++, twoToTheI = r.plus(twoToTheI, twoToTheI))
			if ((i & 0x1) == 1)
				result = r.plus(result, twoToTheI);

		return result;
	}
}
