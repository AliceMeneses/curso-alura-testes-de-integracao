package br.com.alura.leilao.dao;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {

	@Test
	void deveriaBuscarUsuarioPorUsername() {
		EntityManager entityManager = JPAUtil.getEntityManager();
		UsuarioDao usuarioDao = new UsuarioDao(entityManager);
		
		
		Usuario usuario = new Usuario("alice", "alice@gmail.com", "123456");
		
		entityManager.getTransaction().begin();
		entityManager.persist(usuario);
		entityManager.getTransaction().commit();

		usuario = usuarioDao.buscarPorUsername(usuario.getNome());
		assertNotNull(usuario);
	}

}
