package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.adapters.GroupAsZModule;
import com.mdgeorge.algebra.concept.Algebra;
import com.mdgeorge.algebra.concept.IntegralDomain;
import com.mdgeorge.algebra.concept.OrderedRing;
import com.mdgeorge.algebra.concept.Ring;
import com.mdgeorge.algebra.concept.RingHom;
import com.mdgeorge.algebra.properties.meta.annotation.MagicCheck;

@MagicCheck
public class Z
  implements IntegralDomain<Integer>
           , OrderedRing<Integer>
           , Algebra<Integer, Integer, Z>
{

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

	public static class NaturalHom<E, R extends Ring<E>>
	          implements RingHom<Integer, Z, E, R>
	{
		private final GroupAsZModule<E, R> m;
		private final R                    r;

		private NaturalHom(R r) {
			this.m = new GroupAsZModule<E, R>(r);
			this.r = r;
		}
		
		public Z domain()       { return instance; }
		public R codomain()     { return this.r;   }

		public E ap(Integer n) {
			return m.smult(n, r.one());
		}
	}
	
	public static <E, R extends Ring<E>> NaturalHom<E, R> into (R g) {
		return new NaturalHom<E,R>(g);
	}

	@Override
	public Boolean isNonnegative(Integer a) {
		return a <= 0;
	}

	@Override
	public Integer smult(Integer s, Integer a) {
		return times(s, a);
	}
}
