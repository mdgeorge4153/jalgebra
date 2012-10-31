package com.mdgeorge.algebra.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;
import com.mdgeorge.algebra.properties.meta.annotation.MethodDup;
import com.mdgeorge.algebra.properties.meta.annotation.MethodExt;
import com.mdgeorge.algebra.properties.meta.annotation.MethodName;
import com.mdgeorge.util.OpNullary;
import com.mdgeorge.util.OpTernary;
import com.mdgeorge.util.OpUnary;

@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface CommutesWith {
	@MethodExt  String value();
	@MethodDup  String codValue() default "value";
	@MethodExt  String eq()       default "com.mdgeorge.algebra.concept.Set.eq";
	@MethodName String domain()   default "domain";
	@MethodName String codomain() default "codomain";

	public static class Definition {
		public static <S, DE, RE, D, R>
		boolean check ( OpUnary<DE,RE> f
		              , OpTernary<D,DE,DE,DE> value
		              , OpTernary<R,RE,RE,RE> codValue
		              , OpTernary<R,RE,RE,Boolean> eq
		              , OpNullary<D> domain
		              , OpNullary<R> codomain
		              , DE a, DE b
		              )
		{
			return eq.ap( codomain.ap()
			            , codValue.ap(codomain.ap(), f.ap(a), f.ap(b))
					    , f.ap(value.ap(domain.ap(), a,b)));
		}
	}
}
