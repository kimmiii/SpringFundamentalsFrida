package be.vdab.Frida.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.Frida.forms.BeginNaamForm;
import be.vdab.Frida.services.SnackService;

@Controller
@RequestMapping("snacks")
public class SnackController {
	private final char[] alfabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private final SnackService snackService;
	
	public SnackController(SnackService snackService) {
		this.snackService = snackService;
	}
	
	@GetMapping("alfabet")
	ModelAndView alfabet() {
		return new ModelAndView("snackAlfabet", "alfabet", alfabet);
	}
	
	@GetMapping("alfabet/{letter}")
	ModelAndView findByBeginLetter(@PathVariable char letter) {
		return new ModelAndView("snackAlfabet", "alfabet", alfabet)
				.addObject("snacks", snackService.findByBeginNaam(String.valueOf(letter)));
	}
	
	
	@GetMapping("beginnaam/form")
	ModelAndView beginNaamForm() {
		return new ModelAndView("beginNaam")
				.addObject(new BeginNaamForm(""));
	}
	
	@GetMapping("beginnaam")
	ModelAndView beginNaam(@Valid BeginNaamForm form, Errors errors) {
		 ModelAndView modelAndView = new ModelAndView("beginNaam");
		 if (errors.hasErrors()) {
			 return modelAndView;
		 }
		 return modelAndView.addObject("snacks", snackService.findByBeginNaam(form.getBegin()));		 
	}
}
