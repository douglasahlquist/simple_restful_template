package com.ahlquist.document.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class EntityToJsonUtil<T> {

	final static Logger logger = Logger.getLogger(EntityToJsonUtil.class);

	public EntityToJsonUtil() {
	}

	public JSONObject toJson(T obj) {
		JSONObject json = new JSONObject();

		HashMap<String, String> fieldNames = new HashMap<>();
		HashMap<String, String> fieldType = new HashMap<>();

		// This is an ugly hack to get around not being able to pass
		// in T obj and calling T.getClass().getDeclaredFields()
		List<T> list = new ArrayList<T>();
		list.add(obj);
		Class<? extends Object> examinedClass = list.get(0).getClass();
		Field[] fieldsOnTheObject = examinedClass.getDeclaredFields();

		for (Field field : fieldsOnTheObject) {
			String nameLower = field.getName().toLowerCase();
			fieldNames.put(nameLower, field.getName());
			fieldType.put(nameLower, field.getType().toString());
		}

		try {
			Method[] methods = examinedClass.getDeclaredMethods();
			for (Method method : methods) {
				String methodName = method.getName();

				final String fieldFromMethodNameOrig = methodName.substring("get".length());
				final String fieldFromMethodName = fieldFromMethodNameOrig.substring(0, 1).toLowerCase()
						+ fieldFromMethodNameOrig.substring(1);

				// logger.debug("fieldFromMethodName: : " + fieldFromMethodName + " from: " +
				// methodName);

				if (methodName.startsWith("get") && !"getId".equals(methodName)
						&& (methodName.length() > "get".length())) {
					// add logic to bypass 'id' fields

					Object invokeResult = method.invoke(list.get(0));
					// logger.debug(">>>>>>>>>>> methodName: " + methodName + " invoke result: " +
					// invokeResult);
					if (invokeResult != null) {

						String type = fieldType.get(fieldFromMethodName.toLowerCase());
						if ("class java.lang.String".equals(type)) {
							json.put(fieldFromMethodName, invokeResult.toString());
						} else if ("int".equals(type)) {
							json.put(fieldFromMethodName, (int) (invokeResult));
						} else if ("long".equals(type)) {
							json.put(fieldFromMethodName, (long) (invokeResult));
						} else if ("boolean".equals(type)) {
							json.put(fieldFromMethodName, (boolean) (invokeResult));
						} else if ("byte".equals(type)) {
							json.put(fieldFromMethodName, (byte) (invokeResult));
						} else if ("char".equals(type)) {
							json.put(fieldFromMethodName, (char) (invokeResult));
						} else if ("double".equals(type)) {
							json.put(fieldFromMethodName, (double) (invokeResult));
						} else if ("float".equals(type)) {
							json.put(fieldFromMethodName, (float) (invokeResult));
						} else if ("short".equals(type)) {
							json.put(fieldFromMethodName, (short) (invokeResult));
						}
					}
				}
			}
		} catch (IllegalAccessException | InvocationTargetException | JSONException e) {
			e.printStackTrace();
			logger.error(e);
		}

		return json;
	}

	/**
	 * Converts T into a String of attribute:value pairs
	 * 
	 * @param obj
	 * @return
	 */
	public String toString(T obj) {
		return toJson(obj).toString();
	}
}