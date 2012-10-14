package com.mdgeorge.algebra.properties;

import com.mdgeorge.algebra.properties.meta.OpBinary;
import com.mdgeorge.algebra.properties.meta.MagicProperty;
import com.mdgeorge.algebra.properties.meta.MethodName;
import com.mdgeorge.algebra.properties.meta.MethodRef;
import com.mdgeorge.algebra.properties.meta.OpNullary;
import com.mdgeorge.algebra.properties.meta.OpUnary;

@MagicProperty
public @interface InverseOf {
	@MethodName String eq    () default "eq";
	@MethodName String value ();
	@MethodRef  String id    () default "Identity";
	
	public static class Definition {
		public static <E>
		boolean check ( OpUnary<E,E> m
		              , OpBinary<E,E,Boolean> eq, OpBinary<E,E,E> value, OpNullary<E> id
		              , E a
		              )
		{
			return eq.ap (id.ap(), value.ap(a, m.ap(a)))
			    && eq.ap (id.ap(), value.ap(m.ap(a), a));
		}
	}
}
