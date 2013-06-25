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

Source Code
===========

The source code for library is contained in the `src/` directory; the
`processor/` directory contains the annotation processor mentioned above.

The `com.mdgeorge.geometry` package contains geometric constructions that make
use of the `com.mdgeorge.algebra` classes.  They can serve as an example of how
to use the library.  The `algebra` package contains the meat of the library:

 - `com.mdgeorge.concept` contains interfaces defining the algebraic concepts:
   `Group`, `Ring`, `Module`, etc.
 - `com.mdgeorge.properties` contains annotation classes that define properties of
   methods, such as `@Commutative`, `@Associative`, etc.
 - `com.mdgeorge.construction` contains number types that instantiate the algebraic
   concepts.  Some, such as `Z` (the integers), `Q` (the rationals) and `Float`
   are used to handle Java's built in numbers.  Others (such as
   `FieldOfFractions`) can be parameterized by another number type.
 - `com.mdgeorge.adapter` contains utility classes that wrap number types and
   add useful methods.  For example, the `OrderedField` interface provides an
   inverse method, but not a division method.  `OrderedFieldUtils` wraps an
   `OrderedField` and adds a division method.  It also adapts the
   `OrderedField` to the `java.util.Comparator` interface, since the two types
   are equivalent but defined differently.

Building
========

Just run ant in the top directory.

