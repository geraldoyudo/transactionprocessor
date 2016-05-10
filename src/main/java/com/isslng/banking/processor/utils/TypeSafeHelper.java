package com.isslng.banking.processor.utils;

public class TypeSafeHelper {
	public static <T> T convert(Object obj, Class<T> clazz){
		return clazz.cast(obj);
	}
}
