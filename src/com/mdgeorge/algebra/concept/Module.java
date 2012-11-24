package com.mdgeorge.algebra.concept;

/**
 * A module is a generalization of a vector space where scalars are drawn from
 * a ring instead of a field.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Module_%28mathematics%29">Module on Wikipedia</a>
 * @author mdgeorge
 */
public interface Module <E, S>
         extends Group  <E>
{
	// TODO: properties!
	E smult (S s, E a);
	
	Ring<S> scalars();
}
