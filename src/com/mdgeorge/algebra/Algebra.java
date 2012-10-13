package com.mdgeorge.algebra;

public interface Algebra<E, S, R extends Ring<S>>
         extends Module<E, S, R>, Ring<E>
{

}
