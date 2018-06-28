package store.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import store.com.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
