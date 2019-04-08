package be.vdab.Frida.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.Frida.domain.Adres;
import be.vdab.Frida.domain.Gemeente;

@RestController
@RequestMapping("/")
class IndexController {
	@GetMapping
	ModelAndView index(@CookieValue(name = "reedsBezocht", required = false)
			String reedsBezocht, HttpServletResponse response) {
		DayOfWeek weekdag = LocalDate.now().getDayOfWeek();
		String openGesloten =
		weekdag == DayOfWeek.MONDAY || weekdag == DayOfWeek.THURSDAY ?
		"gesloten" : "open";
		ModelAndView modelAndView = new ModelAndView("index", "openGesloten", openGesloten);
		modelAndView.addObject(new Adres("Grote markt", "7", new Gemeente("Brussel", 1000)));
		
		Cookie cookie = new Cookie("reedsBezocht", "ja");
		cookie.setMaxAge(31_536_000);
		response.addCookie(cookie);
		if (reedsBezocht != null) {
			modelAndView.addObject("reedsBezocht",true);
		}
		return modelAndView;
	}
}