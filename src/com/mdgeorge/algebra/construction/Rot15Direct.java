package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.Algebra;
import com.mdgeorge.algebra.concept.Field;
import com.mdgeorge.algebra.concept.OrderedRing;
import com.mdgeorge.algebra.numbers.Three;
import com.mdgeorge.algebra.numbers.Two;
import com.mdgeorge.util.NotImplementedException;

public class Rot15Direct
  implements OrderedRing<Rot15Direct.Element>
           , Field<Rot15Direct.Element>
           , Algebra<Rot15Direct.Element, Integer>
{
	private Sqrt6 s6;
	private Z     z;
	
	public class Element {
		public final Sqrt6.Element num;
		public final Integer       den;
		
		protected Element( Integer a1, Integer a2, Integer a3, Integer a6
		                 , Integer den) {
			
			if (z.eq(den, z.zero()))
				throw new IllegalArgumentException("Division by zero");
			
			Sqrt6.Element num = s6.makeElement(a1, a2, a3, a6);
			
			if (!z.isNonnegative(den)) {
				num = s6.neg(num);
				den =  z.neg(den);
			}
			
			this.num = num;
			this.den = den;
		}
		
		protected Element(Sqrt6.Element num, Integer den) {
			this.num = num;
			this.den = den;
		}
		
		public String toString() {
			String result = num.toString();
			if (den == 1)
				return result;
			else
				return "(" + result + "/" + den + ")";
		}
	}

	protected Element makeElement(Sqrt6.Element num, Integer den) {
		return new Element(num, den);
	}
	
	@Override
	public Z scalars() {
		return Z.instance;
	}
	
	@Override
	public Element times(Element a, Element b) {
		return makeElement( s6.times(a.num, b.num)
		                  ,  z.times(a.den, b.den)
		                  );
	}

	@Override
	public Element one() {
		return makeElement( s6.one(), z.one());
	}

	@Override
	public Element plus(Element a, Element b) {
		return makeElement( s6.plus( s6.smult(b.den, a.num)
		                           , s6.smult(a.den, b.num)
		                           )
		                  , z.times(a.den, b.den)
		                  );
	}

	@Override
	public Element neg(Element a) {
		return makeElement(s6.neg(a.num), a.den);
	}

	@Override
	public Element zero() {
		return makeElement(s6.zero(), z.one());
	}

	@Override
	public Boolean eq(Element a, Element b) {
		return s6.eq(s6.smult(b.den, a.num), s6.smult(a.den, b.num));
	}

	@Override
	public Element smult(Integer s, Element a) {
		return makeElement(s6.smult(s, a.num), a.den);
	}

	@Override
	public Element inv(Element a) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		throw new NotImplementedException("inv");
	}

	@Override
	public Boolean isNonnegative(Element e) {
		return s6.isNonnegative(e.num);
	}
}

final class Sqrt2 extends AdjoinSqrt<Integer, Z, Two> {
	public static final Sqrt2 instance = new Sqrt2();
	
	private Sqrt2() {
		super(Z.instance, Two.instance);
	}
}

final class Sqrt6 extends AdjoinSqrt<Sqrt2.Element, Sqrt2, Three> {
	public static final Sqrt6 instance = new Sqrt6();
	
	private Sqrt6() {
		super(Sqrt2.instance, Three.instance);
	}
	
	public Element makeElement(Integer a1, Integer a2, Integer a3, Integer a6) {
		return makeElement( Sqrt2.instance.makeElement(a1, a2)
		                  , Sqrt2.instance.makeElement(a3, a6)
		                  );
	}
	
	
}

