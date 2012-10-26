package com.mdgeorge.algebra.properties;

import com.mdgeorge.algebra.properties.meta.MethodName;
import com.mdgeorge.algebra.properties.meta.MethodPair;
import com.mdgeorge.algebra.properties.meta.OpBinary;
import com.mdgeorge.algebra.properties.meta.OpNullary;
import com.mdgeorge.algebra.properties.meta.OpTernary;
import com.mdgeorge.algebra.properties.meta.OpUnary;

public @interface CommutesWith {
	@MethodPair String value();
	@MethodName String eq()       default "eq";
	@MethodName String domain()   default "domain";
	@MethodName String codomain() default "codomain";

	public static class Definition {
		public static <S, DE, RE, D, R>
		boolean check ( OpUnary<DE,RE> f
		              , OpTernary<D,DE,DE,DE> domValue
		              , OpTernary<R,RE,RE,RE> codValue
		              , OpBinary<RE,RE,Boolean> eq
		              , OpNullary<D> domain
		              , OpNullary<R> codomain
		              , DE a, DE b
		              )
		{
			return eq.ap( codValue.ap(codomain.ap(), f.ap(a), f.ap(b))
					    , f.ap(domValue.ap(domain.ap(), a,b)));
		}
	}
}
