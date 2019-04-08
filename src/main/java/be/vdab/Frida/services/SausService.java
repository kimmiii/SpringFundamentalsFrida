package be.vdab.Frida.services;

import java.util.List;
import java.util.Optional;

import be.vdab.Frida.domain.Saus;

public interface SausService {
	List<Saus> findAll();
	List<Saus> findByNaamBeginMet(char letter);
	Optional<Saus> findById(long id);
}
