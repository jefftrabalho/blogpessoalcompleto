package br.org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		
	usuarioRepository.deleteAll();
		
		usuarioRepository.save(new Usuario(0L,"joao da silva","http//i.imgur.com/h4:","joaoemail@.com.br","123456"));		
		usuarioRepository.save(new Usuario(0L,"manuela da silva","http//i.imgur.com/h4:","manuelaemail@.com.br","123456"));		
		usuarioRepository.save(new Usuario(0L,"adriana da silva","http//i.imgur.com/h4:","adrianemail@.com.br","123456"));		
		usuarioRepository.save(new Usuario(0L,"paulo da silva","http//i.imgur.com/h4:","pauloemail@.com.br","123456"));		
		
		
	}

	
	@Test
	@DisplayName("retorna 1 usuario")
	public void deveRetornarUmUsuario() {
	
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joaoemail@.com.br");
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
	}
	
	@Test
	@DisplayName("retorna 3 usuario")
	public void deveRetornarTresUsuarios() {
		
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3,listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("joao da silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("manuela da silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("adriana da silva"));
	}
}








