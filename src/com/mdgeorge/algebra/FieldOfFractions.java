package com.mdgeorge.algebra;

public class FieldOfFractions<E, D extends IntegralDomain<E>>
  implements Field<Fraction<E,E>>
{
	final D d;
	
	public FieldOfFractions(D d) {
		this.d = d;
	}

	@Override
	public Fraction<E, E> times(Fraction<E, E> a, Fraction<E, E> b) {
		return new Fraction<E,E> ( d.times(a.num, b.num)
				                 , d.times(a.den, b.den)
				                 );
	}

	@Override
	public Fraction<E, E> one() {
		return new Fraction<E,E> (d.one(), d.one());
	}

	@Override
	public Fraction<E, E> plus(Fraction<E, E> a, Fraction<E, E> b) {
		return new Fraction<E,E> ( d.plus  ( d.times(a.num, b.den)
		                                   , d.times(b.num, a.den)
		                                   )
		                         , d.times ( a.den, b.den )
		                         );
	}

	@Override
	public Fraction<E, E> neg(Fraction<E, E> a) {
		return new Fraction<E,E>(d.neg(a.num), a.den);
	}

	@Override
	public Fraction<E, E> zero() {
		return new Fraction<E,E>(d.zero(), d.one());
	}

	@Override
	public boolean eq(Fraction<E, E> a, Fraction<E, E> b) {
		return d.eq(d.times(a.num, b.den), d.times(b.num, a.den));
	}

	@Override
	public Fraction<E, E> inv(Fraction<E, E> a) {
		if (d.eq(a.num, d.zero()))
			throw new IllegalArgumentException();
		return new Fraction<E,E>(a.den, a.num);
	}
	
	
}
