package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.IntegralDomain;

public class Rot15
     extends FieldOfFractions<Rot15.Cos15e.Element, Rot15.Cos15e>
{
	private Cos15e c15e;
	
	public final Rot15.Element ROT45 = inj(c15e.ROT45);
	public final Rot15.Element ROT30 = inj(c15e.ROT30);
	public final Rot15.Element ROT15 = times(ROT45, inv(ROT30));
	
	public Rot15(Cos15e c15e) {
		super(c15e);
		this.c15e = c15e;
	}

	public static class Root6
                extends Extension < Integer
                                  , Z
                                  , SignatureRoot6<Integer, Z>
                                  >
	         implements IntegralDomain < Root6.Element > 
	{
		public final Root6.Element SQRT1 = new Element(1,0,0,0);
		public final Root6.Element SQRT2 = new Element(0,1,0,0);
		public final Root6.Element SQRT3 = new Element(0,0,1,0);
		public final Root6.Element SQRT6 = new Element(0,0,0,1);
	}
	
	
	public static class Cos15
	            extends FieldOfFractions<Root6.Element, Root6>
	{
		private final Root6 r6 = new Root6();
		
		public final Cos15.Element HALF  = inv(inj(r6.inj(2)));
		public final Cos15.Element COS45 = times(inj(r6.SQRT2), HALF);
		public final Cos15.Element SIN45 = times(inj(r6.SQRT2), HALF);
		public final Cos15.Element COS30 = times(inj(r6.SQRT3), HALF);
		public final Cos15.Element SIN30 = times(inj(r6.SQRT1), HALF);
		
		public Cos15(Root6 d) {
			super(d);
		}
	}
	
	public static class Cos15e
	            extends Extension < Cos15.Element
	                              , Cos15
	                              , SignatureComplex<Cos15.Element, Cos15>
	                              >
	         implements IntegralDomain <Cos15e.Element>
	{
		private final Cos15 c15 = new Cos15(new Root6());
		
		public final Cos15e.Element ROT45 = new Element(c15.COS45, c15.SIN45);
		public final Cos15e.Element ROT30 = new Element(c15.COS30, c15.SIN30);
	}
}
