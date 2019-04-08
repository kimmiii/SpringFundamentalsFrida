package be.vdab.Frida.repositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import be.vdab.Frida.domain.Saus;
import be.vdab.Frida.exceptions.SausRepositoryException;

@Component
@Qualifier("CSVSausenPad")
class CSVSausRepository implements SausRepository{
	private final Path pad;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
	public CSVSausRepository(@Value("${CSVSausenPad}") Path pad) {
		this.pad = pad;
	}

	@Override
	public List<Saus> findAll() {
		//List<Saus> sauzen = new ArrayList<>();
		try {
			return Files.lines(pad)
					.filter(regel -> !regel.isEmpty())
					.map(regel -> maakSaus(regel))
					.collect(Collectors.toList());
		} catch (IOException ex) {
			String fout = "Fout bij lezen " + pad;
			logger.error(fout, ex);
			throw new SausRepositoryException(fout);
		}
		
	}
	
	private Saus maakSaus(String regel) {
		String[] onderdelen = regel.split(",");
		if (onderdelen.length < 2) {
			String fout = pad + ":" + regel + " bevat minder dan 2 onderdelen";
			logger.error(fout);
			throw new SausRepositoryException(fout);
		}
		try {
			String[] ingredienten = Arrays.copyOfRange(onderdelen, 2, onderdelen.length);			
			return new Saus(Long.parseLong(onderdelen[0]), onderdelen[1], ingredienten);
		} catch (NumberFormatException ex) {
			String fout = pad + ":" + " bevat verkeerde id";
			logger.error(fout, ex);
			throw new SausRepositoryException(fout);
		}
	}

}
