package be.vdab.Frida.forms;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;

public class BeginNaamFormTest {
	private Validator validator;
	
	@Before
	public void before() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void beginOk() {
		assertTrue(validator.validateValue(BeginNaamForm.class, "begin", "t").isEmpty());
	}
	
	@Test
	public void beginMoetIngevuldZijn() {
		assertFalse(validator.validateValue(BeginNaamForm.class, "begin", null).isEmpty());
	}
	
	@Test
	public void beginMoetMagNietEnkelSpatiesBevatten() {
		assertFalse(validator.validateValue(BeginNaamForm.class, "begin", "  ").isEmpty());
	}
}
