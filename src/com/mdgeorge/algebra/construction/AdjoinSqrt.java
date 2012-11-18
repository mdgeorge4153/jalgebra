package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.Algebra;
import com.mdgeorge.algebra.concept.OrderedRing;
import com.mdgeorge.algebra.numbers.NumberType;

public class AdjoinSqrt< E
                       , R extends OrderedRing<E> & Algebra<E, Integer, Z>
                       , N extends NumberType
                       >
  implements OrderedRing<AdjoinSqrt<E,R,N>.Element>
           , Algebra<AdjoinSqrt<E,R,N>.Element, Integer, Z>
{
	private final E n;
	private final R r;

	public class Element {
		public final E c1;
		public final E cn;
		
		protected Element(E a1, E an) {
			this.c1 = a1;
			this.cn = an;
		}
		
		@Override
		public String toString() {
			if (eq(this, zero()))
				return "0";
			
			if (r.eq(c1, r.zero()))
				return cn.toString() + "√" + n;
			
			if (r.eq(cn, r.zero()))
				return c1.toString();
			
			return "(" + c1.toString() + " + " + cn.toString() + "√" + n + ")";
		}
	}
	
	public Element makeElement(E a1, E an) {
		return new Element(a1, an);
	}
	
	public AdjoinSqrt(R r, N n) {
		this.n = Z.into(r).ap(n.value);
		this.r = r;
	}

	@Override
	public Element times(Element a, Element b) {
		// (a1 + an√n) (b1 + bn√n)
		
		// r1 = a1 * b1 + n * an * bn
		E r1 = r.plus( r.times(a.c1, b.c1)
		             , r.times(n, r.times(a.cn, b.cn))
		             );
		// r2 = a1 * bn + an * b1;
		E rn = r.plus( r.times(a.cn, b.c1)
		             , r.times(a.c1, b.cn)
		             );
		
		// r = r1 + rn*√n
		return makeElement(r1, rn);
	}

	@Override
	public Element one() {
		return makeElement(r.one(), r.zero());
	}

	@Override
	public Element plus(Element a, Element b) {
		return makeElement(r.plus(a.c1, b.c1), r.plus(a.cn, b.cn));
	}

	@Override
	public Element neg(Element a) {
		return makeElement(r.neg(a.c1), r.neg(a.cn));
	}

	@Override
	public Element zero() {
		return makeElement(r.zero(), r.zero());
	}

	@Override
	public Boolean eq(Element a, Element b) {
		return r.eq(a.c1, b.c1) && r.eq(a.cn, b.cn);
	}

	@Override
	public Boolean isNonnegative(Element e) {
		boolean nn1 = r.isNonnegative(e.c1);
		boolean nnn = r.isNonnegative(e.cn);
		
		if (nn1 && nnn)
			// both coeffs are non-negative
			return true;
		
		if (!nn1 && !nnn)
			// both coeffs are negative
			return false;

		// determinant = c1^2 - n*cn^2
		E determinant = timesConj(e);
		
		if (nn1) {
			// c1 is >= 0
			// cn is <  0
			//
			// the following are equivalent:
			//     c1 + cn√n >= 0
			//            c1 >= (-cn)√n (>= 0)
			//          c1^2 >= n*cn^2
			// c1^2 - n*cn^2 >= 0
			//   determinant >= 0
			return r.isNonnegative(determinant);
		}
		
		else {
			// c1 is < 0
			// cn is >= 0
			//
			// the following are equivalent:
			//     c1 + cn√n >= 0
			//          cn√n >= (-c1) (>= 0)
			//        n*cn^2 >= c1^2
			// n*cn^2 - c1^2 >= 0
			// - determinant >= 0
			return r.isNonnegative(r.neg(determinant));
		}
	}
	
	@Override
	public Element smult(Integer s, Element a) {
		return makeElement(r.smult(s, a.c1), r.smult(s, a.cn));
	}

	public Element conj(Element e) {
		return makeElement(e.c1, r.neg(e.cn));
	}

	public E timesConj(Element e) {
		// (c1 + cn√n)(c1 - cn√n) = c1^2 - n*cn^2
		return r.plus( r.times(e.c1, e.c1)
		             , r.neg (r.times(n, r.times(e.cn, e.cn)))
		             );
	}

}
