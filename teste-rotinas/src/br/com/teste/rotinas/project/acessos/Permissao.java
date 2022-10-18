package br.com.teste.rotinas.project.acessos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public enum Permissao {

	ADMIN ("ADMIN","Adminitrador"),
	USER ("USER","Usuario - Padrão"),
	
	CADASTRAR_ACESSOS ("CADASTRAR_ACESSOS","Cadastrar - Acessos"),
	EDITAR_ACESSOS ("EDITAR_ACESSOS","Editar - Acessos"),
	VISUALIZAR_ACESSOS ("VISUALIZAR_ACESSOS", "Visualizar - Acessos"),
	EXCLUIR_ACESSOS ("EXCLUIR_ACESSOS", "Excluir - Acessos");
	
	private String valor = "";
	private String descricao = "";
	
	private Permissao(String val, String desc) {
		this.valor = val;
		this.descricao = desc;
	}
	
	private Permissao() {
		
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getValor() {
		return valor;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		
		return getValor();
	}
	
	public static List<Permissao> getLista(){
		List<Permissao> permi = new ArrayList<Permissao>();
		
		for(Permissao permiss : Permissao.values()){
			permi.add(permiss);
		}
		
		Collections.sort(permi, new Comparator<Permissao>(){

			@Override
			public int compare(Permissao o1, Permissao o2) {
				
				return new Integer(o1.ordinal()).compareTo(new Integer(o2.ordinal()));
			}
			
			
		});
		
		return permi;
	}
	
}
