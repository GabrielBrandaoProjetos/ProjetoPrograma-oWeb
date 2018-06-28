package store.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import store.com.model.Pessoa;
import store.com.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public void adicionarPessoa(Pessoa pessoa) {	
		pessoa.setSenha(new BCryptPasswordEncoder().encode(pessoa.getSenha()));
		pessoaRepository.save(pessoa);
	
	}
	
	public Pessoa buscaPorLogin(String login) {
		return pessoaRepository.findByLogin(login);
	}
	
	public void buscarPorId(Long id) {
		pessoaRepository.findById(id);
	}

	public void atualizarPessoa(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
		
	}

}
