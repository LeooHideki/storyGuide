package br.com.leoo.storeguide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

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
import br.com.leoo.storeguide.model.Loja;
import br.com.leoo.storeguide.repository.AdminRepository;
import br.com.leoo.storeguide.util.HashUtil;

@Controller
public class AdmController {

	// variavel para persistências dos dados
	@Autowired
	private AdminRepository repository;

	@RequestMapping("cadastroAdmin")
	public String form() {
		return "admCadastro";
	}
	@RequestMapping("login")
	public String formLogin() {
		return "index";
	}
	

	// request mapping para salvar o Administrador, do tipo POST
	@RequestMapping(value="salvarAdmin", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador adm, BindingResult result, RedirectAttributes attr) {
		//verifica se houveram erros na validação
		if(result.hasErrors()) {
			//adiciona mensagem de erro
			attr.addFlashAttribute("msgErro", "Verifique os campos");
			//redireciona para o formulário
			return "redirect:cadastroAdmin";
		}
		
		//variavel para descobrir alteração ou inserção
		boolean alteracao = adm.getId() !=null ? true : false;
		//verificar se a senha está vazia
		if(adm.getSenha().equals(HashUtil.hash(""))) {
			if(!alteracao) {
				//retira a parte antes do @ no e-mail
				String parte = adm.getEmail().substring(0, adm.getEmail().indexOf("@"));
				//"setar" a parte na senha do admin
				adm.setSenha(parte);
			}else {
				//buscar a senha atual no banco
				String hash = repository.findById(adm.getId()).get().getSenha();
				//"setar" o hash na senha
				adm.setSenhaComHash(hash);
			}
		}
		
		try {
		//salva no banco de dados a entidade
		repository.save(adm);
		//adiciona uma mensagem de sucesso
		attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso. ID:"+adm.getId());
		}catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar:"+e.getMessage());
		}
		return "redirect:cadastroAdmin";
		
	}

	@RequestMapping("listaAdmin/{page}")
	public String listaAdmin(Model model, @PathVariable("page") int page) {
		// cria um pageable informando os parâmetros da página
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		// cria um page de Administrador atravéz dos parâmetros passados pelo repository
		Page<Administrador> pagina = repository.findAll(pageable);
		// adiciona à Model, lista com os admins
		model.addAttribute("admins", pagina.getContent());
		// variável para o total de páginas
		int totalPages = pagina.getTotalPages();
		// cria um List de inteiros para armazenar os números das páginas
		List<Integer> numPaginas = new ArrayList<Integer>();
		// preencher o list com as páginas
		for (int i = 1; i <= totalPages; i++) {
			// adiciona página ao List
			numPaginas.add(i);
		}
		// adiciona os valores à model
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("pagAtual", page);
		// retorna para o html da lista
		return "admLista";
	}

	@RequestMapping("alterarAdmin")
	public String alterar(Long id, Model model) {
		Administrador adm = repository.findById(id).get();
		model.addAttribute("administrador", adm);
		return "forward:cadastroAdmin";
	}

	@RequestMapping("excluirAdmin")
	public String excluir(Long id) {
		repository.deleteById(id);
		return "redirect:listaAdmin/1";
	}
	
	public String login(Administrador admLogin, RedirectAttributes attr, HttpSession session) {
		//busca o administrador no banco
		Administrador admin = repository.findByEmailAndSenha(admLogin.getEmail(), admLogin.getSenha());
		//verificar se existe
		if(admin == null) {
			attr.addAttribute("mensagemErro", "Login e/ou senha inválido(s)");
			return "redirect:/";
		}else {
			//salva o administrador na sessão
			session.setAttribute("usuarioLogado", admin);
			return "redirect:/listaLoja/1";
		}
		
	}
}
