package be.vdab.Frida.forms;

import javax.validation.constraints.NotBlank;

public class BeginNaamForm {
	@NotBlank
	private final String begin;

	public BeginNaamForm(String begin) {
		this.begin = begin;
	}

	public String getBegin() {
		return begin;
	}
	
}
