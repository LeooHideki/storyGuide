package br.com.leoo.storeguide.rest;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.leoo.storeguide.annotation.Publico;
import br.com.leoo.storeguide.model.Avaliacao;
import br.com.leoo.storeguide.repository.AvaliacaoRepository;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoRestController {
	@Autowired
	private AvaliacaoRepository repository;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody Avaliacao avaliacao) {
		repository.save(avaliacao);
		return ResponseEntity.created(URI.create("/api/avalicao" + avaliacao.getId())).body(avaliacao);
	}
	
	@Publico
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public Avaliacao getById(@PathVariable("id") Long idAvaliacao) {
		return repository.findById(idAvaliacao).get();
		
	}
	
	@Publico
	@RequestMapping(value="/loja/{id}", method = RequestMethod.GET)
	public List<Avaliacao> getByLoja(@PathVariable("id") Long id) {
		return repository.findByLojaId(id);
		
	}
}
