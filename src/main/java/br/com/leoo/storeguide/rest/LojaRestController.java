package br.com.leoo.storeguide.rest;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.leoo.storeguide.model.Loja;
import br.com.leoo.storeguide.repository.LojaRepository;

@RestController
@RequestMapping("/api/loja")
public class LojaRestController {
	
	@Autowired
	private LojaRepository repository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Loja> getLojas(){
		return repository.findAll();
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Loja> getLoja(@PathVariable("id") Long id){
		//tenta buscar a loja no repository
		Optional<Loja> optional = repository.findById(id);
		//se a loja existir
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		}else {
			return ResponseEntity.notFound().build();
		}	
	}
	@RequestMapping(value="/tipo/{id}", method = RequestMethod.GET)
	public Iterable<Loja> getTipo(@PathVariable("id") Long id){
		return repository.findByMarcaId(id);
	}
}
