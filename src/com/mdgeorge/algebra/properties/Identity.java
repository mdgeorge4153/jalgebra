package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;
import com.mdgeorge.algebra.properties.meta.annotation.MethodName;
import com.mdgeorge.util.OpBinary;
import com.mdgeorge.util.OpNullary;

/**
 * A function f : E → E → R with @Identity function g : E has the property that
 * 
 * f(a, g()) = f(g(),a) = a
 * 
 * @author mdgeorge
 *
 */
@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface Identity {
	@MethodName String eq    () default "eq";
	@MethodName String value ();
	
	public static class Definition {
		public static <E>
		boolean check ( OpBinary<E,E,E> m, OpBinary<E,E,Boolean> eq, OpNullary<E> value
		              , E a
		              )
		{
			return eq.ap ( a, m.ap (value.ap(), a) )
			    && eq.ap ( a, m.ap (a, value.ap()) );
		}
	}
}
