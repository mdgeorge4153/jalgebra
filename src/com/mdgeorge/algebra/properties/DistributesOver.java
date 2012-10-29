package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;
import com.mdgeorge.algebra.properties.meta.annotation.MethodName;
import com.mdgeorge.util.OpBinary;

/**
 * A function f : S → E → E that @DistributesOver a function g : E → E → E has
 * the property that
 * 
 * f(a,g(b,c)) = g(f(a,b), f(a,c))
 *  
 * @author mdgeorge
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface DistributesOver {
	@MethodName String eq    () default "eq";
	@MethodName String value ();
	
	public static class Definition {
		public static <S,E>
		boolean check (OpBinary <S,E,E> m, OpBinary<E,E,Boolean> eq, OpBinary<E,E,E> value
		              , S a, E b, E c
		              )
		{
			return eq.ap ( m.ap(a, value.ap(b, c))
			             , value.ap(m.ap(a,b), m.ap(a,c))
			             );
		}
	}
}
