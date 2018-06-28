package store.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import store.com.model.Compras;

public interface ComprasRepository extends JpaRepository<Compras, Long> {
	
}
