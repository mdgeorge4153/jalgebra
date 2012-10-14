package com.mdgeorge.algebra;

import com.mdgeorge.algebra.properties.meta.MagicCheck;
import com.mdgeorge.util.NotImplementedException;

@MagicCheck
public class Extension< E
                      , R extends Ring<E>
                      , S extends Extension.Signature<E>
                      >
  implements Algebra<Extension<E,R,S>.Element, E, R>
{
	
	private final S   s;
	private final R   r;
	private final int n;
	
	public interface Signature<E> {
		int dimension();
		E   coefficient(int i, int j, int k);
	}
	
	public final class Element {
		private final E [] coeffs;
		
		public Element (E[] coeffs) {
			this.coeffs = coeffs;
		}
	}

	public Extension() {
		throw new NotImplementedException("Extension constructor");
	}

	@Override
	public Element plus(Element a, Element b) {
		E[] result = (E[]) new Object[n];
		for (int i = 0; i < n; i++)
			result[i] = r.plus(a.coeffs[i], b.coeffs[i]);
		return new Element(result);
	}
	
	@Override
	public Element neg(Element a) {
		E[] result = (E[]) new Object[n];
		for (int i = 0; i < n; i++)
			result[i] = r.neg(a.coeffs[i]);
		return new Element(result);
	}

	@Override
	public Element zero() {
		E[] result = (E[]) new Object[n];
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
		E[] result = (E[]) new Object[n];
		for (int i = 0; i < n; i++) {
			E entry = r.zero();
			for (int j = 0; j < n; j++)
				for (int k = 0; k < n; k++)
					entry = r.plus ( entry
					               , r.times(s.coefficient(i, j, k)
					                        , r.times(a.coeffs[j], b.coeffs[k])
					                        )
					               );
			result[i] = entry;
		}
		
		return new Element(result);
	}

	@Override
	public Element one() {
		// TODO Auto-generated method stub
		throw new NotImplementedException("one");
	}

	@Override
	public Element smult(E s, Element a) {
		E[] result = (E[]) new Object[n];
		for (int i = 0; i < n; i++)
			result[i] = r.times(s, a.coeffs[i]);
		return new Element(result);
	}
}
