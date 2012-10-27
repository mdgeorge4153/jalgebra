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
                          , RE, R extends Group<RE>
                          >
         extends SetHom<DE, D, RE, R>
{
	@Override @CommutesWith("Group.plus")
	RE ap (DE e);
}
