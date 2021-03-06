package com.mdgeorge.util;

import java.util.Iterator;

public final class Utils {
	public static String join(Iterable<?> elements, String delim) {
		StringBuilder result = new StringBuilder();

		Iterator<?> i = elements.iterator();
		while (i.hasNext()) {
			result.append(i.next());
			if (i.hasNext())
				result.append(delim);
		}
		
		return result.toString();
	}
}
