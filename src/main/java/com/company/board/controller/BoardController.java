package com.company.board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.company.board.service.BoardVO;
import com.company.board.service.impl.BoardMapper;
import com.company.common.FileRenamePolicy;


@Controller
public class BoardController {
	
	@Autowired BoardMapper boardMapper;
	
	@GetMapping("/insertBoard")
	public String insertBoard() { // 등록 페이지 이동
		return "board/insertBoard";
	}
	
	// https://winmargo.tistory.com/102
	@PostMapping("/insertBoard")
	public String insertBoardProc(BoardVO vo) throws Exception { // 등록
		System.out.println("insertBoard 확인 =====> " + vo);
		// 첨부파일처리
		MultipartFile[] files = vo.getUploadFile();
		String fileNames = "";
		// 마지막 파일 이름 뒤에 , 없애기
		boolean start = true;
		for(MultipartFile file : files) {
			
			if(file != null && !file.isEmpty() && file.getSize() > 0) {
				String fileName = file.getOriginalFilename(); // 업로드된 파일의 파일명 추출
				// 파일명 중복체크 -> rename
				File rename = FileRenamePolicy.rename(new File("c:/upload/" + fileName)); // <- 이 파일이 있는지 검사. 같은 파일명이 있다면 파일명 변경
				
				// 마지막 파일 이름 뒤에 , 없애기
				if(!start ) {
					fileNames += ",";
				}else {
					start = false;
				}
				fileNames += rename.getName();
				file.transferTo(rename); // 임시폴더에서 업로드 폴더로 파일 이동
			}
		}
		vo.setFilename(fileNames); // 추출된 파일명 VO에 저장 -> DB(테이블)에 저장(파일 이름만)
		// 등록 서비스 호추
		boardMapper.insertBoard(vo);
		return "redirect:/getBoard?seq="+vo.getSeq();
	}
	
	// 단건조회
	@GetMapping("/getBoard")
	public String getBoard(BoardVO vo, Model model) {
		model.addAttribute("board", boardMapper.getBoard(vo));
		return "board/getBoard";
	}
	
	// 게시글 번호로 파일 다운
	@GetMapping("/fileDown")
	public void fileDown(BoardVO vo, HttpServletResponse response) throws Exception {
		vo = boardMapper.getBoard(vo);
		File file = new File("c:/upload", vo.getFilename());
		if(file.exists() ) {
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(vo.getFilename(), "utf-8") + "\"");
			
			BufferedInputStream in = null;
			BufferedOutputStream out = null;
			try {
			// 파일 다운로드 로직 => file을 inputstream으로 읽어서 outputstream으로 복사(파일 입력/출력을 동시에 실시)
			in = new BufferedInputStream(new FileInputStream(file));
			out = new BufferedOutputStream(response.getOutputStream());
			FileCopyUtils.copy(in, out);
			out.flush();
			} catch (IOException ex) {
			} finally {
			in.close(); 
			response.getOutputStream().flush(); 
			response.getOutputStream().close();
			}
			
		}else {
			response.setContentType("text/html; charset=UTF-8"); // .jsp 파일 설정과 동일하게 작성
			response.getWriter()
			.append("<script>")
			.append("alert('file not found / 파일 없음');")
			.append("history.back();") // 방법 01
			// .append("history.go(-1);") 방법 02
			.append("</script>");
		}
	}
	
	// 파일 이름으로 다운
		@GetMapping("/fileNameDown")
		public void fileNameDown(BoardVO vo, HttpServletResponse response) throws Exception {
			File file = new File("c:/upload", vo.getFilename());
			if(file.exists() ) {
				response.setContentType("application/octet-stream;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(vo.getFilename(), "utf-8") + "\"");
				
				BufferedInputStream in = null;
				BufferedOutputStream out = null;
				try {
				// 파일 다운로드 로직 => file을 inputstream으로 읽어서 outputstream으로 복사
				in = new BufferedInputStream(new FileInputStream(file));
				out = new BufferedOutputStream(response.getOutputStream());
				FileCopyUtils.copy(in, out);
				out.flush();
				} catch (IOException ex) {
				} finally {
				in.close(); response.getOutputStream().flush(); response.getOutputStream().close();
				}
				
			}else {
				response.setContentType("text/html; charset=UTF-8"); // .jsp 파일 설정과 동일하게 작성
				response.getWriter()
				.append("<script>")
				.append("alert('file not found / 파일 없음');")
				.append("history.back();") // 방법 01
				// .append("history.go(-1);") 방법 02
				.append("</script>");
			}
		}
	
	

}
