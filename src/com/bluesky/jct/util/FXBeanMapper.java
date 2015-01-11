package com.bluesky.jct.util;

import java.lang.reflect.Method;

public class FXBeanMapper {

	/**
	 * Map pojo properties to FX bean specification bean. 
	 * 
	 * @param dest FX type of bean.
	 * @param orig Java bean spec format.
	 */
	public static void copyProperties(Object dest, Object orig) {

		try {
			Class origClass = Class.forName(orig.getClass().getName());
			Method[] origMethods = origClass.getDeclaredMethods();
			Class destClass = Class.forName(dest.getClass().getName());

			for (Method method : origMethods) {
				if (method.getName().startsWith("get")) {
					Object returnedType = method.getReturnType();
					// check if target method exists
					if(methodExists(dest,String.format("%s%s", "set", method.getName().substring(3)),method.getReturnType())) {
					
						Method destMethod = destClass.getDeclaredMethod(String.format("%s%s", "set", method.getName().substring(3)), method.getReturnType());
						// check if source value is not null
						if (method.invoke(orig, new Object[0])!=null) {
							destMethod.invoke(dest, method.invoke(orig, new Object[0]));
						}

					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static boolean methodExists(Object obj, String name, Class parameterType) {
		try {
			obj.getClass().getDeclaredMethod(name, parameterType);
		} catch (NoSuchMethodException e) {
			return false;
		} catch (SecurityException e) {
			return false;
		}
		return true;
	}
}