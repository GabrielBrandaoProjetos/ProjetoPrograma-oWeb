package store.com.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import store.com.model.Pessoa;
import store.com.repository.PessoaRepository;

@Repository
@Transactional
public class UserDetailsServiceImplementacao implements UserDetailsService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
		Pessoa pessoa = pessoaRepository.findByLogin(login);
		
		if (pessoa == null) {
			throw new UsernameNotFoundException("Usuario não existe!");
		}
		
		return new User(pessoa.getUsername(), pessoa.getPassword(),true,true,true,true,pessoa.getAuthorities());
		
	}
}
