package be.vdab.Frida.services;

import java.util.List;
import java.util.Optional;

import be.vdab.Frida.domain.Snack;

public interface SnackService {
	Optional<Snack> findById(long id);
	void update(Snack snack);
	List <Snack> findByBeginNaam(String beginNaam);
}
