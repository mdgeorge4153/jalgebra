package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;
import com.mdgeorge.algebra.properties.meta.annotation.MethodName;
import com.mdgeorge.util.OpBinary;

/**
 * A @Commutative function f : E → E → R has the property that f(a,b) == f(b,a).
 * @author mdgeorge
 *
 */
@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface Commutative {
	@MethodName String eq() default "eq";
	
	public static class Definition {
		public static <E,R>
		boolean check ( OpBinary<E,E,R> m, OpBinary<R,R,Boolean> eq
		              , E a, E b
		              )
		{
			return eq.ap(m.ap(a, b), m.ap(b, a));
		}
	}
}
