import com.mdgeorge.algebra.concept.Group;
import com.mdgeorge.algebra.construction.Q;
import com.mdgeorge.algebra.construction.Z;
import com.mdgeorge.algebra.properties.CommutesWith;
import com.mdgeorge.util.OpBinary;
import com.mdgeorge.util.OpNullary;
import com.mdgeorge.util.OpTernary;
import com.mdgeorge.util.OpUnary;


public class Plus <E, G extends Group<E>> implements OpTernary<G, E, E, E> {

	@Override
	public E ap(G a, E b, E c) {
		return a.plus(b,c);
	}

	void foo() {
		Q q = Q.instance;
		
		OpUnary<Integer,Q.Element> f = q.new NaturalHom();
		
		OpTernary<Z, Integer, Integer, Integer>
			domPlus = new Plus<Integer, Z>();
		
		OpTernary<Q, Q.Element, Q.Element, Q.Element>
			codPlus = new Plus<Q.Element, Q>();
		
		OpBinary<Q.Element, Q.Element, Boolean> eq = null;
		OpNullary<Z> domain   = null;
		OpNullary<Q> codomain = null;
		
		CommutesWith.Definition.check(f, domPlus, codPlus, eq, domain, codomain, null, null);
	}
}
