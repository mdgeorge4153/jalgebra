package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.Binary;
import com.mdgeorge.algebra.properties.meta.MagicProperty;
import com.mdgeorge.algebra.properties.meta.MethodName;
import com.mdgeorge.algebra.properties.meta.Nullary;

/**
 * A function f : E → E → R with @Identity function g : E has the property that
 * 
 * f(a, g()) = f(g(),a) = a
 * 
 * @author mdgeorge
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface Identity {
	@MethodName String eq    () default "eq";
	@MethodName String value ();
	
	public static class Definition {
		public static <E>
		boolean check ( Binary<E,E,E> m, Binary<E,E,Boolean> eq, Nullary<E> value
		              , E a
		              )
		{
			return eq.ap ( a, m.ap (value.ap(), a) )
			    && eq.ap ( a, m.ap (a, value.ap()) );
		}
	}
}
