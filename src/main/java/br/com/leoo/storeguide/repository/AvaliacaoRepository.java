package br.com.leoo.storeguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leoo.storeguide.model.Avaliacao;

public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long> {

}