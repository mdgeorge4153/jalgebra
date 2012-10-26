Overview
========

This is a java library for using concepts and constructions from abstract
algebra.  It is focused on providing code that closely matches the way an
algebraist would define things, and providing a framework for checking the
implementations for correctness.

For example, a mathematical (abelian) group is a set coupled with an operation
plus that is commutative, associative, has an identity element and an inverse.
This is exactly how to read the code for `algebra.concept.Group`:

	interface Group<E> extends Set<E> {
		@Commutative @Associative @Identity("zero") @Inverse("neg")
		E plus (E, E);

		E zero ();

		E neg (E e);
	}


The key feature here are the annotations `@Commutative`, `@Associative`, etc.
They are each given executable definitions that can be tested.  For example, a
binary function f from E x E to R is commutative if f(a,b) equals f(b,a).  This
is captured by the code for `@Commutative` (in algebra.properties):

	@interface Commutative {

		...
		boolean check(Binary<E,E,R> f, Binary<R,R,Boolean> eq, E a, E b)
		{
			return eq.ap(f.ap(a,b), f.ap(b,a));
		}
	}


This framework is supported by a custom annotation processor (in the processor/
directory).  This processor ensures that methods annotated with these
properties are correctly typed (for example, `@Commutative E zero()` is nonsense
because zero is not a binary relation), and also generates randomized tests
that invoke the property definitions.

Building
========

Just run ant in the top directory.

