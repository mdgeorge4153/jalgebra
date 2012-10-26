package com.mdgeorge.algebra.concept;

public interface Algebra<E, S, R extends Ring<S>>
         extends Module<E, S, R>, Ring<E>
{

}
