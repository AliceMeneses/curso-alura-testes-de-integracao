package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.UsuarioBuilder;

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

		Usuario usuario = new UsuarioBuilder()
				.comNome("alice")
				.comEmail("alice@gmail.com")
				.comSenha("123456")
				.criar();
		
		entityManager.persist(usuario);
		
		usuario = usuarioDao.buscarPorUsername(usuario.getNome());
		assertNotNull(usuario);
		
	}

	@Test
	public void naoDeveriaEncontrarUsuarioNaoCadastrado() {
		
		Usuario usuario = new UsuarioBuilder()
				.comNome("alice")
				.comEmail("alice@gmail.com")
				.comSenha("123456")
				.criar();
		
		entityManager.persist(usuario);
		
		assertThrows(NoResultException.class,() -> usuarioDao.buscarPorUsername("fulano"));
		
	}
	
	@Test
	public void deveriaRemoverUmUsuario() {
		
		Usuario usuario = new UsuarioBuilder()
				.comNome("alice")
				.comEmail("alice@gmail.com")
				.comSenha("123456")
				.criar();
		
		entityManager.persist(usuario);
		
		usuarioDao.deletar(usuario);
		assertThrows(NoResultException.class,() -> usuarioDao.buscarPorUsername(usuario.getNome()));
		
	}

}
