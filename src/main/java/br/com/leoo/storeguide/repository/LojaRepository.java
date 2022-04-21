package br.com.leoo.storeguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leoo.storeguide.model.Loja;


public interface LojaRepository extends PagingAndSortingRepository<Loja, Long>{
	
	public List<Loja> findByMarcaId(Long id);
	
}
