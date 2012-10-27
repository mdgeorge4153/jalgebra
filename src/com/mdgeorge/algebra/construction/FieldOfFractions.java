package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.Field;
import com.mdgeorge.algebra.concept.IntegralDomain;
import com.mdgeorge.algebra.concept.RingHom;
import com.mdgeorge.algebra.properties.meta.MagicCheck;

public abstract class FieldOfFractions<E, D extends IntegralDomain<E>>
           implements Field<FieldOfFractions<E,D>.Element>
{
	private final D d;
	
	public final class Element
	{
		public  final E num;
		public  final E den;

		/** @throws IllegalArgumentException If den is zero in r. */
		public Element(E num, E den) throws IllegalArgumentException
		{
			this.num = num;
			this.den = den;
			
			if (d.eq(den, d.zero()))
				throw new IllegalArgumentException("Division by zero");
		}

		/** A string representation of this suitable for display. */
		public String toString() {
			if (d.eq(den, d.one()))
				return num.toString();
			else
				return "(" + num.toString() + " / " + den.toString() + ")";
		}
	}

	@MagicCheck
	public final class NaturalHom
	        implements RingHom<E, D, Element, FieldOfFractions<E,D>>
	{
		public D domain()                       { return d; }
		public FieldOfFractions<E,D> codomain() { return FieldOfFractions.this; }
		public Element ap(E e) { return new Element(e, d.one()); }
	}

	public NaturalHom INJ = new NaturalHom();
	
	protected FieldOfFractions(D d) {
		this.d = d;
	}

	@Override
	public Element times(Element a, Element b) {
		return new Element ( d.times(a.num, b.num)
				           , d.times(a.den, b.den)
				           );
	}

	@Override
	public Element one() {
		return new Element (d.one(), d.one());
	}

	@Override
	public Element plus(Element a, Element b) {
		return new Element ( d.plus  ( d.times(a.num, b.den)
		                             , d.times(b.num, a.den)
		                             )
		                   , d.times ( a.den, b.den )
		                   );
	}

	@Override
	public Element neg(Element a) {
		return new Element(d.neg(a.num), a.den);
	}

	@Override
	public Element zero() {
		return new Element(d.zero(), d.one());
	}

	@Override
	public Boolean eq(Element a, Element b) {
		return d.eq(d.times(a.num, b.den), d.times(b.num, a.den));
	}

	@Override
	public Element inv(Element a) {
		if (d.eq(a.num, d.zero()))
			throw new IllegalArgumentException();
		
		return new Element(a.den, a.num);
	}
}

/*
** vim: ts=4 sw=4 cindent cino=\:0
*/
