package br.com.coldigogeladeiras.jdbcinterface;

import br.com.coldigogeladeiras.modelo.Marca;
import br.com.coldigogeladeiras.modelo.Retorno;


public interface MarcaDAO {
	public Retorno buscar();
	public Retorno buscarPorNome(String nome);
	public Retorno inserir(Marca marca);
	public Retorno deletar(int id);
	public Retorno buscarPorId(int id);
	public Retorno alterar(Marca produto);
	public Retorno alterarStatus(int id, int status);
}
