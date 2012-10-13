package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.Binary;
import com.mdgeorge.algebra.properties.meta.MagicCheck;
import com.mdgeorge.algebra.properties.meta.MethodName;

/**
 * A @Commutative function f : E → E → R has the property that f(a,b) == f(b,a).
 * @author mdgeorge
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@MagicCheck
public @interface Commutative {
	@MethodName String eq() default "eq";
	
	public static class Definition {
		public static <E,R>
		boolean check ( Binary<E,E,R> m, Binary<R,R,Boolean> eq
		              , E a, E b
		              )
		{
			return eq.ap(m.ap(a, b), m.ap(b, a));
		}
	}
}
