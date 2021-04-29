package com.ssafy.pet.controller;

import com.ssafy.pet.dto.BoardDto;
import com.ssafy.pet.service.BoardService;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@AllArgsConstructor
public class BoardController {
    private BoardService boardService;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> list(Model model) {
    	Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        List<BoardDto> boardList = boardService.getBoardlist();
        if(boardList != null) {
        	resultMap.put("list", boardList);
        	status = HttpStatus.ACCEPTED;
        }else {
        	status = HttpStatus.NOT_FOUND;
        	resultMap.put("message", "정보 없음");
        }
        
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/post")
    public String write() {
        return "board/write.html";
    }

    @PostMapping("/insert")
    public String write(BoardDto boardDto) {
        boardService.savePost(boardDto);

        return "redirect:/";
    }
    
    @GetMapping("/detail/{no}")
    public ResponseEntity<Map<String, Object>> detail(@PathVariable("no") Long no) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        BoardDto boardDTO = boardService.getPost(no);
        if(boardDTO != null) {
        	resultMap.put("boardDTO", boardDTO);
        	status = HttpStatus.ACCEPTED;
        }else {
        	status = HttpStatus.NOT_FOUND;
        	resultMap.put("message", "정보 없음");
        }
        
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDTO = boardService.getPost(no);

        model.addAttribute("boardDto", boardDTO);
        return "board/update.html";
    }

    @PutMapping("/edit/{no}")
    public String update(BoardDto boardDTO) {
        boardService.savePost(boardDTO);

        return "redirect:/";
    }

    @DeleteMapping("/delete/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);

        return "redirect:/";
    }
}
