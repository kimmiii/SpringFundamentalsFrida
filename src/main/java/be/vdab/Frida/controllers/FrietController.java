package be.vdab.Frida.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.Frida.sessions.ZoekDeFriet;

@Controller
@RequestMapping("frieten")
class FrietController {
	private ZoekDeFriet zoekDeFriet;
	
	FrietController(ZoekDeFriet zoekDeFriet) {
		this.zoekDeFriet = zoekDeFriet;
	}
	
	@GetMapping("zoeken")
	ModelAndView zoekDeFriet() {
		return new ModelAndView("zoekDeFriet").addObject(zoekDeFriet);
	}

	@PostMapping("zoeken/nieuwspel")
	String nieuwSpel() {
		zoekDeFriet.reset();
		return "redirect:/frieten/zoeken";
	}
	
	@PostMapping("zoeken/opendeur")
	String openDeur(int index) {
		zoekDeFriet.openDeur(index);
		return "redirect:/frieten/zoeken";
	}
}
