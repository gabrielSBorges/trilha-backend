package br.com.coldigogeladeiras.modelo;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonObject;
import br.com.coldigogeladeiras.modelo.Marca;
import br.com.coldigogeladeiras.modelo.Produto;

public class Retorno {
	private String status;
	private String message;
	private Marca marca;
	private Produto produto;
	private List<Marca> listMarcas = new ArrayList<Marca>();
	private List<Produto> listProdutos = new ArrayList<Produto>();
	private List<JsonObject> listJson = new ArrayList<JsonObject>();
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Marca getMarca() {
		return this.marca;
	}
	
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	public Produto getProduto() {
		return this.produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public List<Marca> getListMarcas() {
		return this.listMarcas;
	}
	
	public void setListMarcas(List<Marca> listMarcas) {
		this.listMarcas = listMarcas;
	}
	
	public List<Produto> getListProdutos() {
		return this.listProdutos;
	}
	
	public void setListProdutos(List<Produto> listProdutos) {
		this.listProdutos = listProdutos;
	}
	
	public List<JsonObject> getListJson() {
		return this.listJson;
	}
	
	public void setListJson(List<JsonObject> listJson) {
		this.listJson = listJson;
	}
}
