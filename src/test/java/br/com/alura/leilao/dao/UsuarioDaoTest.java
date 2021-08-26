package br.com.alura.leilao.dao;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {

	private EntityManager entityManager;
	private UsuarioDao usuarioDao;

	@BeforeEach
	public void beforeEach() {
		
		entityManager = JPAUtil.getEntityManager();
		usuarioDao = new UsuarioDao(entityManager);
		entityManager.getTransaction().begin();
		
	}

	@AfterEach
	public void afterEach() {
		
		entityManager.getTransaction().rollback();
		
	}

	@Test
	void deveriaEncontrarUsuarioCadastrado() {

		Usuario usuario = criarUsuario();
		usuario = usuarioDao.buscarPorUsername(usuario.getNome());
		assertNotNull(usuario);
		
	}

	@Test
	public void naoDeveriaEncontrarUsuarioNaoCadastrado() {
		
		criarUsuario();
		assertThrows(NoResultException.class,() -> usuarioDao.buscarPorUsername("fulano"));
		
	}
	
	@Test
	public void deveriaRemoverUmUsuario() {
		
		Usuario usuario = criarUsuario();
		usuarioDao.deletar(usuario);
		assertThrows(NoResultException.class,() -> usuarioDao.buscarPorUsername(usuario.getNome()));
		
	}

	public Usuario criarUsuario() {
		
		Usuario usuario = new Usuario("alice", "alice@gmail.com", "123456");
		entityManager.persist(usuario);
		
		return usuario;
	}

}
