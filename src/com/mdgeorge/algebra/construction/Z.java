package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.IntegralDomain;
import com.mdgeorge.algebra.concept.Ring;
import com.mdgeorge.algebra.concept.RingHom;
import com.mdgeorge.algebra.properties.meta.MagicCheck;

@MagicCheck
public class Z implements IntegralDomain<Integer> {

	public final static Z instance = new Z();
	private Z() { }

	@Override
	public Integer one() {
		return 1;
	}

	@Override
	public Integer plus(Integer a, Integer b) {
		return a + b;
	}

	@Override
	public Integer neg(Integer a) {
		return - a;
	}

	@Override
	public Integer zero() {
		return 0;
	}

	@Override
	public Boolean eq(Integer a, Integer b) {
		return a.equals(b);
	}

	@Override
	public Integer times(Integer a, Integer b) {
		return a * b;
	}

	private static class NaturalHom<E, R extends Ring<E>>
	         implements RingHom<Integer, Z, E, R>
	{
		private final R r;
		private NaturalHom(R r) { this.r = r; }
		public Z domain()   { return instance; }
		public R codomain() { return this.r;   }

		public E ap(Integer n) {
			E result = r.zero();
			
			E twoToTheI = r.one();
			for (int i = 0; n >> i == 0; i++, twoToTheI = r.plus(twoToTheI, twoToTheI))
				if ((i & 0x1) == 1)
					result = r.plus(result, twoToTheI);

			return result;
		}
	}
	
	public static <E, R extends Ring<E>> NaturalHom<E, R> into (R g) {
		return new NaturalHom<E,R>(g);
	}
}
