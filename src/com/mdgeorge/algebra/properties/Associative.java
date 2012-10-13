package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.Binary;
import com.mdgeorge.algebra.properties.meta.MagicCheck;
import com.mdgeorge.algebra.properties.meta.MethodName;


/**
 * An @Associative function f : E → E → E has the property that
 *
 * f(a, f(b,c)) = f(f(a,b), c) 
 *
 * @author mdgeorge
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@MagicCheck
public @interface Associative {

	@MethodName String eq() default "eq";
	
	public static class Definition {
		public static <E>
		boolean check ( Binary<E,E,E> m
		               , Binary<E,E,Boolean> eq
		               , E a, E b, E c
		               )
		{
			return eq.ap ( m.ap(a, m.ap(b,c))
			              , m.ap(m.ap(a,b), c)
			              );
		}
	}
}
