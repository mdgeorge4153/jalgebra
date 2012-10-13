package com.mdgeorge.algebra.properties;

import com.mdgeorge.algebra.properties.meta.Binary;
import com.mdgeorge.algebra.properties.meta.MagicCheck;
import com.mdgeorge.algebra.properties.meta.MethodName;
import com.mdgeorge.algebra.properties.meta.MethodRef;
import com.mdgeorge.algebra.properties.meta.Nullary;
import com.mdgeorge.algebra.properties.meta.Unary;

@MagicCheck
public @interface InverseOf {
	@MethodName String eq    () default "eq";
	@MethodName String value ();
	@MethodRef  String id    () default "Identity";
	
	public static class Definition {
		public static <E>
		boolean check ( Unary<E,E> m
		              , Binary<E,E,Boolean> eq, Binary<E,E,E> value, Nullary<E> id
		              , E a
		              )
		{
			return eq.ap (id.ap(), value.ap(a, m.ap(a)))
			    && eq.ap (id.ap(), value.ap(m.ap(a), a));
		}
	}
}
