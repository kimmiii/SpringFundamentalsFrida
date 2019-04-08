package be.vdab.Frida.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import be.vdab.Frida.domain.Snack;
import be.vdab.Frida.repositories.SnackRepository;

@Service
@Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
class DefaultSnackService implements SnackService{
	private final SnackRepository snackRepository;

	public DefaultSnackService(SnackRepository snackRepository) {
		this.snackRepository = snackRepository;
	}

	@Override
	public Optional<Snack> findById(long id) {
		return snackRepository.findById(id);
	}

	@Override
	@Transactional(readOnly =false, isolation = Isolation.READ_COMMITTED)
	public void update(Snack snack) {
		snackRepository.update(snack);
	}

	@Override
	public List<Snack> findByBeginNaam(String beginNaam) {
		return snackRepository.findByBeginNaam(beginNaam);
	}
	
	
}
