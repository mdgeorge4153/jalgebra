package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.Binary;
import com.mdgeorge.algebra.properties.meta.MagicProperty;
import com.mdgeorge.algebra.properties.meta.MethodName;

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
		boolean check (Binary <S,E,E> m, Binary<E,E,Boolean> eq, Binary<E,E,E> value
		              , S a, E b, E c
		              )
		{
			return eq.ap ( m.ap(a, value.ap(b, c))
			             , value.ap(m.ap(a,b), m.ap(a,c))
			             );
		}
	}
}
