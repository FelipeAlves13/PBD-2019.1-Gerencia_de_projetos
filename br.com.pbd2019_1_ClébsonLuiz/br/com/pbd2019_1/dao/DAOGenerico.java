package br.com.pbd2019_1.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.pbd2019_1.entidade.Entidade;
import br.com.pbd2019_1.exception.DAOException;

public abstract class DAOGenerico <T extends Entidade> {

	private EntityManagerFactory entityManagerFactory;
	
	public EntityManager createEntityManager(){
		entityManagerFactory = Persistence.createEntityManagerFactory("banco");
		return entityManagerFactory.createEntityManager();
	}
	
	public T inserir(T t) throws DAOException{
		EntityManager entityManager = createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(t);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			
			entityManager.getTransaction().rollback();
			
			throw new DAOException("Erro de inser��o no banco de dados");
		} finally {
			entityManager.close();
		}
		return t;
	}

	public T atualizar(T t)throws DAOException{
		EntityManager entityManager = createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(t);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			
			entityManager.getTransaction().rollback();
			
			throw new DAOException("Erro de atualiza��o no banco de dados");
		} finally {
			entityManager.close();
		}
		return t;
	}

	public T buscar(Class<T> classe, int id) throws DAOException{
		EntityManager entityManager = createEntityManager();
		T t = null;
		try {
		
		t = entityManager.find(classe, id);
		
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new DAOException("Erro de busca no banco de dados");
		} finally {
			entityManager.close();
		}
		return t;
	}

	public void deletar(T t) throws DAOException{
		EntityManager entityManager = createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(t);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			
			e.printStackTrace();
			
			entityManager.getTransaction().rollback();
			
			throw new DAOException("Erro de remo��o no banco de dados");
			
		} finally {
			entityManager.close();
		}
	}
	
	public T buscaSQLGenerica(Class<T> classe, String sql) throws DAOException{
		EntityManager entityManager = createEntityManager();
		T t = null;
		try {
			t = entityManager.createQuery(sql, classe).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new DAOException("Erro de buscaSQL no banco de dados");
		} finally {
			entityManager.close();
		}
		return t;
	}
	
	public List<T> buscaListaSQLGenerica(Class<T> classe, String sql) throws DAOException{
		EntityManager entityManager = createEntityManager();
		List<T> t = null;
		try {
			t = entityManager.createQuery(sql, classe).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new DAOException("Erro de busca lista SQL no banco de dados");
		} finally {
			entityManager.close();
		}
		return t;
	}
	
}
