package br.com.leoo.storeguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.leoo.storeguide.util.HashUtil;
import lombok.Data;

//cria os getters & setters
@Data
//mapeia a entidade para o JPA
@Entity
public class Administrador {
	//chave primária e auto-incremento
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nome;
	//define a coluna email com um índice único
	@Column(unique = true)
	@Email
	private String email;
	@NotEmpty
	private String senha;
	
	//método set que aplica hash na senha
	public void setSenha(String senha) {
		this.senha = HashUtil.hash(senha);
	}
	//"seta" o hash na senha
	public void setSenhaComHash(String hash) {
		this.senha = hash;
	}
}
