package com.mdgeorge.algebra.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;
import com.mdgeorge.util.OpBinary;
import com.mdgeorge.util.OpNullary;

@Target(ElementType.METHOD)
@Documented
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
