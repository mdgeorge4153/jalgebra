package com.mdgeorge.algebra.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import com.mdgeorge.algebra.concept.Field;
import com.mdgeorge.algebra.construction.Vectors;
import com.mdgeorge.algebra.construction.Zp;
import com.mdgeorge.algebra.numbers.N09;
import com.mdgeorge.algebra.numbers.N10;
import com.mdgeorge.algebra.numbers.NumberType;

public class LinearEquations<N extends NumberType,E> {
	
	private Vectors<N,E> v;
	private Field<E>     f;
	private int n;
	
	public LinearEquations(Vectors<N,E> v, N n) {
		this.n = n.value;
		this.v = v;
		this.f = v.scalars();
	}
	
	private int getFirstVar(Vectors<N,E>.Vec v) {
		for (int i = 0; i < n; i++)
			if (!f.eq(f.zero(), v.get(i)))
				return i;
		return n;
	}

	public void reduce(ArrayList<Vectors<N,E>.Vec> eqns) {
		for (int i = 0; i < eqns.size(); i++) {
			
			// find pivot (remaining row with leftmost non-zero term)
			int nextVar   = getFirstVar(eqns.get(i));
			int nextIndex = i; 
			for (int j = i+1; j < eqns.size(); j++) {
				int var = getFirstVar(eqns.get(j));
				if (var < nextVar) {
					nextVar = var; nextIndex = j;
				}
			}

			if (nextVar == n)
				// all remaining rows contain only 0s.
				break;
						
			// swap it into place i and normalize
			Vectors<N,E>.Vec tmp = eqns.get(nextIndex);
			eqns.set(nextIndex, eqns.get(i));
			eqns.set(i, v.smult(f.inv(tmp.get(nextVar)), tmp));
			
			// nullify remainder of nextVar's column
			for (int j = i+1; j < eqns.size(); j++)
				// eqj = eqj - (coeff j)(eqn i)
				eqns.set(j, v.plus(eqns.get(j), v.smult(f.neg(eqns.get(j).get(nextVar)), eqns.get(i))));
		}
		
		// array is in row-echelon form.  Back-substitute to get RRE form
		
		for (int i = eqns.size()-1; i >= 0; i--) {
			int var = getFirstVar(eqns.get(i));
			for (int j = 0; j < i; j++)
				eqns.set(j, v.plus(eqns.get(j), v.smult(f.neg(eqns.get(j).get(var)), eqns.get(i))));
		}
	}
	
	public static void main(String[] args) {
		int[][] inteqns = new int[][] {
			// a, b, c,
			// d, e, f,
			// g, h, i 
			{ 1, 1, 0, 1, 0, 0, 0, 0, 0, 0 }, // eqn a
			{ 1, 1, 1, 0, 1, 0, 0, 0, 0, 0 }, // eqn b
			{ 0, 1, 1, 0, 0, 1, 0, 0, 0, 0 }, // eqn c
			{ 1, 0, 0, 1, 1, 0, 1, 0, 0, 0 }, // eqn d
			{ 1, 0, 1, 0, 1, 0, 1, 0, 1, 0 }, // eqn e
			{ 0, 0, 1, 0, 1, 1, 0, 0, 1, 0 }, // eqn f
			{ 0, 0, 0, 1, 0, 0, 1, 1, 0, 2 }, // eqn g
			{ 0, 0, 0, 0, 1, 0, 1, 1, 1, 0 }, // eqn h
			{ 0, 0, 0, 0, 0, 1, 0, 1, 1, 0 }  // eqn i
		};
		
		Vectors<N10, Integer> v = new Vectors<N10, Integer>(N10.instance, Integer.class, new Zp(3));
		Field<Integer> f = v.scalars();

		ArrayList<Vectors<N10, Integer>.Vec> eqns = new ArrayList<Vectors<N10, Integer>.Vec>();
		
		for (int i = 0; i < 9; i++) {
			Integer[] coeffs = new Integer[10];
			for (int j = 0; j < 10; j++)
				coeffs[j] = inteqns[i][j];
			Vectors<N10, Integer>.Vec eqn = v.make(coeffs);
			eqns.add(eqn);
		}

		LinearEquations<N10, Integer> le = new LinearEquations<N10, Integer>(v, N10.instance);
		le.reduce(eqns);
		for (Object o : eqns)
			System.out.println(o.toString());
	}
}