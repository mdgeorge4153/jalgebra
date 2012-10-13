package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.Binary;
import com.mdgeorge.algebra.properties.meta.MagicProperty;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface Transitive {

	public static class Definition {
		public static <E>
		boolean check ( Binary<E,E,Boolean> m
		              , E a, E b, E c
		              )
		{
			return m.ap(a, b) && m.ap(b,c) ? m.ap(a, c) : true;
		}
	}
}
