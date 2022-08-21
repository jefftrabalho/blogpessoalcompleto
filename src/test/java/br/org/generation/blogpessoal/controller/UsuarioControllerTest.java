package br.org.generation.blogpessoal.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start(){
	
		usuarioRepository.deleteAll();
		
	}
	
	@Test
	@Order(1)
	@DisplayName("cadstrar um usuario")
	public void deveCriarUmusuario() {
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario> (new Usuario(0l,
				"paulo antunes","https;//i.imgur.jpg", "paulo_antunes@email.com.br", "134564"));
	
		ResponseEntity<Usuario> resposta = testRestTemplate
				.exchange("/usuarios/cadastrar",HttpMethod.POST,requisicao, Usuario.class);
	
	}

	@Test
	@Order(2)
	@DisplayName("nao deve permitir duplicaçao do usuario")
	public void naoDeveDuplicarUsuario() {
		
		
		usuarioService.cadastrarUsuario(new Usuario(0l,
				"maria da silva","https;/.imgur.com","maria_silva@.com.br","13465278"));
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario (0l,
				"maria da silva","https;/.imgur.com","maria_silva@.com.br","13465278"	));
		
		ResponseEntity<Usuario> resposta = testRestTemplate
				.exchange("/usuarios/cadastrar", HttpMethod.POST,requisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST,resposta.getStatusCode());
	}

	
	@Test
	@Order(3)
	@DisplayName("alterar um usuario")
	public void deveAtualizarUmUsuario() {
		
		Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0l,
				"Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", "https://i.imgur.com/yDRVeK7.jpg"		));
		
		Usuario UsuarioUpdate = new Usuario(usuarioCreate.get().getId(),
				"Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", "https://i.imgur.com/yDRVeK7.jpg");
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(UsuarioUpdate);
		
		ResponseEntity<Usuario> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT,requisicao, Usuario.class);
		
		assertEquals(HttpStatus.OK,resposta.getStatusCode());
		assertEquals(UsuarioUpdate.getNome(),resposta.getBody().getNome());
		assertEquals(UsuarioUpdate.getFoto(),resposta.getBody().getFoto());
		assertEquals(UsuarioUpdate.getUsuario(),resposta.getBody().getUsuario());
	}

	@Test
	@Order(3)
	@DisplayName("listar todos os usuarios")
	public void deveMostrarTodosUsuarios() {
		
		usuarioService.cadastrarUsuario(new Usuario(0l,
				"Sabrina Sanches", "sabrina_sanches@email.com.br", "sabrina123", "https://i.imgur.com/5M2p5Wb.jpg"));
		
		
		usuarioService.cadastrarUsuario(new Usuario(0l,
				"ricardo marques ", "ricardo_sanches@email.com.br", "ricardo123", "https://i.imgur.com/5M2p5Wb.jpg"));
		
		
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/all", HttpMethod.GET,null,String.class);
		
		assertEquals(HttpStatus.OK,resposta.getStatusCode());
		
		
	}




}

