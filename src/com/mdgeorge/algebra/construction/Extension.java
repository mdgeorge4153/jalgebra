package com.mdgeorge.algebra.construction;

import java.util.ArrayList;
import java.util.List;

import com.mdgeorge.algebra.concept.Algebra;
import com.mdgeorge.algebra.concept.Ring;
import com.mdgeorge.algebra.concept.RingHom;
import com.mdgeorge.algebra.properties.meta.MagicCheck;
import com.mdgeorge.util.Utils;

@MagicCheck
public abstract class Extension< E
                               , R extends Ring<E>
                               , S extends Extension.Signature<E>
                               >
           implements Algebra<Extension<E,R,S>.Element, E, R>
{
	public interface Signature<E> {
		/**
		 * The number of generators of the extension as a module over R.
		 */
		int dimension();
		
		/**
		 * The structure coefficient tensor for extension. If the generators are
		 * e0, e1, ..., en, then it should be the case that
		 * 
		 *   ei * ej = sum over k of (coefficients()[i][j][k] * ek).
		 *   
		 * Each dimension of the returned array must be of size dimension(). 
		 */
		E[][][] coefficients();
		
		/**
		 * The coefficients of R.one() as an element of the extended algebra.
		 */
		E[]     one();

		/**
		 * A useful string representation of the i'th generator.
		 */
		String generatorName(int i) throws IllegalArgumentException;
	}
	
	public final class Element {
		private final E [] coeffs;
		
		/**
		 * Create a new element of the extension with the given coefficients.
		 * The caller is expected to not change the array after it is passed in.
		 * 
		 * @throws IllegalArgumentException
		 *         if the number of coefficients is not S.dimension().
		 */
		public Element (E... coeffs) throws IllegalArgumentException {
			if (coeffs.length != s.dimension())
				throw new IllegalArgumentException ( "An element of " +
				                                     Extension.this.toString() +
				                                     " must have " + n +
				                                     " coefficients."
				                                   );
			
			this.coeffs = coeffs;
			
		}
		
		/**
		 * @return the i'th coefficient of this element. 
		 */
		public E get (int i) {
			if (i < 0 || i > coeffs.length)
				throw new IllegalArgumentException();

			return this.coeffs[i];
		}
		
		/**
		 * @return a useful String represenation of this element.
		 */
		public String toString() {
			List<String> result = new ArrayList<String> (s.dimension());
			for (int i = 0; i < n; i++)
			{
				if (!r.eq(coeffs[i], r.zero()))
					result.add(coeffs[i].toString() + "⋅" + s.generatorName(i));
			}
			
			if (result.isEmpty())
				return r.zero().toString();
			else
				return "(" + Utils.join(result, "+") + ")";
		}
	}

	public final class NaturalHom
	        implements RingHom<E, R, Element, Extension<E,R,S>>
	{
		public  R                domain()   { return r; }
		public  Extension<E,R,S> codomain() { return Extension.this; }
		public  Element ap (E e) { return smult(e,one()); }
	}
	
	public final NaturalHom INJ = new NaturalHom();
	
	private final S   s;
	private final R   r;
	private final int n;

	protected Extension(S s, R r) {
		this.s = s;
		this.r = r;
		this.n = s.dimension();
	}

	@Override
	public Element plus(Element a, Element b) {
		E[] result = makeArray();
		for (int i = 0; i < n; i++)
			result[i] = r.plus(a.coeffs[i], b.coeffs[i]);
		return new Element(result);
	}
	
	@Override
	public Element neg(Element a) {
		E[] result = makeArray();
		for (int i = 0; i < n; i++)
			result[i] = r.neg(a.coeffs[i]);
		return new Element(result);
	}

	@Override
	public Element zero() {
		E[] result = makeArray();
		for (int i = 0; i < n; i++)
			result[i] = r.zero();
		return new Element(result);
	}

	@Override
	public Boolean eq(Element a, Element b) {
		for (int i = 0; i < n; i++)
			if (!r.eq(a.coeffs[i], b.coeffs[i]))
				return false;
		return true;
	}

	@Override
	public Element times(Element a, Element b) {

		E[] result = makeArray();
		for (int i = 0; i < n; i++)
			result[i] = r.zero();
		
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				for (int k = 0; k < n; k++)
					result[k] = r.plus ( result[k]
					                   , r.times( s.coefficients()[i][j][k]
					                            , r.times(a.coeffs[i], b.coeffs[j])
					                            )
					                   );
		
		return new Element(result);
	}

	@Override
	public Element one() {
		return new Element(s.one());
	}

	@Override
	public Element smult(E s, Element a) {
		E[] result = makeArray();
		for (int i = 0; i < n; i++)
			result[i] = r.times(s, a.coeffs[i]);
		return new Element(result);
	}
	
	@Deprecated
	public Element inj(E e) {
		return INJ.ap(e);
	}

	/**
	 * A string representation of this object, suitable for debugging.
	 */
	public String toString() {
		List<String> names = new ArrayList<String> (s.dimension());
		
		for (int i = 0; i < n; i++)
			names.add(s.generatorName(i));
		
		return r.toString() + "[" + Utils.join(names, ",") + "]"; 
	}
	
	/**
	 * Create a new array of elements of size n.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private E[] makeArray() {
		return (E[]) new Object[n];
	}
}
