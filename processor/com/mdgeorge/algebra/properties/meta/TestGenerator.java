package com.mdgeorge.algebra.properties.meta;

import java.util.HashMap;
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
import javax.lang.model.util.ElementFilter;


@SupportedAnnotationTypes("com.mdgeorge.algebra.properties.meta.MagicCheck")
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
					checkMethodProperty(clazz, m,a);
	}
	
	private void checkMethodProperty(TypeElement clazz, ExecutableElement method, AnnotationMirror property)
	{
		String propertyName = "@" + property.getAnnotationType().asElement().getSimpleName(); 
		
		// util.note( util.methodName(method) + ": " + propertyName);
		
		//
		// find all of the annotation value methods
		//

		// if property has a method K, then  
		Map<ExecutableElement, ExecutableElement> methodArgs = new HashMap<ExecutableElement, ExecutableElement> ();
		
		methodArgs.put(null, method);
		
		for ( Map.Entry<? extends ExecutableElement,? extends AnnotationValue> e
		    : util.eu.getElementValuesWithDefaults(property).entrySet())
		{
			// TODO: a lot of this logic is duplicated in
			// PropertyChecker.checkAnnotationValue

			ExecutableElement argDecl  = e.getKey();
			String            argValue = e.getValue().getValue().toString();
			
			if (!util.returnsString(argDecl))
			{
				util.warning( "The MagicProperty " + propertyName + " " +
				              "is malformed " +
				              "(" + argDecl.getSimpleName() + "is not a String)."
				            , method
				            );
				continue;
			}

			boolean isMethodName = !util.findAllAnnotationsOf(argDecl, MethodName.class).isEmpty();
			boolean isMethodRef  = !util.findAllAnnotationsOf(argDecl, MethodRef.class).isEmpty();

			if (!(isMethodName ^ isMethodRef))
			{
				util.warning ( "The MagicProperty " + propertyName + " " +
				               "is malformed " +
				               "(" + argValue + " not annotated properly)."
				             , method
				             );
				continue;
			}

			//
			// look up the method
			//
			
			String methodName;
			
			if (isMethodRef)
			{
				//
				// get the referred-to annotation type
				//
				
				TypeElement refType = util.eu.getTypeElement(argValue);
				
				if (refType == null)
				{
					util.error( "The MagicProperty " + propertyName + " " +
					            "refers to the non-existent property "    +
					            argValue + "."
					          , method
					          );
					continue;
				}
				
				//
				// Make sure the referred-to annotation has a "value" field
				//
				List<ExecutableElement> values = ElementFilter.methodsIn(util.findBySimpleName(refType, "value"));
				
				if (values.isEmpty())
				{
					util.error ( "The property @" + argValue + " " +
					             "referred to by " + propertyName + " " +
					             "must have a 'value' member."
					           , method
					           );
					continue;
				}
				if (values.size() > 1)
				{
					util.error ( "The property @" + argValue + " has too many " +
					             "'value' members."
					           , method
					           );
					continue;
				}
				
				ExecutableElement value = values.get(0);

				if (!util.returnsString(value))
				{
					util.error ( "The @" + argValue + " annotation " +
					             "referred to by " + propertyName + " " +
					             "must hava a String as it's 'value' parameter"
					           , method
					           );
				}
				
				//
				// Get the referred-to annotation from the method. 
				//
				
				Set<AnnotationMirror> annots = util.findAllAnnotationsOf(method, refType);
				
				if (annots.isEmpty())
				{
					util.error( "The MagicProperty " + propertyName + " " +
					            "requires the method to also have an " +
					            "@" + refType.getSimpleName() + " annotation."
					          , method
					          );
					continue;
				}
				
				if (annots.size() > 1)
				{
					util.error( "The MagicProperty " + propertyName + " " +
				                "refers to the multiply-defined " +
				                "@" + refType.getSimpleName() + " annotation."
				              , method
				              );
					continue;
				}
				
				AnnotationMirror refVal = annots.iterator().next(); 
				
				//
				// Get the "value" field of the referred-to annotation.
				//
				
				methodName = refVal.getElementValues().get(value).getValue().toString();
			}
			else
			{
				//
				// Life is much easier - we are given the method name directly
				//
				methodName = argValue;
			}
			
			List<ExecutableElement> methods = ElementFilter.methodsIn(util.findBySimpleName(clazz, methodName));
			
			if (methods.isEmpty())
			{
				util.error ( "The MagicProperty " + propertyName + " " +
				             "requires a method named " + methodName + " " +
				             "to be defined in the same class"
				           , method
				           );
				continue;
			}
			if (methods.size() > 1)
			{
				util.error ( "The MagicProperty " + propertyName + " " +
				             "refers to the method " + methodName + ", " +
				             "which is multiply defined."
				           , method
				           );
				continue;
			}
			
			methodArgs.put(argDecl, methods.get(0));
		}
		
		for (Map.Entry<ExecutableElement, ExecutableElement> e : methodArgs.entrySet())
		{
			String note = "\t";
			note += e.getKey() == null ? "this" : e.getKey().getSimpleName();
			note += " --> ";
			note += e.getValue() + " : " + e.getValue().getReturnType();
			// util.note ( note );
		}
	}
	
	
	/**
	 * Convenience method to determine if a given annotation is a @MagicProperty.
	 */
	private boolean isMagic(AnnotationMirror a){
		return a.getAnnotationType().asElement()
				.getAnnotation(MagicProperty.class) != null;
	}
	
}
