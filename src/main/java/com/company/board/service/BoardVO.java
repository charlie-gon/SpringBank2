package com.company.board.service;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardVO { // 파일 업로드
	
	private int seq;
	private String title;
	private String writer;
	private String content;
	private String filename;
	
	// 여러개 파일 업로드 시 [] 추가하여 배열로 만들어야 한다.
	// 배열로 지정했으니 여러건 출력 시 for loop 구현하면 됨
	private MultipartFile[] uploadFile;

}
