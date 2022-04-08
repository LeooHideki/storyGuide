package br.com.leoo.storeguide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.leoo.storeguide.model.Administrador;
import br.com.leoo.storeguide.model.Marca;
import br.com.leoo.storeguide.repository.MarcaRepository;

@Controller
public class MarcaController {
	
	@Autowired
	private MarcaRepository repository;
	
	@RequestMapping("cadastroMarca")
	private String form() {
		return "marcaCadastro";
	}
	
	@RequestMapping(value="salvarMarca", method = RequestMethod.POST)
	public String salvarMarca(@Valid Marca m, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			attr.addFlashAttribute("msgErro", "Verifique os campos");
			return "redirect:cadastroMarca";
		}
		try {
			repository.save(m);
			attr.addFlashAttribute("mensagemSucesso", "Marca cadastrada com sucesso. ID:"+m.getId());
		}catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar:"+e.getMessage());
		}
		return "redirect:cadastroMarca";
	}
	@RequestMapping("listaMarca/{page}")
	public String listaMarca(Model model, @PathVariable("page") int page) {
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Marca> pagina = repository.findAll(pageable);
		model.addAttribute("marcas", pagina.getContent());
		int totalPages = pagina.getTotalPages();
		List<Integer> numPaginas = new ArrayList<Integer>();
		for (int i = 1; i <= totalPages; i++) {
			numPaginas.add(i);
		}
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pagAtual", page);
		return "marcaLista";
	}
	@RequestMapping("alterarMarca")
	public String alterar(Long id, Model model) {
		Marca m = repository.findById(id).get();
		model.addAttribute("marca", m);
		return "forward:cadastroMarca";
	}

	@RequestMapping("excluirMarca")
	public String excluir(Long id) {
		repository.deleteById(id);
		return "redirect:listaMarca/1";
	}
	@RequestMapping("/listaMarca")
	public String buscarEstilo(String estilo, Model model) {		
		model.addAttribute("marcas", repository.buscarEstilo(estilo));	
		return "marcaLista";
	}
}
