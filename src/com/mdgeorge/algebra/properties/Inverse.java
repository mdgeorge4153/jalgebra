package com.mdgeorge.algebra.properties;

import com.mdgeorge.algebra.properties.meta.OpBinary;
import com.mdgeorge.algebra.properties.meta.MagicProperty;
import com.mdgeorge.algebra.properties.meta.MethodName;
import com.mdgeorge.algebra.properties.meta.MethodRef;
import com.mdgeorge.algebra.properties.meta.OpNullary;
import com.mdgeorge.algebra.properties.meta.OpUnary;

@MagicProperty
public @interface Inverse {
	@MethodName String eq    () default "eq";
	@MethodName String value ();
	@MethodRef  String id    () default "com.mdgeorge.algebra.properties.Identity";
	
	public static class Definition {
		public static <E>
		boolean check ( OpBinary<E,E,E> m
		              , OpBinary<E,E,Boolean> eq, OpUnary<E,E> value, OpNullary<E> id
		              , E a
		              )
		{
			return eq.ap (id.ap(), m.ap(a, value.ap(a)))
			    && eq.ap (id.ap(), m.ap(value.ap(a), a));
		}
	}
}
