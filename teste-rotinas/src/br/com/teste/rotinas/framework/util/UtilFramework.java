package br.com.teste.rotinas.framework.util;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class UtilFramework implements Serializable{
//classe responsável por saber o usuário que está excluindo, cadastrando ou atualizando dados no sistema.
	
	private static final long serialVersionUID = 1L;
	
	//carrega código de usuário, responsável por verificar os usuários que estão cadastrando, excluindo, atualizando dados
	private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();
	
	/*método sincronizado para evitar concorrência entre as threads, a ação de cada usuário irá ter uma espera para poder acessar o método
	enquanto o outro não finalzar o processo nesse método. Ex: enquanto um usuário estiver gravando um produto, o outro irá entrar 
	na fila para acessar esse método.*/
	public synchronized static ThreadLocal<Long> getThreadLocal(){
		return threadLocal;
	}

}
