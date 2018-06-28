package store.com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import store.com.model.Produto;
import store.com.repository.ProdutoRepository;
import store.com.util.ArquivoUtils;



@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public void adicionarProduto(Produto produto, MultipartFile imagem) {
		produtoRepository.save(produto);
		String caminho = "imagem/" + produto.getId() + ".jpg";
		ArquivoUtils.salvarImagem(caminho, imagem);
	}
	
	public List<Produto> retornarTodosOsProdutos() {
		return produtoRepository.findAll();
	}
	
	public void excluirProduto(Long id) {
		produtoRepository.deleteById(id);
	}

	public Produto buscarProduto(Long id) {
		return produtoRepository.getOne(id);
	}

}
