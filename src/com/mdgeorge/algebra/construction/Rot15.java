package com.mdgeorge.algebra.construction;

import com.mdgeorge.algebra.concept.IntegralDomain;
import com.mdgeorge.algebra.concept.Ring;
import com.mdgeorge.algebra.properties.meta.OpUnary;

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

	/*
	** Z[sqrt2, sqrt3] *********************************************************
	**/
	
	public final static class SignatureRoot6<E, R extends Ring<E>>
		implements Extension.Signature<E>
	{
		private final R r;
		
		public SignatureRoot6(R r) {
			this.r = r;
		}

		@SuppressWarnings("unchecked")
		public final E[][][] coefficients() {
			OpUnary<Integer,E> inj = Z.into(r);
			E e0 = inj.ap(0);
			E e1 = inj.ap(1);
			E e2 = inj.ap(2);
			E e3 = inj.ap(3);
			E e6 = inj.ap(6);
			E e9 = inj.ap(9);

			return (E[][][]) new Object[][][]
				{
					{ { e1 , e0 , e0 , e0 } // √1 * √1
					, { e0 , e1 , e0 , e0 } // √1 * √2
					, { e0 , e0 , e1 , e0 } // √1 * √3
					, { e0 , e0 , e0 , e1 } // √1 * √6
					},

					{ { e0 , e1 , e0 , e0 } // √2 * √1 
					, { e2 , e0 , e0 , e0 } // √2 * √2
					, { e0 , e0 , e0 , e1 } // √2 * √3
					, { e0 , e0 , e2 , e0 } // √2 * √6
					},

					{ { e0 , e0 , e1 , e0 } // √3 * √1
					, { e0 , e0 , e0 , e1 } // √3 * √2
					, { e3 , e0 , e0 , e0 } // √3 * √3
					, { e0 , e3 , e0 , e0 } // √3 * √6
					},

					{ { e0 , e0 , e0 , e1 } // √6 * √1
					, { e0 , e0 , e2 , e0 } // √6 * √2
					, { e0 , e9 , e0 , e0 } // √6 * √3
					, { e6 , e0 , e0 , e0 } // √6 * √6
					},
				};
		}
		
		@Override
		public int dimension() {
			return 4;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E[] one() {
			return (E[]) new Object[] {r.one(), r.zero(), r.zero(), r.zero()};
		}

		@Override
		public String generatorName(int i) throws IllegalArgumentException {
			switch(i) {
			case 0: return null;
			case 1: return "√2";
			case 2: return "√3";
			case 3: return "√6";
			default: throw new IllegalArgumentException();
			}
		}
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

	/*
	** Field of fractions of Root6 *********************************************
	*/
	
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
	
	/*
	** Cos15[i] ****************************************************************
	*/

	public final static class SignatureComplex<E, R extends Ring<E>>
	        implements Extension.Signature<E>
	{
		private final R r;

		public SignatureComplex(R r) {
			this.r = r;
		}

		@SuppressWarnings("unchecked")
		public final E[][][] coefficients() {
			E e0  = r.zero();
			E e1  = r.one();
			E n1  = r.neg(e1);

			return (E[][][]) new Object[][][]
				{
					{ { e1 , e0 } // 1 * 1
					, { e0 , e1 } // 1 * i
					},

					{ { e0 , e1 } // i * 1 
					, { n1 , e0 } // i * i
					},
				};
		}
		
		@Override
		public int dimension() {
			return 2;
		}

		@SuppressWarnings("unchecked")
		@Override
		public E[] one() {
			return (E[]) new Object[] {r.one(), r.zero()};
		}

		@Override
		public String generatorName(int i) throws IllegalArgumentException {
			switch(i) {
			case 0: return null;
			case 1: return "i";
			default: throw new IllegalArgumentException();
			}
		}
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
