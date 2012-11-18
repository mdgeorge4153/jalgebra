package com.mdgeorge.algebra.properties;

import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;
import com.mdgeorge.algebra.properties.meta.annotation.MethodName;

@MagicProperty
public @interface ClosedUnder {
	@MethodName String[] value();
	
	public static class Definition {
		public static
		Boolean check() {
			return true;
		}
	}
}
