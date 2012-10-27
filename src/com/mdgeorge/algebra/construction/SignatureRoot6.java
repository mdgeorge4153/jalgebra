package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.Ring;
import com.mdgeorge.algebra.properties.meta.OpUnary;

public final class SignatureRoot6<E, R extends Ring<E>>
        implements Extension.Signature<E>
{
	private final R r;
	
	public SignatureRoot6(R r) {
		this.r = r;
	}

	@SuppressWarnings("unchecked")
	public final E[][][] coefficients() {
		OpUnary<Integer,E> inj = Z.into(r);
		E e0 = inj.ap(0);
		E e1 = inj.ap(1);
		E e2 = inj.ap(2);
		E e3 = inj.ap(3);
		E e6 = inj.ap(6);
		E e9 = inj.ap(9);

		return (E[][][]) new Object[][][]
			{
				{ { e1 , e0 , e0 , e0 } // √1 * √1
				, { e0 , e1 , e0 , e0 } // √1 * √2
				, { e0 , e0 , e1 , e0 } // √1 * √3
				, { e0 , e0 , e0 , e1 } // √1 * √6
				},

				{ { e0 , e1 , e0 , e0 } // √2 * √1 
				, { e2 , e0 , e0 , e0 } // √2 * √2
				, { e0 , e0 , e0 , e1 } // √2 * √3
				, { e0 , e0 , e2 , e0 } // √2 * √6
				},

				{ { e0 , e0 , e1 , e0 } // √3 * √1
				, { e0 , e0 , e0 , e1 } // √3 * √2
				, { e3 , e0 , e0 , e0 } // √3 * √3
				, { e0 , e3 , e0 , e0 } // √3 * √6
				},

				{ { e0 , e0 , e0 , e1 } // √6 * √1
				, { e0 , e0 , e2 , e0 } // √6 * √2
				, { e0 , e9 , e0 , e0 } // √6 * √3
				, { e6 , e0 , e0 , e0 } // √6 * √6
				},
			};
	}
	
	@Override
	public int dimension() {
		return 4;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E[] one() {
		return (E[]) new Object[] {r.one(), r.zero(), r.zero(), r.zero()};
	}

	@Override
	public String generatorName(int i) throws IllegalArgumentException {
		switch(i) {
		case 0: return "√1";
		case 1: return "√2";
		case 2: return "√3";
		case 3: return "√6";
		default: throw new IllegalArgumentException();
		}
	}
}
