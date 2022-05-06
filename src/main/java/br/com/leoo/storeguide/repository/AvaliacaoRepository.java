package br.com.leoo.storeguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leoo.storeguide.model.Avaliacao;

public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long> {
	public List<Avaliacao> findByLojaId(Long id);
}