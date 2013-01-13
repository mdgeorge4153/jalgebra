package com.mdgeorge.geometry;

public interface Subcurve<P,XMC> {
	public <R, E extends Exception>
	R accept(SubcurveVisitor<R,P,XMC,E> visitor)
	throws E;
	
	
	public interface SubcurveVisitor<R, P, XMC, E extends Exception>
	{
		R visitPoint(Point<P,XMC> p) throws E;
		R visitCurve(Curve<P,XMC> p) throws E;
	}
	
	public final class Point<P,XMC>
	        implements Subcurve<P,XMC>
	{
		public final P   point;
		public final int multiplicity;
		
		public Point(P point, int multiplicity) {
			this.point        = point;
			this.multiplicity = multiplicity;
		}
		
		public <R, E extends Exception>
		R accept(SubcurveVisitor<R,P,XMC,E> visitor) throws E {
			return visitor.visitPoint(this);
		}
	}
	
	public final class Curve<P,XMC>
	        implements Subcurve<P,XMC>
	{
		public final XMC curve;
		
		public Curve(XMC curve) {
			this.curve = curve;
		}
		
		public <R, E extends Exception>
		R accept(SubcurveVisitor<R,P,XMC,E> visitor) throws E {
			return visitor.visitCurve(this);
		}
	}
}