package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.CommutesWith;

/**
 * A group homomorphism h from a group D (the domain) to a group C (the codomain)
 * is a function that commutes with the group operation on C and D.  In other
 * words, for all a, b in D, h(a + b) = h(a) + h(b).
 *
 * @see <a href="http://en.wikipedia.org/wiki/Group_homomorphism">Group homomorphism on Wikipedia</a>
 * @author mdgeorge
 */
public interface GroupHom < DE, D extends Group<DE>
                          , CE, C extends Group<CE>
                          >
         extends SetHom<DE, D, CE, C>
{
	@Override @CommutesWith("Group.plus")
	CE ap (DE e);
}
