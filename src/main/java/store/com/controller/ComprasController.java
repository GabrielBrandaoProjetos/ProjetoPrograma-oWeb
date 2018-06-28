package store.com.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import store.com.model.Compras;
import store.com.model.Pessoa;
import store.com.model.Produto;
import store.com.service.ComprasService;
import store.com.service.PessoaService;
import store.com.service.ProdutoService;

@Controller
@RequestMapping("/compras")
public class ComprasController {
	
	@Autowired
	private ComprasService comprasService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PessoaService pessoaService;
	
	@RequestMapping("/finalizar")
	public ModelAndView salvarComapras(){
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		
		List<Produto> produtos = pessoa.getCarrinho();
		Compras compra = new Compras();
		compra.setPessoa(pessoa);
		compra.setProdutos(new ArrayList<Produto>());
		comprasService.salvarCompras(compra);
		
		float x = (float) 0;
		float total = (float) 0;
		for (Produto produto : produtos) {
			x = x + Float.parseFloat(produto.getValor());
			total = x;
			DecimalFormat df = new DecimalFormat("R$,##0.00;(R$,##0.00)");
			String dx = df.format(total);
			List<Produto> lista = compra.getProdutos();
			lista.add(produto);
			compra.setProdutos(lista);
			compra.setValor(String.valueOf(dx));
			comprasService.salvarCompras(compra);
		}
		produtos.clear();
		pessoa.setCarrinho(produtos);
		pessoaService.atualizarPessoa(pessoa);
		ModelAndView mv = new ModelAndView("redirect:/compras/historico");
		
		return mv;
	}
	
	@GetMapping("/historico")
	public ModelAndView listarCompra() {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		Pessoa pessoa = pessoaService.buscaPorLogin(user.getUsername());
		List<Compras> listaCompra = pessoa.getCompras();
		ModelAndView mv = new ModelAndView("historico-compras");
		mv.addObject("userNome", user.getUsername());
		mv.addObject("todasAsCompras", listaCompra);
		return mv;
	}

}
