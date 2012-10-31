package com.mdgeorge.algebra.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;
import com.mdgeorge.algebra.properties.meta.annotation.MethodName;
import com.mdgeorge.algebra.properties.meta.annotation.MethodRef;
import com.mdgeorge.util.OpBinary;
import com.mdgeorge.util.OpNullary;
import com.mdgeorge.util.OpUnary;

@Target(ElementType.METHOD)
@Documented
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
