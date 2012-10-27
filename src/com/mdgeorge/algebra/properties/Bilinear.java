package com.mdgeorge.algebra.properties;

import com.mdgeorge.algebra.properties.meta.MethodName;
import com.mdgeorge.algebra.properties.meta.OpBinary;

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
