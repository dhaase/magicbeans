package com.codepoetics.magicbeans.introspection;

import org.pcollections.PMap;

import com.google.common.base.Function;

public interface BeanReader<T> extends Function<T, PMap<String, Object>> { }
