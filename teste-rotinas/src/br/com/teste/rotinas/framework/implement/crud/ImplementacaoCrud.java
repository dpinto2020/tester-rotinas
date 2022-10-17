package br.com.teste.rotinas.framework.implement.crud;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.teste.rotinas.framework.hibernate.session.HibernateUtil;
import br.com.teste.rotinas.framework.interfac.crud.InterfaceCrud;

@Component
@Transactional
public class ImplementacaoCrud<T> implements InterfaceCrud<T>{

	private static final long serialVersionUID = 1L;

	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	@Autowired
	private JdbcTemplateImpl jdbcTemplate;
	
	@Autowired
	private SimpleJdbcTemplateImpl simpleJdbcTemplate;
	
	@Autowired
	private SimpleJdbcInsertImpl simpleJdbcInsertImpl;
	
	@Autowired
	private SimpleJdbcClassImpl simleJdbcClassImpl;
	
	
	
	@Override
	public void save(T obj) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().save(obj);
		executeFlushSession();
		
	}

	@Override
	public void persist(T obj) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().persist(obj);
		executeFlushSession();
	}

	@Override
	public void saveOrUpdate(T obj) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().saveOrUpdate(obj);
		executeFlushSession();
		
	}

	@Override
	public void update(T obj) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().update(obj);
		executeFlushSession();
		
	}

	@Override
	public void delete(T obj) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().delete(obj);
		executeFlushSession();
		
	}

	@Override
	public T merge(T obj) throws Exception {
		validarSessionFactory();
		obj = (T) sessionFactory.getCurrentSession().merge(obj);
		executeFlushSession();
		return obj;
	}

	@Override
	public List<T> findList(Class<T> entidade) throws Exception {
		validarSessionFactory();
		
		StringBuilder query = new StringBuilder();
		query.append("select distinct(entity) from").append(entidade.getSimpleName()).append("entity");
		
		List<T> lista = sessionFactory.getCurrentSession().createQuery(query.toString()).list(); 
				
		return lista;
		
	}

	@Override
	public Object findById(Class<T> entidade, Long id) throws Exception {
		validarSessionFactory();
		Object obj = sessionFactory.getCurrentSession().load(getClass(), id);
		return obj;
	}

	@Override
	public T findByPorId(Class<T> entidade, Long id) throws Exception {
		
		validarSessionFactory();
		T obj = (T) sessionFactory.getCurrentSession().load(getClass(), id);
		return obj;
	}

	@Override
	public List<T> findListQueryDinamica(String s) throws Exception {
		validarSessionFactory();
		List<T> lista = new ArrayList<T>();
		lista =  sessionFactory.getCurrentSession().createQuery(s).list();
		return lista;
	}

	@Override
	public void executeUpdateQueryDinamica(String s) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().createQuery(s).executeUpdate();
		executeFlushSession();
	
		
	}

	@Override
	public void executeUpdateSQLDinamica(String sql) throws Exception {
		validarSessionFactory();
		//executa uma instrução SQL normal
		sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
		executeFlushSession();
		
	}

	@Override
	public void clearSession() throws Exception {
		sessionFactory.getCurrentSession().clear();
		
	}

	@Override
	public void evict(Object obj) throws Exception {
		validarSessionFactory();
		sessionFactory.getCurrentSession().evict(obj);
		
	}

	@Override
	public Session getSession() throws Exception {
		validarSessionFactory();
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<?> getListSqlDinamica(String sql) throws Exception {
		validarSessionFactory();
		List<?> lista = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return lista;
		
	}

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	@Override
	public SimpleJdbcInsert getInserJdbcInsert() {
		return simpleJdbcInsertImpl;
	}

	@Override
	public SimpleJdbcCall getSimleJdbcClass() {
		return simleJdbcClassImpl;
	}
	
	@Override
	public Long totalRegistros(String tabela) throws Exception {
		 //não precisa de validar, pois está usando conexão direta com JDBC utilizando JdbcTemplate Spring ao invés do hibernate
		 StringBuilder sql = new StringBuilder();
		 sql.append("select count(1) from ").append(tabela);
		 
		return jdbcTemplate.queryForLong(sql.toString());
	}

	@Override
	public Query obterQuery(String query) throws Exception {
		validarSessionFactory();
		Query queryQ = sessionFactory.getCurrentSession().createQuery(query.toString());
		return queryQ;
	}

	@Override
	public List<Object[]> getListBySqlDinamicaArray(String sql) throws Exception {
		validarSessionFactory();
		List<Object[]> lista = (List<Object[]>) sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return lista;
		
	}
	
	@Override
	public List<T> findListByQueryDinamica(String query, int regInicial,
			int maxResultado) throws Exception {
		
		validarSessionFactory();
		List<T> lista = new ArrayList<T>();
		lista =  sessionFactory.getCurrentSession().createQuery(query).setFirstResult(regInicial).setMaxResults(maxResultado).list();
		return lista;
		
	}

	private void validarSessionFactory(){
		if(sessionFactory == null){
			sessionFactory = HibernateUtil.getSessionFactory();
		}
		validarTransaction();
	}
	
	private void validarTransaction(){
		
		if(!sessionFactory.getCurrentSession().getTransaction().isActive()){
			sessionFactory.getCurrentSession().beginTransaction();
		}
	}
	
	private void commitProcessoAjax(){
		sessionFactory.getCurrentSession().beginTransaction().commit();
	}
	
	private void rollbackProcessoAjax(){
		sessionFactory.getCurrentSession().beginTransaction().rollback();
	}
	
	//roda instantaneamente SQL no banco de dados
	private void executeFlushSession(){
		sessionFactory.getCurrentSession().flush();
	}
}
