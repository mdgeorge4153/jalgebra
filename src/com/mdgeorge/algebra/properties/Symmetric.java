package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.OpBinary;
import com.mdgeorge.algebra.properties.meta.MagicProperty;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface Symmetric {
	
	public static class Definition {
		public static <E>
		boolean check ( OpBinary<E,E,Boolean> m
		              , E a, E b
		              )
		{
			return m.ap(a,b) == m.ap(b, a);
		}
	}
}
