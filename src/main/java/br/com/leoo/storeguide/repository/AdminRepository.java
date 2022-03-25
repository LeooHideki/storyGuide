package br.com.leoo.storeguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leoo.storeguide.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long>{

}
