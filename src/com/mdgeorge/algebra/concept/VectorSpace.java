package com.mdgeorge.algebra.concept;

/**
 * A vector space is nothing more than a module over a field.  But the more
 * usual definition is that it is a set of vectors that can be added and
 * multiplied by scalars drawn from a field.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Vector_space">Vector space on Wikipedia</a>
 * @author mdgeorge
 */
public interface VectorSpace < V
                             , S
                             , F extends Field<S>
                             >
         extends Module<V, S, F>
{
}
