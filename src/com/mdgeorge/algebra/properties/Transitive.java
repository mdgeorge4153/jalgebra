package com.mdgeorge.algebra.properties;

import java.lang.annotation.*;

import com.mdgeorge.algebra.properties.meta.MagicProperty;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@MagicProperty
public @interface Transitive {
}
