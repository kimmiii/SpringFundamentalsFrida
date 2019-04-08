package be.vdab.Frida.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.Frida.domain.Snack;
import be.vdab.Frida.exceptions.SnackNietGevondenException;

@RunWith(SpringRunner.class)
@JdbcTest
@AutoConfigureTestDatabase(replace =Replace.NONE)
@Import(JdbcSnackRepository.class)
@Sql("/insertSnacks.sql")
public class JdbcSnackRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private JdbcSnackRepository repository;
	private long idVanTestSnack() {
		return super.jdbcTemplate.queryForObject(
				"select id from snacks where naam = 'test'", Long.class);
	}
	
	@Test
	public void update() {
		long id = idVanTestSnack();
		Snack snack = new Snack(id, "test", BigDecimal.ONE);
		repository.update(snack);
		assertEquals(0, BigDecimal.ONE.compareTo(
				super.jdbcTemplate.queryForObject(
						"select prijs from snacks where id = ?", 
						BigDecimal.class,id)));
	}
	
	@Test(expected= SnackNietGevondenException.class)
	public void updateOnbestaandeSnack() {
		repository.update(new Snack(-1, "test", BigDecimal.ONE));
	}
	
	@Test
	public void findById() {
		assertEquals("test", repository.findById(idVanTestSnack()).get().getNaam());
	}
	
	@Test
	public void findByOnbestaandeId() {
		assertFalse(repository.findById(-1).isPresent());
	}
	
	@Test
	public void findByBeginNaam() {
		List<Snack> snacks = repository.findByBeginNaam("t");
		long aantalSnacks = super.jdbcTemplate.queryForObject(
				"select count(*) from snacks where naam like 't%'", Long.class);
		assertEquals(aantalSnacks, snacks.size());
		snacks.stream().map(snack -> snack.getNaam())
			.reduce((vorigeNaam, naam) ->{
					assertTrue(naam.toLowerCase().startsWith("t"));
					assertTrue(naam.compareToIgnoreCase(vorigeNaam) >=0);
					return naam;
			});
	}
}
