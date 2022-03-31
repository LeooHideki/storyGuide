package br.com.leoo.storeguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leoo.storeguide.model.Marca;


public interface MarcaRepository extends PagingAndSortingRepository<Marca, Long>{

}
