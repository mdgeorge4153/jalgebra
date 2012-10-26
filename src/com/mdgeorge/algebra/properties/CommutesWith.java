package com.mdgeorge.algebra.properties;

import com.mdgeorge.algebra.properties.meta.MethodName;

public @interface CommutesWith {
	@MethodName String value();
	@MethodName String eq()     default "eq";
}
