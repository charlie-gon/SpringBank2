package com.company.common;

import java.util.HashMap;
import java.util.Map;

import com.company.board.controller.JsonUtil;
import com.company.board.service.BoardVO;

public class JsonUtilTest {
	public static void main(String[] args) {
		
		// 1
		JsonUtil json = new JsonUtil();
		Map<String, Object> map = new HashMap<>();
		map.put("username", "Kim");
		map.put("age", "10");
		map.put("dept", "IT");
		String result = json.toJson(map);
		System.out.println("toJson = "+result);
		// {"username":"Kim", "age":10,"dept":"IT"}
		
		// 3
		BoardVO vo = new BoardVO();
		vo.setContent("test");
		vo.setTitle("title");
		vo.setFilename("filename");
		//result = json.toObjectJson(vo);
		System.out.println("toObjectJson = "+ result);
	}

}
