package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;
import com.mdgeorge.util.OpBinary;

@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface Reflexive {
	
	public static class Definition {
		public static <E>
		boolean check ( OpBinary<E,E,Boolean> m
		              , E a
		              )
		{
			return m.ap(a, a);
		}
	}
}
