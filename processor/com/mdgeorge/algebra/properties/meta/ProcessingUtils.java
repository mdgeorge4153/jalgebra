package com.mdgeorge.algebra.properties.meta;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

/**
 * Convenience class for working with javax.lang.model stuff.  Each instance
 * corresponds to a single ProcessingEnvironment and provides easy access to 
 * useful features.
 *
 * @author mdgeorge
 */
public class ProcessingUtils {

	/** The type utilities object. */
	public final Types                 tu;
	
	/** The element utilities object. */
	public final Elements              eu;
	
	/** The processing environment. */
	public final ProcessingEnvironment env;

	/** Create a new Utils corresponding to the given ProcessingEnvironment. */
	public ProcessingUtils(ProcessingEnvironment env) {
		this.tu  = env.getTypeUtils();
		this.eu  = env.getElementUtils();
		this.env = env;
	}

	////////////////////////////////////////////////////////////////////////////
	// traversal ///////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Find all of the annotations on any method that the given method overrides.
	 */
	public Set<AnnotationMirror> findAllAnnotations(ExecutableElement method)
	{
		return findAllAnnotations(method, (TypeElement) method.getEnclosingElement());
	}
	
	
	/**
	 * Find all annotations of a given type on any method that the given method
	 * overrides.
	 */
	public Set<AnnotationMirror> findAllAnnotationsOf(ExecutableElement method, TypeElement type)
	{
		Set<AnnotationMirror> result = new HashSet<AnnotationMirror> ();
		for (AnnotationMirror a : findAllAnnotations(method))
			if (a.getAnnotationType().asElement().equals(type))
				result.add(a);
		return result;
	}
	
	
	/**
	 * Find all annotations of a given type on any method that the given method
	 * overrides.
	 */
	public Set<AnnotationMirror> findAllAnnotationsOf(ExecutableElement method, Class<? extends Annotation> type)
	{
		return findAllAnnotationsOf(method, eu.getTypeElement(type.getCanonicalName()));
	}

	/**
	 * Recursive helper method for findAllAnnotations.  Returns all annotations
	 * on methods from container or any supertype of container that are
	 * overridden by the given method. 
	 */
	private Set<AnnotationMirror> findAllAnnotations(ExecutableElement method, TypeElement container)
	{
		final Set<AnnotationMirror> result = new HashSet<AnnotationMirror>();

		// recursively find annotations in container's parents
		if (container.getSuperclass().getKind().equals(TypeKind.DECLARED))
			result.addAll(findAllAnnotations(method, (TypeElement) tu.asElement(container.getSuperclass())));
		
		for (TypeMirror sup : container.getInterfaces())
			result.addAll(findAllAnnotations(method, (TypeElement) tu.asElement(sup)));
		
		// find annotations in container
		for (ExecutableElement containedMethod : ElementFilter.methodsIn(container.getEnclosedElements()))
			if (  eu.overrides(method, containedMethod, (TypeElement) method.getEnclosingElement())
			   || containedMethod.equals(method)
			   )
				result.addAll(containedMethod.getAnnotationMirrors());

		return result;
	}
	
	/**
	 * Find all elements of the given container with the given simple name.
	 */
	public List<Element> findBySimpleName(TypeElement container, String name)
	{
		final List<Element> result = new ArrayList<Element> ();
		
		for (Element child : eu.getAllMembers(container))
			if (child.getSimpleName().contentEquals(name))
				result.add(child);
		
		return result;
	}
	
	/**
	 * Determine whether the given method returns a String
	 */
	public boolean returnsString(ExecutableElement method) {
		TypeElement stringElem = eu.getTypeElement("java.lang.String");
		TypeMirror  stringType = stringElem.asType();
		return method.getReturnType().equals(stringType);
	}

	////////////////////////////////////////////////////////////////////////////
	// logging /////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Log a note to the messager.
	 */
	@SuppressWarnings("unused")
	public void note(String message) {
		env.getMessager().printMessage(Kind.NOTE, message);
	}

	/**
	 * Log an error to the messager.
	 */
	@SuppressWarnings("unused")
	public void error(String message, Element location) {
		env.getMessager().printMessage(Kind.ERROR, message, location);
	}
	
	/**
	 * Log a warning to the messager.
	 */
	@SuppressWarnings("unused")
	public void warning(String message, Element location) {
		env.getMessager().printMessage(Kind.WARNING, message, location);
	}


	/**
	 *  Display the name of m in a reasonable format for informational output.
	 */
	public String methodName(ExecutableElement m) {
		return m.getEnclosingElement().getSimpleName()
		     + "."
		     + m.getSimpleName();
	}
}
