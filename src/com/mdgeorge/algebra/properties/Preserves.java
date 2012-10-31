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
public @interface Preserves {
	@MethodExt  String value();
	@MethodDup  String codValue() default "value";
	@MethodName String domain()   default "domain";
	@MethodName String codomain() default "codomain";
	
	public static class Definition {
		public static <DE, D, CE, C, R>
		boolean check ( OpUnary<DE, CE> m
		              , OpTernary<D, DE, DE, Boolean> value
		              , OpTernary<C, CE, CE, Boolean> codValue
		              , OpNullary<D> domain
		              , OpNullary<C> codomain
		              , DE a, DE b
		              )
		{
			return value.ap    (domain.ap(),   a,       b)
			     ? codValue.ap (codomain.ap(), m.ap(a), m.ap(b))
			     : true;
		}
	}
}
