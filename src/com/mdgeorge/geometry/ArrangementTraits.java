package com.mdgeorge.geometry;

import java.util.List;

import com.mdgeorge.algebra.concept.PartialOrder;
import com.mdgeorge.algebra.concept.Set;
import com.mdgeorge.algebra.concept.TotalOrder;
import com.mdgeorge.util.Pair;

/**
 * @author mdgeorge
 *
 * @param <P>
 * @param <XMC>
 */
public interface ArrangementTraits<P, XMC> {

	public TotalOrder<P> points();

	public Set<XMC> curves();
	
	/** points().leq(minVertex(c), maxVertex(c)) */
	public P minVertex(XMC curve);
	public P maxVertex(XMC curve);

	/**
	 * Given two curves c1 and c2 having a common left endpoint p,
	 * compareCurvesRightOf(p).leq(c1,c2) returns true if c1 lies on or below c2
	 * immediately to the right of p.
	 * 
	 * This order should be total when restricted to XMCs having p as a right
	 * endpoint. All other curves should be unrelated.
	 */
	public PartialOrder<XMC> compareCurvesRightOf(P p);

	/**
	 * return all of the intersections between the curves c1 and c2, ordered
	 * from leftmost to rightmost.  The closures of the returned components
	 * should be disjoint, so for example intersect(c,c) = [c] (and not
	 * [minVertex(c), c, maxVertex(c)] for example).
	 */
	public List<Subcurve<P,XMC>> intersect(XMC c1, XMC c2);
	
	/**
	 * Given a point p on the curve c, split c into a pair r of curves such that
	 * maxVertex(r.first) = p = minVertex(r.second). 
	 *
	 * @throws IllegalArgumentException
	 *     if p does not lie on c.
	 */
	public Pair<XMC, XMC> split(XMC c, P p) throws IllegalArgumentException;
}
