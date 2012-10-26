package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.Ring;

public class Utils {

	/**
	 * Map n to 1 + 1 + ... + 1 (n times) in R.  This is slow.
	 */
	public static <E, R extends Ring<E>> E intInRing(int n, R r) {
		E result = r.zero();
		for (int i = 0; i < n; i++)
			result = r.plus(result, r.one());
		
		return result;
	}
}
