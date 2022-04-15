package br.com.leoo.storeguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leoo.storeguide.model.Administrador;
import br.com.leoo.storeguide.model.Loja;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long>{
	
	public Administrador findByEmailAndSenha(String email, String senha);
	
}
