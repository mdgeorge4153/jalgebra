package com.mdgeorge.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import com.mdgeorge.geometry.util.AbstractForwardCirculator;

public class Arrangement {

	/*
	 ** Face type **************************************************************
	 */
	
	static class Face {
		final List<FaceBoundary> boundaries;
		
		Face() {
			this.boundaries = new ArrayList<FaceBoundary> ();
		}
	}

	interface FaceBoundary {
		public abstract <R, E extends Exception>
		R accept (FaceBoundaryVisitor<E,R> visitor)
		throws E;
	}
	
	interface FaceBoundaryVisitor<E extends Exception,R> {
		R visitHalfedge(Halfedge       he) throws E;
		R visitIsolated(IsolatedVertex iv) throws E;
	}
	
	/*
	 ** Vertex type ************************************************************
	 */
	
	static class Vertex {
		Halfedge leaving;
	}

	static class IsolatedVertex
	  implements FaceBoundary
	{
		Face face;
		
		public <R, E extends Exception>
		R accept (FaceBoundaryVisitor<E,R> visitor)
		throws E
		{
			return visitor.visitIsolated(this);
		}
	}

	/*
	 ** Halfedge type **********************************************************
	 */
	
	static class Halfedge implements FaceBoundary {
		Vertex   origin;
		Halfedge next;
		Halfedge twin;
		Face     face;
		
		public <R, E extends Exception>
		R accept (FaceBoundaryVisitor<E,R> visitor)
		throws E
		{
			return visitor.visitHalfedge(this);
		}
	}
	

	/*
	 ** Arrangement ************************************************************
	 */
	
	Collection<Face>     faces;
	Collection<Vertex>   vertices;
	Collection<Halfedge> halfedges;
	
	Arrangement() {
		this.faces     = new ArrayList<Face>     ();
		this.vertices  = new ArrayList<Vertex>   ();
		this.halfedges = new ArrayList<Halfedge> ();
		
		this.faces.add(new Face());
	}
	
	/*
	 ** Traversals *************************************************************
	 */
	
	public class HalfedgesLeavingVertex
	     extends AbstractForwardCirculator<Halfedge>
	{
		Halfedge current;
		
		public HalfedgesLeavingVertex(Vertex source) throws NoSuchElementException
		{
			super(source.leaving);
		}
		
		@Override
		public void inc() {
			current = current.twin.next;
		}
	}
	
	public class HalfedgesEnteringVertex
	     extends AbstractForwardCirculator<Halfedge>
	{
		public HalfedgesEnteringVertex(Vertex target) throws NoSuchElementException
		{
			super(target.leaving);
		}
		
		@Override
		public void inc() {
			this.current = current.next.twin;
		}
	}
	
	public class HalfedgesAroundFace
	     extends AbstractForwardCirculator<Halfedge>
	{
		protected HalfedgesAroundFace(Halfedge first) throws NoSuchElementException
		{
			super(first);
		}

		@Override
		public void inc() {
			this.current = current.next; 
		}
	}
}
