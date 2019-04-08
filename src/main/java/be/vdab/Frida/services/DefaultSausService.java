package be.vdab.Frida.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import be.vdab.Frida.domain.Saus;
import be.vdab.Frida.repositories.SausRepository;

@Service
class DefaultSausService implements SausService{
	private final SausRepository sausRepository;
		
	public DefaultSausService(@Qualifier("properties")SausRepository sausRepository) {
		this.sausRepository = sausRepository;
	}

	@Override
	public List<Saus> findAll() {
		return sausRepository.findAll();
	}

	@Override
	public List<Saus> findByNaamBeginMet(char letter) {
		return sausRepository.findAll().stream()
				.filter(saus -> saus.getNaam().charAt(0) == letter)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Saus> findById(long id) {
		return sausRepository.findAll().stream()
				.filter(saus -> saus.getId() == id).findFirst();
	}
	
}
