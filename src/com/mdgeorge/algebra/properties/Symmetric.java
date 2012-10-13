package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.Binary;
import com.mdgeorge.algebra.properties.meta.MagicCheck;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@MagicCheck
public @interface Symmetric {
	
	public static class Definition {
		public static <E>
		boolean check ( Binary<E,E,Boolean> m
		              , E a, E b
		              )
		{
			return m.ap(a,b) == m.ap(b, a);
		}
	}
}
