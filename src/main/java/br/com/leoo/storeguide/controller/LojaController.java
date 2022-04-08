package br.com.leoo.storeguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.leoo.storeguide.model.Loja;
import br.com.leoo.storeguide.repository.LojaRepository;
import br.com.leoo.storeguide.repository.MarcaRepository;

@Controller
public class LojaController {
	@Autowired
	private MarcaRepository repMarca;
	@Autowired
	private LojaRepository repository;
	
	@RequestMapping("cadastroLoja")
	public String form(Model model) {
		model.addAttribute("tipos", repMarca.findAll());
		return "lojaCadastro";
	}
	
	@RequestMapping("salvarLoja")
	public String salvar(Loja loja,@RequestParam("fts") MultipartFile[] fts) {
		System.out.println(fts.length);
		//repository.save(loja);
		return "redirect:cadastroLoja";
	}
}
