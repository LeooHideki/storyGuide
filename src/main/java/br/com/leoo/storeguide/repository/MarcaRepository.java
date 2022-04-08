package br.com.leoo.storeguide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.leoo.storeguide.model.Marca;



public interface MarcaRepository extends PagingAndSortingRepository<Marca, Long>{
	
	@Query("SELECT m FROM Marca m WHERE m.estilo LIKE %:n%")
	public List<Marca> buscarEstilo(@Param("n")String estilo);
	
	public List<Marca> findAllByOrderByNomeAsc();

}
