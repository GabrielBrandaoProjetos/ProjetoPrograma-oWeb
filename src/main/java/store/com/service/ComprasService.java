package store.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import store.com.model.Compras;
import store.com.repository.ComprasRepository;

@Service
public class ComprasService {
	
	@Autowired
	private ComprasRepository comprasRepository;
	
	public void salvarCompras(Compras compras) {
		comprasRepository.save(compras);
	}

	public List<Compras> listarCompras() {
		return comprasRepository.findAll();
	}
	

}
