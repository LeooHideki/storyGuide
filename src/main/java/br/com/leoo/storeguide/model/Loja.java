package br.com.leoo.storeguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Loja {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private String cep;
	private String endereco;
	private String numero;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String formasPagamento;
	private String horario;
	private String site;
	private String telefone;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	private boolean entrega;
	private boolean estacionamento;
	@ManyToOne
	private Marca marca;
}
