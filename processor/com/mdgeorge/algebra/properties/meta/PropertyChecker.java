package com.mdgeorge.algebra.properties.meta;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

/**
 * This class checks 
 * @author mdgeorge
 *
 */
@SupportedAnnotationTypes("com.mdgeorge.algebra.properties.meta.MagicProperty")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class PropertyChecker
     extends AbstractProcessor
{
	private ProcessingUtils util;
	
	public void init(ProcessingEnvironment pe) {
		super.init(pe);
		this.util = new ProcessingUtils(processingEnv);
	}
	
	@Override
	public boolean process ( Set<? extends TypeElement> _
	                       , RoundEnvironment           env
	                       )
	{
		for (Element e : env.getElementsAnnotatedWith(MagicProperty.class)) {
			assert e instanceof TypeElement;
			checkProperty((TypeElement) e);
		}
		
		return true;
	}

	/**
	 * This method checks whether a type is a well-formed @MagicProperty type. 
	 * A type I is well-formed if: <ol>
	 * 	<li>I is an annotation with @Target(METHOD)</li>
	 *  <li>I contains a public static inner class named <code>Definition</code></li>
	 *  <li>I.Definition contains a public static method named <code>check</code></li>
	 *  <li>I.Definition.check's arguments must be as follows:<ol>
	 *  	<li>The first argument corresponds to the annotated method</li>
	 *  	<li>The following arguments correspond to the other annotation parameters of I</li>
	 *  	<li>The remaining arguments correspond to arbitrary elements of their domains.</li>
	 *  	</ol>
	 *  </ol>  
	 * @param property
	 */
	private void checkProperty(TypeElement property)
	{
		//
		// check that magicProperty is an annotation
		//
		if (!property.getKind().equals(ElementKind.ANNOTATION_TYPE))
			util.error ( "@MagicProperties must be annotations"
			           , property
			           );
		
		//
		// check annotation values
		//
		boolean cont = true; 
		for (ExecutableElement value : ElementFilter.methodsIn(property.getEnclosedElements()))
			cont &= checkAnnotationValue(value);
		if (!cont)
			return;

		//
		// find and check Definition inner class
		//
		List<TypeElement> definitions = ElementFilter.typesIn (util.findBySimpleName(property, "Definition"));
		
		if (definitions.isEmpty())
			util.error ( "@MagicProperty annotation "  +
			             property.getSimpleName() +
			             " requires a static inner class named 'Definition'"
			           , property
			           );
		
		if (definitions.size() > 1)
			for (TypeElement e : definitions)
				util.error ( "@MagicProperty annotation "  +
				             property.getSimpleName() +
			                 " cannot have multiple Definitions"
				           , e
				           );

		for (TypeElement definition : definitions)
			checkDefinition(property, definition);
	}

	/**
	 * Check that the annotation value has the appropriate type.
	 */
	private boolean checkAnnotationValue(ExecutableElement value)
	{
		try {
			util.switchOn(value, new MethodRefVisitor()
				{
					public void caseMethodDup()  { }
					public void caseMethodExt()  { }
					public void caseMethodName() { }
					public void caseMethodRef()  { }
				});
			return true;
		} catch(BadPropertyException e) {
			util.error( e.getMessage(), value );
			return false;
		}
	}

	/**
	 * Check that the given definition is suitable for the given property.
	 */
	private void checkDefinition(TypeElement property, TypeElement definition)
	{
		//
		// ensure that we found a suitable Definition inner class
		//
		if (!definition.getModifiers().contains(Modifier.PUBLIC))
			util.error ( property.getSimpleName() +
			             ".Definition must be public"
			           , definition
			           );
		
		if (!definition.getModifiers().contains(Modifier.STATIC))
			util.error ( property.getSimpleName() + ".Definition must " +
			             "be static"
			           , definition
			           );
		
		//
		// walk over the contents of definition to find a method called "check"
		//
		List<ExecutableElement> checks = ElementFilter.methodsIn(util.findBySimpleName(definition, "check"));
		
		if (checks.isEmpty())
			util.error ( "@MagicProperty annotation " +
			             property.getSimpleName() +
			             " requires a 'check' method in its 'Definition' class."
			           , definition
			           );

		else if (checks.size() > 1)
			for (Element e : checks)
				util.error ( "@MagicProperty Definitions should only have a " +
				             "single 'check' method"
				           , e
				           );

		for (ExecutableElement check : checks)
			checkCheckMethod(property, definition, check);
	}

	
	/**
	 * Determine whether the given method is an appropriate Definition.check
	 * method for the given property. 
	 */
	private void checkCheckMethod ( TypeElement property
	                              , TypeElement definition
	                              , ExecutableElement check
	                              )
	{
		//
		// ensure we found one appropriate "check" method
		//
		if (!check.getModifiers().contains(Modifier.PUBLIC))
			util.error ( property.getSimpleName() + ".Definition.check" +
	                     " must be public"
			           , check
			           );
		
		if (!check.getModifiers().contains(Modifier.STATIC))
			util.error ( property.getSimpleName() + ".Definition.check" +
	                     " must be static"
			           , check
			           );
		
		//
		// check must return boolean
		//
		if (!check.getReturnType().getKind().equals(TypeKind.BOOLEAN))
			util.error ( "@MagicProperty 'check' function must return a boolean"
			           , check
			           );
		
		//
		// verify check parameter types
		//
		
		Iterator<? extends VariableElement> args = check.getParameters().iterator();

		//
		// first parameter should correspond to the annotated method
		//
		if (!args.hasNext())
		{
			util.error ( "@MagicProperty 'check' function must have at least " +
			             "one argument."
			           , check
			           );
			return;
		}
		
		VariableElement   methodArg     = args.next();
		ExecutableElement methodArgAp   = util.getAp(methodArg.asType());
		
		if (methodArgAp == null)
			util.error ( "The type of the first argument of Definition.check " +
			             "must have a method named 'ap'."
			           , methodArg
			           );
		
		// TODO: typecheck methodArg
		
		//
		// next parameters should correspond to the annotation values
		//
		for (Element e : ElementFilter.methodsIn(property.getEnclosedElements()))
		{
			if (!args.hasNext()) {
				util.error ( "@MagicProperty 'check' method must have an " +
				             "argument corresponding to the " +
				             e.getSimpleName() + " property."
				           , check
				           );
				return;
			}
			else {
				VariableElement   arg   = args.next();
				ExecutableElement argAp = util.getAp(arg.asType());
				if (argAp == null)
					util.error ( "The argument corresponding to the " +
					             e.getSimpleName() + " property must have an " +
					             "'ap' method."
					           , arg
					           );
			}
		}
		
		//
		// final parameters are the generated arguments to the test
		//
	}
	
}
