package com.shinhan.maahproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shinhan.maahproject.repository.BankRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BankController {
	final BankRepository bRepo;
	
	@GetMapping("/bank.do")
	public void bankDisplay(Model model) {
		model.addAttribute("blist", bRepo.findAll());
	}
}
