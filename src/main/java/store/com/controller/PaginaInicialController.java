package store.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import store.com.model.Pessoa;
import store.com.service.PessoaService;

@Controller
@RequestMapping("")
public class PaginaInicialController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@RequestMapping("/store")
	public ModelAndView paginaInicial() {
		if(SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
				
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("userNome", user.getUsername());
		return mv;
		}else {
			ModelAndView mv = new ModelAndView("index");
			return mv;		
		}
	}
	
	@RequestMapping("/contact")
	public ModelAndView paginaContact() {
		if(SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
			Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails user = (UserDetails) auth;
					
			ModelAndView mv = new ModelAndView("contact");
			mv.addObject("userNome", user.getUsername());
			return mv;
			}else {
				ModelAndView mv = new ModelAndView("contact");
				return mv;		
			}
	}
	
	@RequestMapping("/about")
	public ModelAndView paginaAbout() {
		if(SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
			Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails user = (UserDetails) auth;
					
			ModelAndView mv = new ModelAndView("about");
			mv.addObject("userNome", user.getUsername());
			return mv;
			}else {
				ModelAndView mv = new ModelAndView("about");
				return mv;		
			}

	}
	
}
