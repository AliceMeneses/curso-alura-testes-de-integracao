package br.com.alura.leilao.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class LeilaoDaoTest {

	private EntityManager entityManager;
	private LeilaoDao leilaoDao;

	@BeforeEach
	public void beforeEach() {
		
		entityManager = JPAUtil.getEntityManager();
		leilaoDao = new LeilaoDao(entityManager);
		entityManager.getTransaction().begin();
		
	}

	@AfterEach
	public void afterEach() {
		
		entityManager.getTransaction().rollback();
		
	}

	@Test
	void deveriaCadastrarUmLeilao() {

		Usuario usuario = criarUsuario();
		Leilao leilao = new Leilao("mochila", new BigDecimal("50"), LocalDate.now(), usuario);
		
		leilao = leilaoDao.salvar(leilao);
		
		leilao = leilaoDao.buscarPorId(leilao.getId());
		assertNotNull(leilao);
		
	}
	
	@Test
	void deveriaAtualizarrUmLeilao() {

		Usuario usuario = criarUsuario();
		Leilao leilao = new Leilao("mochila", new BigDecimal("50"), LocalDate.now(), usuario);
		
		leilao = leilaoDao.salvar(leilao);
		
		leilao.setNome("Celular");
		leilao.setValorInicial(new BigDecimal("4000"));
		
		leilao = leilaoDao.salvar(leilao);
		
		leilao = leilaoDao.buscarPorId(leilao.getId());
		
		assertEquals("Celular", leilao.getNome());
		assertEquals(new BigDecimal("4000"), leilao.getValorInicial());
		
	}

	public Usuario criarUsuario() {
		
		Usuario usuario = new Usuario("alice", "alice@gmail.com", "123456");
		entityManager.persist(usuario);
		
		return usuario;
	}

}
