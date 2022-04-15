package br.com.leoo.storeguide.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.leoo.storeguide.model.Loja;
import br.com.leoo.storeguide.repository.LojaRepository;
import br.com.leoo.storeguide.repository.MarcaRepository;
import br.com.leoo.storeguide.util.FirebaseUtil;

@Controller
public class LojaController {
	@Autowired
	private MarcaRepository repMarca;
	@Autowired
	private LojaRepository repository;
	@Autowired
	private FirebaseUtil fireUtil;
	
	@RequestMapping("cadastroLoja")
	public String form(Model model) {
		model.addAttribute("tipos", repMarca.findAll());
		return "lojaCadastro";
	}
	
	@RequestMapping("salvarLoja")
	public String salvar(Loja loja, RedirectAttributes attr, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
	//String para armazenar as URLs
	String fotos = loja.getFotos();
	// percorre cada arquivo no vetor
	for (MultipartFile arquivo : fileFotos) {
	// verifica se o arquivo existe
	if (arquivo.getOriginalFilename().isEmpty()) {
	// vai para o proximo arquivo
	continue;
	}
	try {
	// faz o upload e guarda a URL na string fotos
	fotos += fireUtil.upload(arquivo) + ";";
	} catch (IOException e) {
	e.printStackTrace();
	throw new RuntimeException(e);
	}
	}
	// guarda na variavel hospital as fotos
	loja.setFotos(fotos);

	boolean alteracao = loja.getId() != null ? true : false;
	try {
	// salva no banco de dados a entidade(admin)
	repository.save(loja);
	// adiciona uma mensagem de sucesso
	if(!alteracao) {
	attr.addFlashAttribute("mensagemSucesso", "Loja cadastrada com sucesso. ID:" + loja.getId());
	}else {
	attr.addFlashAttribute("mensagemSucesso", "Loja alterada com sucesso. ID:" + loja.getId());
	}
	} catch (Exception e) {
	//capitura um erro generico e exibe a mensagem
	attr.addFlashAttribute("mensagemErro", "Houve um erro ao salvar " + e.getMessage());
	}




	return "redirect:cadastroLoja";
	}



	// request mapping para listar hospitais
	@RequestMapping("listaLoja/{page}")
	public String listaLoja(Model model, @PathVariable("page") int page) {// @PathVariable("page") capitura o valor q
	// esta sendo passado na request
	// cria um pageable informando os parametros da pagina
	PageRequest pageable = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.ASC, "id"));
	// cria um page de hospital atraves dos parametros passados ao repository
	Page<Loja> pagina = repository.findAll(pageable);
	// adiciona à model, a lista com os hospitais
	model.addAttribute("loja", pagina.getContent());

	// variavel para o total de paginas
	int totalPages = pagina.getTotalPages();
	// cria um list de inteiros para armazenar os numeros das paginas
	List<Integer> numPaginas = new ArrayList<Integer>();
	// preencher o list com as paginas
	for (int i = 1; i <= totalPages; i++) {
	// adicioba a pagina ao list
	numPaginas.add(i);
	}
	// adiciona os valores na model
	model.addAttribute("numPaginas", numPaginas);
	model.addAttribute("totalPages", totalPages);
	model.addAttribute("pagAtual", page);
	// retorna para o hmtl da lista
	return "lojaLista";
	}

	@RequestMapping("alterarLoja")
	public String alterar(Long id,Model model) {
	Loja loja = repository.findById(id).get();
	model.addAttribute("loja", loja);
	return "forward:cadastroLoja";
	}

	@RequestMapping("excluirLoja")
	public String excluir(Long id){
	Loja l = repository.findById(id).get();
	if(l.getFotos().length() > 0) {
		for(String foto : l.verFotos()) {
			fireUtil.deletar(foto);
		}
	}
	repository.delete(l);
	return "redirect:/listaLoja/1";
	}

	@RequestMapping("excluirFotos")
	public String excluirFotos(Long idLoja, int numFoto, Model model) {
	//busca a loja
	Loja loja = repository.findById(idLoja).get();
	//busca a URL da foto
	String urlFoto = loja.verFotos()[numFoto];
	//deleta a foto
	fireUtil.deletar(urlFoto);
	//remover a url do atributo fotos
	loja.setFotos(loja.getFotos().replace(urlFoto+";", ""));
	//salva no banco
	repository.save(loja);
	//coloca a loja na model
	model.addAttribute("loja", loja);
	return "forward:cadastroLoja";
	}
}
