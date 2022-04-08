package br.com.leoo.storeguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leoo.storeguide.model.Loja;


public interface LojaRepository extends PagingAndSortingRepository<Loja, Long>{
	
}
