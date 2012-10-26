package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.Field;
import com.mdgeorge.algebra.concept.IntegralDomain;
import com.mdgeorge.algebra.properties.meta.MagicCheck;

@MagicCheck
public class FieldOfFractions<E, D extends IntegralDomain<E>>
  implements Field<FieldOfFractions<E,D>.Element>
{
	private final D d;
	
	public final class Element extends Fraction<E,E,D>
	{
		public Element(E num, E den, D r) throws IllegalArgumentException {
			super(num, den, r);
		}
	}
	
	public FieldOfFractions(D d) {
		this.d = d;
	}

	@Override
	public Element times(Element a, Element b) {
		return new Element ( d.times(a.num, b.num)
				           , d.times(a.den, b.den)
				           , d
				           );
	}

	@Override
	public Element one() {
		return new Element (d.one(), d.one(), d);
	}

	@Override
	public Element plus(Element a, Element b) {
		return new Element ( d.plus  ( d.times(a.num, b.den)
		                             , d.times(b.num, a.den)
		                             )
		                   , d.times ( a.den, b.den )
		                   , d
		                   );
	}

	@Override
	public Element neg(Element a) {
		return new Element(d.neg(a.num), a.den, d);
	}

	@Override
	public Element zero() {
		return new Element(d.zero(), d.one(), d);
	}

	@Override
	public Boolean eq(Element a, Element b) {
		return d.eq(d.times(a.num, b.den), d.times(b.num, a.den));
	}

	@Override
	public Element inv(Element a) {
		if (d.eq(a.num, d.zero()))
			throw new IllegalArgumentException();
		
		return new Element(a.den, a.num, d);
	}
	
	public Element inj(E e) {
		return new Element(e, d.one(), d);
	}
}
