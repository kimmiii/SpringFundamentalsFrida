package be.vdab.Frida.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.vdab.Frida.domain.Saus;
import be.vdab.Frida.services.SausService;

@RunWith(MockitoJUnitRunner.class)
public class SausControllerTest {
	private SausController controller;
	@Mock
	private SausService sausService;
	
	@Before
	public void before() {
		when(sausService.findById(3)).thenReturn(Optional.of(new Saus(3L, "", null)));
		controller = new SausController(sausService);
	}
	
	@Test
	public void sauzenGebruiktDeThymeleafPaginaSauzen() {
		assertEquals("sauzen", controller.sauzen().getViewName());
	}
	
	@Test
	public void sauzenGeeftSauzenDoorAanDeThymeleafPagina() {
		assertTrue(controller.sauzen().getModel().get("sauzen") instanceof List);
	}
	
	@Test
	public void sausGebruiktDeThymeleagPaginaSaus() {
		assertEquals("saus", controller.saus(3).getViewName());
	}
	
	@Test
	public void sausGeeftGevondenSausDoorAanDeThymeleafPagina() {
		Saus saus = (Saus) controller.saus(3).getModel().get("saus");
		assertEquals(3,saus.getId());
	}
	
	@Test
	public void sausGeeftOnbestaandeSausNietDoorAanDeThymeleafPagina() {
		assertFalse(controller.saus(-3).getModel().containsKey("saus"));
	}
}
