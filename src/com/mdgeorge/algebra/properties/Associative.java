package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;
import com.mdgeorge.algebra.properties.meta.annotation.MethodName;
import com.mdgeorge.util.OpBinary;


/**
 * An @Associative function f : E → E → E has the property that
 *
 * f(a, f(b,c)) = f(f(a,b), c) 
 *
 * @author mdgeorge
 */
@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface Associative {

	@MethodName String eq() default "eq";
	
	public static class Definition {
		public static <E>
		boolean check ( OpBinary<E,E,E> m
		               , OpBinary<E,E,Boolean> eq
		               , E a, E b, E c
		               )
		{
			return eq.ap ( m.ap(a, m.ap(b,c))
			              , m.ap(m.ap(a,b), c)
			              );
		}
	}
}
