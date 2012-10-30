package com.mdgeorge.algebra.properties.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

import com.mdgeorge.algebra.properties.meta.annotation.MagicCheck;
import com.mdgeorge.algebra.properties.meta.annotation.MagicProperty;


@SupportedAnnotationTypes("com.mdgeorge.algebra.properties.meta.annotation.MagicCheck")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class TestGenerator
     extends AbstractProcessor
{
	private ProcessingUtils util;
	
	@Override
	public void init(ProcessingEnvironment pe) {
		super.init(pe);
		this.util = new ProcessingUtils(processingEnv);
	}

	@Override
	public boolean process ( Set<? extends TypeElement> _
	                       , RoundEnvironment roundEnv
	                       )
	{
		util.note("TestGenerator.process running.");
		for (Element e : roundEnv.getElementsAnnotatedWith(MagicCheck.class))
		{
			assert e instanceof TypeElement;
			checkClass((TypeElement) e);
		}

		return true;
	}
	
	private void checkClass(TypeElement clazz)
	{
		for (ExecutableElement m : ElementFilter.methodsIn(util.eu.getAllMembers(clazz)))
			for (AnnotationMirror a : util.findAllAnnotations(m))
				if (isMagic(a))
					checkMethodProperty(clazz, m, a);
	}
	
	private void checkMethodProperty ( TypeElement clazz
	                                 , ExecutableElement method
	                                 , AnnotationMirror a
	                                 )
	{
		PropertyDefn propDef = getDefinition(a);
		if (propDef == null)
			//
			// definition is malformed somehow.  User will get an error, so just
			// skip checking.
			//
			return;
		

		//
		// walk through the parameters and find the corresponding methods.
		//
		
		List<MethodWrapper> methods = new ArrayList<MethodWrapper> ();
		for (MethodDefn methDef : propDef.parameters)
			methods.add(resolve(clazz, method, a, methDef));

		if (methods.contains(null))
			return;
		
		for (MethodWrapper m : methods) {
			util.note("comparing " + m.def.ap + " and " + m.impl);
		}
	}
	
	private MethodWrapper resolve ( TypeElement          clazz
	                              , ExecutableElement    method
	                              , AnnotationMirror     a
	                              , MethodDefn           def
	                              )
	{
		String annotationName = "@" + a.getAnnotationType().asElement().getSimpleName();
		
		Map<? extends ExecutableElement, ? extends AnnotationValue> params
			= util.eu.getElementValuesWithDefaults(a);
		
		String argName  = def.decl.getSimpleName().toString();
		String argValue = params.get(def.decl).getValue().toString();
		
		MethodDefn otherDef = null;
		List<ExecutableElement> methods;
		switch (def.type) {
		case PRIMARY:
			return new InternalMethod(def, method);
		case NAME:
			methods = ElementFilter.methodsIn(util.findBySimpleName(clazz, argValue));
			
			if (methods.isEmpty())
			{
				util.error ( "The MagicProperty " + annotationName + " " +
				             "requires a method named " + argValue + " " +
				             "to be defined in the same class."
				           , method
				           );
				return null;
			}
			if (methods.size() > 1)
			{
				util.error ( "The MagicProperty " + annotationName + " " +
				             "refers to the method " + argValue + ", " +
				             "which is multiply defined."
				           , method
				           );
				return null;
			}
			
			return new InternalMethod(def, methods.get(0));
		case EXT:
			//
			// Split up the argument into a class name and a method name
			//
			int lastDot       = argValue.lastIndexOf('.');
			String methodName = argValue.substring(lastDot + 1);
			String className  = argValue.substring(0, lastDot);
			
			TypeElement extClass = util.eu.getTypeElement(className);
			
			if (extClass == null)
			{
				util.error ( "The property " + annotationName + " refers to " +
				             "the non-existent class " + className + "."
				           , method
				           );
				return null;
			}
			
			methods = ElementFilter.methodsIn(util.findBySimpleName(extClass, methodName));
			
			if (methods.isEmpty())
			{
				util.error ( "The property " + annotationName + " refers to " +
				             "the non-existent method " + methodName + " of " +
				             "class " + extClass.getSimpleName() + "."
				           , method
				           );
				return null;
			}
			
			if (methods.size() > 1)
			{
				util.error ( "The property " + annotationName + " refers to " +
			                 "the multiply-defined method " + methodName + " " +
			                 "of class " + extClass.getSimpleName() + "."
			               , method
			               );
				return null;
			}

			return new ExternalMethod(def, methods.get(0));
			
		case DUP:
			//
			// Look for the param named by value
			//
			for (MethodDefn d : getDefinition(a).parameters) 
				if (d.decl.getSimpleName().contentEquals(argValue))
					otherDef = d;
			
			if (otherDef == null)
			{
				util.error ( "Property '" + argName + "' of annotation " +
				             annotationName + " refers to non-existent " +
				             "property '" + argValue + "'."
				           , method
				           );
				return null;
			}
			//
			// Look up that thing.
			//
			return resolve(clazz, method, a, otherDef);
		case REF:
			//
			// Find the annotation named by value
			//
			TypeElement refType = util.eu.getTypeElement(argValue);
			
			if (refType == null)
			{
				util.error( "The MagicProperty " + annotationName + " refers " +
				            "to non-existent class " + argValue + "."
				          , method
				          );
				return null;
			}
			
			//
			// Get the referred-to annotation from the method. 
			//
			
			Set<AnnotationMirror> annots = util.findAllAnnotationsOf(method, refType);
			
			if (annots.isEmpty())
			{
				util.error( "The MagicProperty " + annotationName + " " +
				            "requires the method to also have an " +
				            "@" + refType.getSimpleName() + " annotation."
				          , method
				          );
				return null;
			}
			
			if (annots.size() > 1)
			{
				util.error( "The MagicProperty " + annotationName + " " +
			                "refers to the multiply-defined " +
			                "@" + refType.getSimpleName() + " annotation."
			              , method
			              );
				return null;
			}
			
			AnnotationMirror otherAnnot = annots.iterator().next();
			
			if (!isMagic(otherAnnot)) {
				util.error( "The " + argName + " property of the " +
				            annotationName + " annotation can only refer to " +
				            "other magic properties."
				          , method
				          );
				return null;
			}
			
			//
			// Find the 'value' of the other definition.
			//

			PropertyDefn otherProp = getDefinition(otherAnnot);
			for (MethodDefn d : otherProp.parameters)
				if (d.decl.getSimpleName().contentEquals("value"))
					otherDef = d;
			
			if (otherDef == null)
			{
				util.error ( "The property @" +
				             refType.getSimpleName() + " " +
				             "referred to by " + annotationName + " " +
				             "must have a 'value' member."
				           , method
				           );
				return null;
			}

			return resolve(clazz, method, otherAnnot, otherDef);
		}
		return null;
	}

	private PropertyDefn getDefinition(AnnotationMirror a) {
		TypeElement  aType = (TypeElement) a.getAnnotationType().asElement();

		try {
			return new PropertyDefn(util, aType, new BadPropertyException(null));
		}
		catch (final BadPropertyException e) {
			return null;
		}
	}

	/**
	 * Convenience method to determine if a given annotation is a @MagicProperty.
	 */
	private boolean isMagic(AnnotationMirror a){
		return a.getAnnotationType().asElement()
				.getAnnotation(MagicProperty.class) != null;
	}

	private abstract class MethodWrapper {
		public final List<TypeMirror>  args;
		public final MethodDefn        def;
		public final ExecutableElement impl;
		protected MethodWrapper(MethodDefn def, ExecutableElement m) {
			this.args = new ArrayList<TypeMirror>();
			this.def  = def;
			this.impl = m;
			for (VariableElement v : m.getParameters())
				args.add(v.asType());
		}
	}
	
	private final class InternalMethod extends MethodWrapper {
		public InternalMethod(MethodDefn def, ExecutableElement m) {
			super(def, m);
		}
	}
	
	private final class ExternalMethod extends MethodWrapper {
		public ExternalMethod(MethodDefn def, ExecutableElement m) {
			super(def, m);
			this.args.add(0, m.asType());
		}
	}
}
