package com.mdgeorge.geometry;

import java.util.List;

public class Arrangement {
	private static class Face {
		Halfedge             boundary;
		List<IsolatedVertex> interior;
	}
	
	private static class Vertex {
		Halfedge leaving;
	}
	
	private static class Halfedge       {
		Vertex   origin;
		Halfedge next;
		Halfedge twin;
		Face     face;
	}
	
	private static class IsolatedVertex {
		Face face;
	}
}
