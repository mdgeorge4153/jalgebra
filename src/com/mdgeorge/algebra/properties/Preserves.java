package com.mdgeorge.algebra.properties;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mdgeorge.algebra.properties.meta.MagicProperty;
import com.mdgeorge.algebra.properties.meta.MethodName;
import com.mdgeorge.algebra.properties.meta.MethodExt;
import com.mdgeorge.algebra.properties.meta.MethodDup;
import com.mdgeorge.algebra.properties.meta.OpNullary;
import com.mdgeorge.algebra.properties.meta.OpTernary;
import com.mdgeorge.algebra.properties.meta.OpUnary;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface Preserves {
	@MethodExt  String value();
	@MethodDup  String codEq()    default "com.mdgeorge.algebra.properties.Preserves";
	@MethodName String domain()   default "domain";
	@MethodName String codomain() default "codomain";
	
	public static class Definition {
		public static <DE, D, CE, C, R>
		boolean check ( OpUnary<DE, CE> m
		              , OpTernary<D, DE, DE, Boolean> domEq
		              , OpTernary<C, CE, CE, Boolean> codEq
		              , OpNullary<D> domain
		              , OpNullary<C> codomain
		              , DE a, DE b
		              )
		{
			return domEq.ap(domain.ap(),   a,       b)
			     ? codEq.ap(codomain.ap(), m.ap(a), m.ap(b))
			     : true;
		}
	}
}
