package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.IntegralDomain;

public final class Rot15
           extends FieldOfFractions<Rot15.Cos15e.Element, Rot15.Cos15e>
{
	public static void main(String[] args)
	{
		System.out.println("Cos 30: " + Cos15.instance.COS30);
		System.out.println("Cos 45: " + Cos15.instance.COS45);
		System.out.println("Rot 15: " + instance.ROT15);
	}
	
	public final static Rot15 instance = new Rot15();

	private Cos15e c15e = Cos15e.instance;
	public final Rot15.Element ROT45 = INJ.ap(c15e.ROT45);
	public final Rot15.Element ROT30 = INJ.ap(c15e.ROT30);
	public final Rot15.Element ROT15 = times(ROT45, inv(ROT30));
	
	private Rot15() {
		super(Cos15e.instance);
	}

	public static final class Root6
                          extends Extension < Integer
                                            , Z
                                            , SignatureRoot6<Integer, Z>
                                            >
	               implements IntegralDomain < Root6.Element > 
	{
		public static final Root6 instance = new Root6();
		private Root6() {
			super(new SignatureRoot6<Integer, Z>(Z.instance), Z.instance);
		}

		public final Root6.Element SQRT1 = new Element(1,0,0,0);
		public final Root6.Element SQRT2 = new Element(0,1,0,0);
		public final Root6.Element SQRT3 = new Element(0,0,1,0);
		public final Root6.Element SQRT6 = new Element(0,0,0,1);
	}
	
	
	public static final class Cos15
	                  extends FieldOfFractions<Root6.Element, Root6>
	{
		public final static Cos15 instance = new Cos15();
		private Cos15() { super(Root6.instance); }

		private final Root6 r6 = Root6.instance;
		public  final Cos15.Element HALF  = inv(INJ.ap(r6.INJ.ap(2)));
		public  final Cos15.Element COS45 = times(INJ.ap(r6.SQRT2), HALF);
		public  final Cos15.Element SIN45 = times(INJ.ap(r6.SQRT2), HALF);
		public  final Cos15.Element COS30 = times(INJ.ap(r6.SQRT3), HALF);
		public  final Cos15.Element SIN30 = times(INJ.ap(r6.SQRT1), HALF);
		
	}
	
	public static final class Cos15e
	                  extends Extension < Cos15.Element
	                                    , Cos15
	                                    , SignatureComplex<Cos15.Element, Cos15>
	                                    >
	               implements IntegralDomain <Cos15e.Element>
	{
		public  final static Cos15e instance = new Cos15e();
		private Cos15e() {
			super(new SignatureComplex<Cos15.Element, Cos15>(Cos15.instance), Cos15.instance);
		}

		private final Cos15 c15 = Cos15.instance;
		public  final Cos15e.Element ROT45 = new Element(c15.COS45, c15.SIN45);
		public  final Cos15e.Element ROT30 = new Element(c15.COS30, c15.SIN30);
	}
}
