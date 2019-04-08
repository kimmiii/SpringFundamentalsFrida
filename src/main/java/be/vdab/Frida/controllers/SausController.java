package be.vdab.Frida.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.Frida.services.SausService;

@Controller
@RequestMapping("sauzen")
class SausController {
	private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private final SausService sausService;
		
	public SausController(SausService sausService) {
		this.sausService = sausService;
	}

	@GetMapping
	ModelAndView sauzen() {
		return new ModelAndView("sauzen", "sauzen", sausService.findAll());
	}
	
	@GetMapping("{id}")
	ModelAndView saus(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView("saus");
		sausService.findById(id).ifPresent(saus -> modelAndView.addObject(saus));
		return modelAndView;
	}
	
	@GetMapping("alfabet")
	ModelAndView alfabet() {
		return new ModelAndView("sausAlfabet", "alfabet", alfabet);
	}
	
	@GetMapping("alfabet/{letter}")
	ModelAndView sauzenBeginnendMet(@PathVariable char letter) {
		return new ModelAndView("sausAlfabet", "alfabet", alfabet)
				.addObject("sauzen", sausService.findByNaamBeginMet(letter));
	}
}
