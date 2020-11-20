package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@Value("${config.schedule.open}")
	private String open;

	@Value("${config.schedule.close}")
	private String close;

	@Autowired
	MessageSource messageSource;

	@GetMapping({ "/", "/index" })
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/closed")
	public String closed(Model model) {
		String message = messageSource.getMessage("mall.message.closed", new String[] { open, close },
				LocaleContextHolder.getLocale());
		model.addAttribute("msg_closed", message);
		return "closed";
	}

}
