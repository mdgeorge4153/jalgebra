package com.mdgeorge.algebra.adapters;

import com.mdgeorge.algebra.concept.OrderedField;

public class OrderedFieldUtils<E>
     extends OrderedRingUtils<E>
  implements OrderedField<E>
{
	private final OrderedField<E> impl;
	
	public OrderedFieldUtils(OrderedField<E> impl) {
		super(impl);
		this.impl = impl;
	}

	public E div (E a, E b) {
		return times(a, inv(b));
	}

	/*
	** pass through ************************************************************
	*/
	
	public E inv (E a) {
		return impl.inv(a);
	}
}
