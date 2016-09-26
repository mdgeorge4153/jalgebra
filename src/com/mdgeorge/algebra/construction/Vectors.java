package com.mdgeorge.algebra.construction;

import java.lang.reflect.Array;

import com.mdgeorge.algebra.concept.Field;
import com.mdgeorge.algebra.concept.VectorSpace;
import com.mdgeorge.algebra.numbers.NumberType;

public class Vectors<N extends NumberType,E>
  implements VectorSpace<Vectors<N,E>.Vec, E>
{
	private final int n;
	private final Class<E> clz;
	private final Field<E> f;
	
	public Vectors (N n, Class<E> clz, Field<E> f) {
		this.n   = n.value;
		this.clz = clz;
		this.f   = f;
	}
	
	public class Vec {
		private E[] values;
		
		@SuppressWarnings("unchecked")
		private Vec() {
			this.values = (E[]) Array.newInstance(clz, n);
		}
		
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder("[");
			for (int i = 0; i < n; i++) {
				result.append(this.values[i].toString());
				if (i+1 < n)
					result.append(", ");
			}
			result.append("]");
			return result.toString();
		}
		
		public E get(int i) {
			return this.values[i];
		}
	}
	
	public Vec make(E[] values) {
		if (values.length != n)
			throw new IllegalArgumentException();
		Vec result = new Vec();
		for (int i = 0; i < n; i++)
			result.values[i] = values[i];
		return result;
	}

	@Override
	public Vec smult(E s, Vec a) {
		Vec result = new Vec();
		
		for (int i = 0; i < n; i++)
			result.values[i] = f.times(s, a.values[i]);
		return result;
	}

	@Override
	public Vec plus(Vec a, Vec b) {
		Vec result = new Vec();
		
		for (int i = 0; i < n; i++)
			result.values[i] = f.plus(a.values[i], b.values[i]);
		return result;
	}

	@Override
	public Vec neg(Vec a) {
		Vec result = new Vec();
		for (int i = 0; i < n; i++)
			result.values[i] = f.neg(a.values[i]);
		return result;
	}

	@Override
	public Vec zero() {
		Vec result = new Vec();
		
		for (int i = 0; i < n; i++)
			result.values[i] = f.zero();
		return result;
	}

	@Override
	public Boolean eq(Vec a, Vec b) {
		for (int i = 0; i < n; i++)
			if (!f.eq(a.values[i], b.values[i]))
				return false;
		return true;
	}

	@Override
	public Field<E> scalars() {
		return f;
	}
	
}
