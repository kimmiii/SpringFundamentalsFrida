package be.vdab.Frida.repositories;

import java.util.List;
import java.util.Optional;

import be.vdab.Frida.domain.Snack;

public interface SnackRepository {
	Optional<Snack> findById(long id);
	void update(Snack snack);
	List<Snack> findByBeginNaam(String beginNaam);
}
