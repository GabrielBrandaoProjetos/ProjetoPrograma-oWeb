package store.com.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import store.com.model.Pessoa;
import store.com.model.Produto;
import store.com.model.Role;
import store.com.service.PessoaService;
import store.com.service.ProdutoService;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping("/addcliente")
	public ModelAndView formularioPessoa() {
		ModelAndView mv = new ModelAndView("formulario-pessoa");
		mv.addObject("pessoa", new Pessoa());
		return mv;
	}
	
	@PostMapping("/salvar")
	public ModelAndView adicionarPessoa(Pessoa pessoa) {
		Pessoa p = pessoaService.buscaPorLogin(pessoa.getLogin());
		if(p==null) {
		Role role = new Role();
		List<Pessoa> lista = new ArrayList<Pessoa>();
		List<Role> regras = new ArrayList<Role>();
		role.setPapel("ROLE_USER");
		lista.add(pessoa);
		regras.add(role);
		role.setPessoas(lista);
		pessoa.setRoles(regras);
		pessoaService.adicionarPessoa(pessoa);
		ModelAndView mv = new ModelAndView("redirect:/pessoa/addcliente");
		return mv;
		}else {
			ModelAndView mv =  new ModelAndView("formulario-pessoa");
			mv.addObject("mensagem", "Este Login está sendo utilizado!");
			mv.addObject("pessoa", pessoa);
			return mv;
		}
		
	}
	
	@RequestMapping("/login")
	public ModelAndView paginaLogin() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	
	@RequestMapping("/logout")
	public ModelAndView paginaLogout() {
		ModelAndView mv = new ModelAndView("redirect:/pessoa/login");
		return mv;
	}
	
	@RequestMapping("/addcarrinho/{id}")
	public ModelAndView addCarrinho(@PathVariable Long id){
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
				
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		Produto produto = produtoService.buscarProduto(id);
		
		List<Produto> produtos = pessoa.getCarrinho();
		produtos.add(produto);
		pessoa.setCarrinho(produtos);
		
		pessoaService.atualizarPessoa(pessoa);
		
		ModelAndView mv = new ModelAndView("redirect:/produto/listar");
		
		return mv;
	}
	
	@RequestMapping("/excluirproduto/{id}")
	public ModelAndView excluirProduto(@PathVariable Long id) {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
				
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		List<Produto> produtos = pessoa.getCarrinho();
		produtos.remove(produtoService.buscarProduto(id));
		pessoa.setCarrinho(produtos);
		pessoaService.atualizarPessoa(pessoa);
		
		ModelAndView mv = new ModelAndView("redirect:/pessoa/carrinho");

		return mv;
	}
	
	@GetMapping("/carrinho")
	public ModelAndView listarCarrinho() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
				
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		
		List<Produto> produtocarrinho = pessoa.getCarrinho();
		float valor=(float) 0;
		for (Produto produto : produtocarrinho) {
			valor = valor + Float.parseFloat(produto.getValor());
		}
		DecimalFormat df = new DecimalFormat("R$,##0.00;(R$,##0.00)");
		String dx = df.format(valor);
		ModelAndView mv = new ModelAndView("carrinho");
		mv.addObject("todosOsProdutos", produtocarrinho);
		mv.addObject("userNome", user.getUsername());
		mv.addObject("valorTotal", dx);
		return mv;
	
	}

}
