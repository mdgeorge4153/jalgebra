package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.Field;

public class Floats implements Field<Float> {
	public static final float threshold = 0.0001f;
	
	@Override
	public Float one() {
		return 1f;
	}

	@Override
	public Float plus(Float a, Float b) {
		return a + b;
	}

	@Override
	public Float neg(Float a) {
		return - a;
	}

	@Override
	public Float zero() {
		return 0f;
	}

	@Override
	public Boolean eq(Float a, Float b) {
		return -threshold < a - b && a - b < threshold;  
	}

	@Override
	public Float times(Float a, Float b) {
		return a * b;
	}

	@Override
	public Float inv(Float a) throws IllegalArgumentException {
		if (a == 0)
			throw new IllegalArgumentException("Divide by zero");
		
		return 1/a;
	}
}
