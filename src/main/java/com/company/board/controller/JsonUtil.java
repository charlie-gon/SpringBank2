package com.company.board.controller;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtil {
	
	public String toJson(Map<String, Object> map) {
		StringBuilder result = new StringBuilder();
		result.append("{");
		Iterator<String> keys = map.keySet().iterator();
		boolean start = true;
		while(keys.hasNext()) { // 값 존재 여부 확인
			String key = keys.next();
			String value = (String) map.get(key);
			// 마지막 값에는 comma 삽입 X
			if(!start) {
				result.append(",");
			}else {
				start = false;
			}
			result.append("\"");
			result.append(key);
			result.append("\"");
			result.append(":");
			result.append("\"");
			result.append(value);
			result.append("\"");
			
		}
		result.append("}");
		return result.toString();
	}
	
	public String toJson(List<Map<String, Object>> map) { // 
		StringBuilder result = new StringBuilder();
		result.append("[");
		result.append("{");
		
		
		result.append("}");
		result.append("]");
		
		return result.toString();
	}
	
	// reflection related(자바 리플렉션)
	/* 
	 * Java Reflection: 구체적인 클래스 타입을 알지 못해도 해당 클래스의 메소드, 타입, 변수들에 접근할 수 있도록 해주는 API
	 */
	public String toObjectJson(Object vo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String result = "";
		Field[] fields = vo.getClass().getDeclaredFields(); // 모든 VO에 있는 필드 소환
		for(Field field : fields) {
			String fieldName = field.getName();
			String method = "get"+field.getName()
							.substring(0,1).toUpperCase()
							+field.getName().substring(1);
			Method m = vo.getClass().getDeclaredMethod(method, null);
			String value = (String) m.invoke(vo, null);
		}
		return result;
	}
	
	public String toObjectJson(List<Object> vo) {
		String result = "";
		return result;
	}

}
