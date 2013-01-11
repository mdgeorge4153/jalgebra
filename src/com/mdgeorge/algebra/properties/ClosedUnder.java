package com.mdgeorge.algebra.properties;

import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;
import com.mdgeorge.algebra.properties.meta.annotation.MethodName;
import com.mdgeorge.util.OpBinary;
import com.mdgeorge.util.OpUnary;

/* @MagicProperty */
public @interface ClosedUnder {
	@MethodName String[] value();
	
	public static class Definition {
		public static <E>
		boolean check ( OpUnary<E, Boolean> m
		              , OpBinary<E, E, E>   value
		              , E a, E b
		              )
		{
			return m.ap(a) && m.ap(b) ? m.ap(value.ap(a,b)) : true;
		}
	}
}
