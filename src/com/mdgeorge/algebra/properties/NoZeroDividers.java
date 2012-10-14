package com.mdgeorge.algebra.properties;

import com.mdgeorge.algebra.properties.meta.OpBinary;
import com.mdgeorge.algebra.properties.meta.MagicProperty;
import com.mdgeorge.algebra.properties.meta.OpNullary;

@MagicProperty
public @interface NoZeroDividers {
	
	public static class Definition {
		public static <E>
		boolean check ( OpBinary<E,E,E> m
		              , OpBinary<E,E,Boolean> eq, OpNullary<E> zero
		              , E a, E b
		              )
		{
			return eq.ap(m.ap(a,b), zero.ap())
			     ? eq.ap(a, zero.ap()) || eq.ap(b, zero.ap())
			     : true;
		}
	}
}
