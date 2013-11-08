package com.codepoetics.magicbeans.introspection;

import java.util.Map;

import com.google.common.base.Function;

public interface BeanWriter<T> extends Function<Map<String, Object>, T> { }
