package com.kh.spring.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.spring.api.model.service.ApiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value="api", produces="application/json; charset=UTF-8")
@RequiredArgsConstructor
public class ApiController {
	
	private final ApiService apiService;
	
	
	
	
	@GetMapping("beef")
	public String getBeef() {
		
		return apiService.requestBeef();
		
		
		
	}
	@GetMapping("blog")
	public String getBlog(@RequestParam(name="query")String query) {
		return apiService.requestBlog(query);
	}
	@GetMapping("busan")
	public String getBusan(@RequestParam(name="pageNo")int pageNo) {
		return apiService.requestBusan(pageNo);
	}
	@GetMapping("busan/detail/{num}")
	public String getBusanDetail(@PathVariable(name="num")int num) {
		return apiService.requestBusanDetail(num);
	}

}
