package com.mdgeorge.algebra.adapters;

import com.mdgeorge.algebra.concept.OrderedRing;

/**
 * Utility class for handling ordered field operations.
 * @author mdgeorge
 */
public class OrderedRingUtils<E>
     extends TotalOrderUtils<E>
  implements OrderedRing<E>
{
	protected final OrderedRing<E> impl;
	
	public OrderedRingUtils(OrderedRing<E> impl) {
		/**
		 * This is a little tricky: the super class only uses it's impl in the
		 * leq method, which we override.  We can't pass impl because OR doens't
		 * implement TO (which is sort of the point of this class). 
		 */
		super(null);

		this.impl    = impl;
	}

	public E minus(E a, E b) {
		return plus(a, neg(b));
	}

	public boolean divEq ( E n1, E d1
	                     , E n2, E d2
	                     )
	{
		return eq(times(n1, d2), times(n2, d1));
	}

	@Override
	public Boolean leq(E a, E b) {
		return isNonnegative(plus(b, neg(a)));
	}

	/*
	** pass through ************************************************************
	*/

	@Override
	public Boolean isNonnegative(E e) {
		return impl.isNonnegative(e);
	}

	@Override
	public E times(E a, E b) {
		return impl.times(a, b);
	}

	@Override
	public E one() {
		return impl.one();
	}

	@Override
	public E plus(E a, E b) {
		return impl.plus(a, b);
	}

	@Override
	public E neg(E a) {
		return impl.neg(a);
	}

	@Override
	public E zero() {
		return impl.zero();
	}
}
