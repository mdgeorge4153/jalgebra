package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.Ring;

public final class SignatureComplex<E, R extends Ring<E>>
        implements Extension.Signature<E>
{
	private final R r;

	public SignatureComplex(R r) {
		this.r = r;
	}

	@SuppressWarnings("unchecked")
	public final E[][][] coefficients() {
		E e0  = r.zero();
		E e1  = r.one();
		E n1  = r.neg(e1);

		return (E[][][]) new Object[][][]
			{
				{ { e1 , e0 } // 1 * 1
				, { e0 , e1 } // 1 * i
				},

				{ { e0 , e1 } // i * 1 
				, { n1 , e0 } // i * i
				},
			};
	}
	
	@Override
	public int dimension() {
		return 2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E[] one() {
		return (E[]) new Object[] {r.one(), r.zero()};
	}

	@Override
	public String generatorName(int i) throws IllegalArgumentException {
		switch(i) {
		case 0: return null;
		case 1: return "i";
		default: throw new IllegalArgumentException();
		}
	}
}
