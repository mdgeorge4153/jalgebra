package com.mdgeorge.algebra.properties;

import com.mdgeorge.algebra.properties.meta.OpBinary;

public @interface Total {
	public static class Definition {
		public static <E>
		boolean check ( OpBinary<E,E,Boolean> m
		              , E a, E b
		              )
		{
			return m.ap(a, b) || m.ap(b, a);
		}
	}
}
