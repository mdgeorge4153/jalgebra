package com.mdgeorge.algebra.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mdgeorge.algebra.properties.meta.MagicProperty;
import com.mdgeorge.algebra.properties.meta.MethodName;
import com.mdgeorge.algebra.properties.meta.OpBinary;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface AntiSymmetric {
	@MethodName String eq() default "eq";
	
	public static class Definition {
		public static<E>
		boolean check ( OpBinary<E,E,Boolean> m
		              , OpBinary<E,E,Boolean> eq
		              , E a, E b
		              )
		{
			return m.ap(a, b) && m.ap(b, a)
			     ? eq.ap(a, b)
			     : true;
		}
	}
}
