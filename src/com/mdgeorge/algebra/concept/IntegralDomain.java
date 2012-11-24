package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.NoZeroDividers;

/**
 * An integral domain is a (commutative) ring with no zero divisors.  An element
 * a of a ring R is a zero divisor if there is some b ≠ 0 in R such that ab = 0.  
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Integral_domain">Integral domain on Wikipedia</a>
 * @author mdgeorge
 */
public interface IntegralDomain <E>
         extends Ring           <E>
{
	@Override
	@NoZeroDividers
	E times(E a, E b);
}
