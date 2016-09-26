package com.mdgeorge.algebra.construction;

import java.lang.reflect.Array;

import com.mdgeorge.algebra.concept.Ring;

public class Matrices<E, N extends Number>
  implements Ring<Matrices<E,N>.Matrix>
{
	private Ring<E>  r;
	private int      size;
	private Class<E> clz;

	public class Matrix {
		/** length size*size; (i,j) entry in values[i*size + j] */
		private E[] values;

		public Matrix(E[][] values) throws IllegalArgumentException {
			this();
			
			if (values.length != size)
				throw new IllegalArgumentException("Matrix<N> constructor requires an nxn array");

			for (int i = 0; i < size; i++) {
				if (values[i].length != size)
					throw new IllegalArgumentException("Matrix<N> constructor requires an nxn array");
				for (int j = 0; j < size; j++)
					this.values[i*size + j] = values[i][j];
			}
		}
		
		@SuppressWarnings("unchecked")
		private Matrix() {
			this.values = (E[]) Array.newInstance(clz, size*size);			
		}

		private void set(int i, int j, E e) {
			this.values[i*size + j] = e;
		}
		
		public E get(int i, int j) {
			return values[i*size + j];
		}
		
		@Override
		public String toString() {
			StringBuilder result = new StringBuilder("[");
			for (int i = 0; i < size; i++) {
				result.append("[");
				for (int j = 0; j < size; j++) {
					result.append(get(i,j));
					if (j+1 < size)
						result.append(", ");
				}
				result.append("]");
				if (i+1 < size)
					result.append(", ");
			}
			result.append("]");
			return result.toString();
		}

	}

	@Override
	public Matrix plus(Matrix a, Matrix b) {
		Matrix result = new Matrix();
		
		for (int i = 0; i < size*size; i++)
			result.values[i] = r.plus(a.values[i], b.values[i]);
	
		return result;
	}

	@Override
	public Matrix neg(Matrix a) {
		Matrix result = new Matrix();
		
		for (int i = 0; i < size*size; i++)
			result.values[i] = r.neg(a.values[i]);
		
		return result;
	}

	@Override
	public Matrix zero() {
		Matrix result = new Matrix();
		
		for (int i = 0; i < size * size; i++)
			result.values[i] = r.zero();
		return result;
	}

	@Override
	public Boolean eq(Matrix a, Matrix b) {
		for (int i = 0; i < size * size; i++)
			if (a.values[i] != b.values[i])
				return false;
		return true;
	}

	@Override
	public Matrix times(Matrix a, Matrix b) {
		Matrix result = new Matrix();
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				E entry = r.zero();
				for (int k = 0; k < size; k++)
					entry = r.plus(entry, r.times(a.get(i, k), b.get(k, j)));
				result.set(i,j,entry);
			}
		return result;
	}

	@Override
	public Matrix one() {
		Matrix result = new Matrix();
		
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				result.set(i, j, i == j ? r.one() : r.zero());

		return result;

	}
}

