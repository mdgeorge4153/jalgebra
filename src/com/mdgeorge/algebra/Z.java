package com.mdgeorge.algebra;

public class Z implements IntegralDomain<Integer> {

	public final static Z instance = new Z();
	
	private Z() { }
	
	@Override
	public Integer one() {
		return 1;
	}

	@Override
	public Integer plus(Integer a, Integer b) {
		return a + b;
	}

	@Override
	public Integer neg(Integer a) {
		return - a;
	}

	@Override
	public Integer zero() {
		return 0;
	}

	@Override
	public boolean eq(Integer a, Integer b) {
		return a.equals(b);
	}

	@Override
	public Integer times(Integer a, Integer b) {
		return a * b;
	}

}
