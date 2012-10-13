package com.mdgeorge.algebra.properties.meta;

import java.lang.reflect.Method;

public class Binary<T1, T2, R> {

	private final Method method;
	private final Object receiver;
	
	public Binary (Method method, Object receiver) {
		this.method   = method;
		this.receiver = receiver;
	}
	
	public R ap (T1 t1, T2 t2) {
		try {
			return (R) method.invoke(receiver, t1, t2);
		} catch (Exception e) {
			throw new Error("TODO");
		}
	}
}
