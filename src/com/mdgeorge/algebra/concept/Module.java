package com.mdgeorge.algebra.concept;

public interface Module<E, S, R extends Ring<S>> extends Group<E> {
	E smult (S s, E a);
}
