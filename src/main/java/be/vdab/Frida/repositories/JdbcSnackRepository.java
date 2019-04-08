package be.vdab.Frida.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import be.vdab.Frida.domain.Snack;
import be.vdab.Frida.exceptions.SnackNietGevondenException;

@Repository
class JdbcSnackRepository implements SnackRepository{
	private final JdbcTemplate template;
	private final RowMapper<Snack> snackRowMapper = (result, rowNum) -> 
		new Snack(result.getLong("id"), result.getString("naam"),
				result.getBigDecimal("prijs"));
	private static final String UPDATE_SNACK = 
			"update snacks set naam =?, prijs=? where id=?";
	private static final String SELECT_BY_BEGIN_NAAM =
			"select id, naam, prijs from snacks where naam like ? order by naam";
	private static final String SELECT_BY_ID = 
			"select id, naam, prijs from snacks where id =?";
	
	public JdbcSnackRepository(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public Optional<Snack> findById(long id) {
		try {
			return Optional.of(template.queryForObject(SELECT_BY_ID, snackRowMapper, id));
		} catch (IncorrectResultSizeDataAccessException ex) {
			return Optional.empty();
		}
	}

	@Override
	public void update(Snack snack) {
		if (template.update(UPDATE_SNACK, snack.getNaam(), snack.getPrijs(), 
				snack.getId()) == 0) {
			throw new SnackNietGevondenException();
		}		
	}

	@Override
	public List<Snack> findByBeginNaam(String beginNaam) {
		return template.query(SELECT_BY_BEGIN_NAAM, snackRowMapper, beginNaam + '%');
	}

}
