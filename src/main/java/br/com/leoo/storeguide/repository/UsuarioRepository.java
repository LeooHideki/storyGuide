package br.com.leoo.storeguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leoo.storeguide.model.Usuario;




public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long>{

}

