package store.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import store.com.security.UserDetailsServiceImplementacao;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceImplementacao userDetailsImplementacao;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		
		.antMatchers("/store").permitAll()
		.antMatchers("/contact").permitAll()
		.antMatchers("/about").permitAll()
		.antMatchers("/produto/addproduto").hasRole("ADMIN")
		.antMatchers("/produto/adicionar").hasRole("ADMIN")
		.antMatchers("/produto/listar").permitAll()
		.antMatchers("/produto/pedidos").hasAnyRole("ADMIN", "USER")
		.antMatchers("/produto/*").hasRole("ADMIN")
		.antMatchers("/produto/excluir/*").hasRole("ADMIN")
		
		.antMatchers("/pessoa/addcliente").permitAll()
		.antMatchers("/pessoa/salvar").permitAll()
		.antMatchers("/pessoa/addcarrinho/*").hasRole("USER")
		.antMatchers("/pessoa/carrinho").hasRole("USER")
		.antMatchers("/pessoa/excluirproduto/*").hasRole("USER")
		
		.antMatchers("/compras/finalizar").hasRole("USER")
		.antMatchers("/compras/historico").hasRole("USER")
		.anyRequest().authenticated()
		
		.and()
		.formLogin()
		.loginPage("/pessoa/login").permitAll().defaultSuccessUrl("/store")
		
		.and()
		.logout().logoutSuccessUrl("/store").logoutUrl("/logout").permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsImplementacao).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/imagem/**");
	}

}
