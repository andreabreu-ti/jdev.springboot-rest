package br.com.jdev.springboot_rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jdev.springboot_rest.model.Usuario;
import br.com.jdev.springboot_rest.repository.UsuarioRepository;

/**
 * Responsável por interceptar os dados de uma aplicação
 * 
 */
@RestController
public class GreetingsController {

	// Injeção de dependencias
	@Autowired
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */
	@RequestMapping(value = "/mostranome/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Curso SpringBoot API: " + name + "!";
	}

	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {

		Usuario usuario = new Usuario();
		usuario.setNome(nome);

		usuarioRepository.save(usuario); // Gravar no banco de dados

		return "Ola mundo: " + nome;

	}

	/**
	 * Metódo de API - Listar
	 * 
	 * @return Método para retornar uma lista de usuários
	 * @ResponseBody -> Retorna os dados para o corpo
	 * @return Retorna a lista em JSON
	 */

	@GetMapping(value = "listatodos")
	@ResponseBody
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll(); // Executa a consulta no banco de dados
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
	}

	/**
	 * Método de Salvar usuário
	 * 
	 * @PostMapping --> Mapeia a URL
	 * @ResponseBody --> Descrição da resposta
	 * @RequestBody --> Recebe os dados para salvar
	 * 
	 */
	@PostMapping(value = "salvar")
	@ResponseBody
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {

		Usuario user = usuarioRepository.save(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}

	/**
	 * Método para deletar usuário
	 * 
	 * @RequestParam
	 */
	@DeleteMapping(value = "delete")
	@ResponseBody
	public ResponseEntity<String> delete(@RequestParam Long iduser) {

		usuarioRepository.deleteById(iduser);
		return new ResponseEntity<String>("User deletado com susceso", HttpStatus.OK);
	}

	/**
	 * Método para buscar usuário por Id
	 * 
	 */
	@GetMapping(value = "buscaruserid")
	@ResponseBody
	public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "iduser") Long iduser){
		Usuario usuario = usuarioRepository.findById(iduser).get();
		
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	/**
	 * Método para atualizar usuário
	 * 
	 */
	@PutMapping(value = "atualizar")
	@ResponseBody
	public ResponseEntity<Usuario> atualizar(@RequestBody Usuario usuario) {

		Usuario user = usuarioRepository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
}
