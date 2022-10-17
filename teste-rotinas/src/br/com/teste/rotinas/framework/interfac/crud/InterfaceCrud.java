package br.com.teste.rotinas.framework.interfac.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public interface InterfaceCrud<T> extends Serializable{
	
	//salva dados de objeto que salva no banco de dados
	void save(T obj) throws Exception;
	
	void persist(T obj) throws Exception;
	
	//salva ou atualiza de objeto que salva no banco de dados
	void saveOrUpdate(T obj) throws Exception;
	
	//atualiza de objeto que salva no banco de dados
	void update(T obj) throws Exception;
	
	//deleta objeto que salva no banco de dados
	void delete(T obj) throws Exception;
	
	//salva ou atualiza retorna os daddos do objeto
	T merge(T obj) throws Exception;
	
	//retorna lista de dados de determinada classe(entidade)
	List<T> findList(Class<T> entidade) throws Exception;
	
	//retorna dados de determinada classe(entidade)
	Object findById(Class<T> entidade, Long id) throws Exception;	 
	
	//retorna dados de determinada classe(entidade) por ID
	T findByPorId(Class<T> entidade, Long id) throws Exception;	 
	
	//retorna lista de dados de determinada Query passada por HQL do hibernate ou JPA
	List<T> findListQueryDinamica(String s) throws Exception;
	
	//executa update de determinada Query passada por HQL do hibernate ou JPA
	void executeUpdateQueryDinamica(String s) throws Exception;
	
	//executa update de determinado SQL dinamico
    void executeUpdateSQLDinamica(String sql) throws Exception;
	
    //limpa a session do hibernate
    void clearSession() throws Exception;
	
    //retira objeto da sessao do hibernate, manter consistencia do banco de dados
    void evict(Object obj) throws Exception;
	
    //retorna session do hibernate
    Session getSession() throws Exception;
    
    //retorna lista de dados de determinada SQL dinamico
    List<?> getListSqlDinamica(String sql) throws Exception;	
    
    //retorna implementações do spring JDBC
    JdbcTemplate getJdbcTemplate();
    SimpleJdbcTemplate getSimpleJdbcTemplate();
    SimpleJdbcInsert getInserJdbcInsert();
    SimpleJdbcCall getSimleJdbcClass();
    
    //retorna quantidade de registros em uma tabela 
    Long totalRegistros(String tabela) throws Exception;
    
    //consultar resultado dinamico SQL ou Query de dados
    Query obterQuery(String query) throws Exception;
    
    //retorna lista de objetos com SQL
    List<Object[]> getListBySqlDinamicaArray(String sql) throws Exception;	
    
    //retorna lista para carregamento dinamico com JSF e Primefaces
    List<T> findListByQueryDinamica(String query, int regInicial, int maxResultado) throws Exception;	
    
	

}
