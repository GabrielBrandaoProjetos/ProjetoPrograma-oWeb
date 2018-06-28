package store.com.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import store.com.model.Pessoa;
import store.com.model.Produto;
import store.com.service.ProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	
	@RequestMapping("/addproduto")
	public ModelAndView formularioProduto() {
		ModelAndView mv = new ModelAndView("formulario-produto");
		mv.addObject("produto", new Produto());
		return mv;
	}
	
	@PostMapping("/adicionar")
	public ModelAndView adicionarProduto(Produto produto, @RequestParam(value="imagem") MultipartFile imagem) {
		produtoService.adicionarProduto(produto, imagem);
		ModelAndView mv = new ModelAndView("redirect:/produto/listar");
		
		return mv;
	}
	
	@GetMapping("/listar")
	public ModelAndView listarProduto() {
		if(SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
			Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails user = (UserDetails) auth;
			List<Produto> produtos = produtoService.retornarTodosOsProdutos();
			ModelAndView mv = new ModelAndView("skates");
			mv.addObject("userNome", user.getUsername());
			mv.addObject("todosOsProdutos", produtos);
			return mv;}else {
				ModelAndView mv = new ModelAndView("skates");
				List<Produto> produtos = produtoService.retornarTodosOsProdutos();
				mv.addObject("todosOsProdutos", produtos);
				return mv;		
			}
	}
	
	@RequestMapping("{id}")
	public ModelAndView atualizarProduto(@PathVariable Long id) {
		Produto produto = produtoService.buscarProduto(id);
		ModelAndView mv = new ModelAndView("formulario-produto");
		mv.addObject("produto", produto);
		
		return mv;
	}
	
	@RequestMapping("/excluir/{id}")
	public ModelAndView excluirProduto(@PathVariable Long id) {
		produtoService.excluirProduto(id);
		ModelAndView mv = new ModelAndView("redirect:/produto/listar");
		return mv;
	}

}
