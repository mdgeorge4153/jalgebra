package com.mdgeorge.algebra.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;
import com.mdgeorge.algebra.properties.meta.annotation.MethodName;
import com.mdgeorge.util.OpBinary;

@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface Bilinear {
	@MethodName String smult() default "smult";
	@MethodName String eq()    default "eq";
	
	public static class Definition {
		public static <S,E>
		boolean check ( OpBinary<E, E, E>       m
		              , OpBinary<S, E, E>       smult
		              , OpBinary<E, E, Boolean> eq
		              , S s, E a, E b
		              )
		{
			return eq.ap( smult.ap(s, m.ap(a, b))
			            , m.ap(smult.ap(s, a), b)
			            )
			    && eq.ap( smult.ap(s, m.ap(a, b))
			            , m.ap(a, smult.ap(s, b))
			            );
		}
	}
}
