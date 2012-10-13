package com.mdgeorge.algebra.properties;

import com.mdgeorge.algebra.properties.meta.Binary;
import com.mdgeorge.algebra.properties.meta.MagicProperty;
import com.mdgeorge.algebra.properties.meta.Nullary;

@MagicProperty
public @interface NoZeroDividers {
	
	public static class Definition {
		public static <E>
		boolean check ( Binary<E,E,E> m
		              , Binary<E,E,Boolean> eq, Nullary<E> zero
		              , E a, E b
		              )
		{
			return eq.ap(m.ap(a,b), zero.ap())
			     ? eq.ap(a, zero.ap()) || eq.ap(b, zero.ap())
			     : true;
		}
	}
}
