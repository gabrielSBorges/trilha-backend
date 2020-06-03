package br.com.coldigogeladeiras.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.coldigogeladeiras.jdbcinterface.MarcaDAO;
import br.com.coldigogeladeiras.modelo.Marca;
import br.com.coldigogeladeiras.modelo.Retorno;

public class JDBCMarcaDAO implements MarcaDAO {
	private Connection conexao;
	private JsonObject marca;
	
	public JDBCMarcaDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public Retorno buscarPorNome(String marcaNome) {
		Retorno retorno = new Retorno();
		String comando = "SELECT * FROM marcas ";
		
		if (!marcaNome.contentEquals("")) {
			comando += "WHERE nome LIKE '%" + marcaNome + "%' ";
		}
		
		comando += "ORDER BY nome ASC";
		
		List<JsonObject> listaMarcas = new ArrayList<JsonObject>();
		marca = null;
		
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				int status = rs.getInt("status");
				
				
				marca = new JsonObject();
				marca.addProperty("id", id);
				marca.addProperty("nome", nome);
				marca.addProperty("status", status);
				
				listaMarcas.add(marca);
				
			}
			
			retorno.setStatus("sucesso");
			retorno.setListJson(listaMarcas);
		} catch(Exception e) {
			e.printStackTrace();
			
			retorno.setStatus("erro");
			retorno.setMessage("Ocorreu um erro ao listar as marcas! \n Erro: \n" + e.getMessage());
		}
		
		
		return retorno;
	}

	public Retorno buscar() {
		Retorno retorno = new Retorno();
		String comando = "SELECT * FROM marcas WHERE status=1";
		
		List<Marca> listMarcas = new ArrayList<Marca>();
		
		Marca marca = null;
		
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			
			while (rs.next()) {
				marca = new Marca();
				
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				int status = rs.getInt("status");
				
				marca.setId(id);
				marca.setNome(nome);
				marca.setStatus(status);
				
				listMarcas.add(marca);
				
			}
			
			retorno.setStatus("sucesso");
			retorno.setListMarcas(listMarcas);
		} catch(Exception e) {
			e.printStackTrace();
			
			retorno.setStatus("erro");
			retorno.setMessage("Ocorreu um erro ao listar as marcas! \n Erro: \n" + e.getMessage());
		}
		
		return retorno;
	}
	
	public Retorno buscarPorId(int marcaId) {
		Retorno retorno = new Retorno();
		String comando = "select * from marcas where marcas.id = ?";
		Marca marca = new Marca();
		
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, marcaId);
			ResultSet rs = p.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				int status = rs.getInt("status");

				marca.setId(id);
				marca.setNome(nome);
				marca.setStatus(status);
			}
			
			retorno.setStatus("sucesso");
			retorno.setMarca(marca);
		} catch(Exception e) {
			e.printStackTrace();
			
			retorno.setStatus("erro");
			retorno.setMessage("Ocorreu um erro ao tentar buscar a marca! \n Erro: \n" + e.getMessage());
		}
		
		return retorno;
	}
	
	public Retorno inserir(Marca marca) {
		Retorno retorno = new Retorno();
		String comando = "INSERT INTO marcas (nome) VALUES (?)";
		String findMarca = "SELECT id FROM marcas WHERE nome=? LIMIT 1";
		
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(findMarca);
			
			p.setString(1, marca.getNome());
			ResultSet rs = p.executeQuery();
			
			if (rs.next() == false) {
				p = this.conexao.prepareStatement(comando);
				
				p.setString(1, marca.getNome());
				
				p.execute();
				
				retorno.setStatus("sucesso");
				retorno.setMessage("Marca cadastrada com sucesso!");				
			} 
			else {
				retorno.setStatus("erro");
				retorno.setMessage("Já existe uma marca cadastrada com esse nome!");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
			retorno.setStatus("erro");
			retorno.setMessage("Ocorreu um erro ao registrar a marca! \n Erro: \n" + e.getMessage());
		}
		
		return retorno;
	}

	public Retorno deletar(int id) {
		Retorno retorno = new Retorno();
		String deleteMarca = "DELETE FROM marcas WHERE id = ?";
		String findProduto = "SELECT id FROM produtos WHERE marcas_id = ? LIMIT 1";
		PreparedStatement p;
		
		try {
			// Busca produto com essa marca id
			p = this.conexao.prepareStatement(findProduto);	
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			
			if (rs.next() == false) {
				// Exclui marca e retorna mensagem de sucesso
				p = this.conexao.prepareStatement(deleteMarca);
				p.setInt(1, id);
				p.execute();
				
				retorno.setStatus("sucesso");
				retorno.setMessage("Marca removida com sucesso!");
			}
			
			else {
				// Retorna mensagem de erro
				retorno.setStatus("erro");
				retorno.setMessage("Não é possível remover essa marca, há produtos registrados com ela!");
			}
		} catch(Exception e) {
			e.printStackTrace();
			
			retorno.setStatus("erro");
			retorno.setMessage("Ocorreu um erro ao remover a marca! \n Erro: \n" + e.getMessage());
		}
		
		return retorno;
	}

	public Retorno alterar(Marca marca) {
		Retorno retorno = new Retorno();
		String comando = "UPDATE marcas SET nome=?, status=? WHERE id=?";
		String findMarca = "SELECT id FROM marcas WHERE id=? LIMIT 1";
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(findMarca);	
			p.setInt(1, marca.getId());
			ResultSet rs = p.executeQuery();
			
			if (rs.next() == true) {
				p = this.conexao.prepareStatement(comando);
				p.setString(1, marca.getNome());
				p.setInt(2, marca.getStatus());
				p.setInt(3, marca.getId());
				p.executeUpdate();
				
				retorno.setStatus("sucesso");
				retorno.setMessage("Marca alterada com sucesso!");				
			}
			else {
				retorno.setStatus("erro");
				retorno.setMessage("Marca informada não existe!");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			
			retorno.setStatus("erro");
			retorno.setMessage("Ocorreu um erro ao alterar a marca! \n Erro: \n" + e.getMessage());
		}
		
		return retorno;
	}
	
	public Retorno alterarStatus(int id, int status) {
		Retorno retorno = new Retorno();
		String comando = "UPDATE marcas SET status=? WHERE id=?";
		String findMarca = "SELECT id FROM marcas WHERE id=? LIMIT 1";
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(findMarca);	
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			
			if (rs.next() == true) {
				if (status == 1 || status == 0) {
					p = this.conexao.prepareStatement(comando);
					p.setInt(1, status);
					p.setInt(2, id);
					p.executeUpdate();
					
					retorno.setStatus("sucesso");
					
					if (status == 1) {
						retorno.setMessage("Marca ativada com sucesso!");				
					} 
					else {
						retorno.setMessage("Marca desativada com sucesso!");				
					}				
				}
				else {
					retorno.setStatus("erro");
					retorno.setMessage("Código de status enviado é inválido!");
				}
			}
			else {
				retorno.setStatus("erro");
				retorno.setMessage("Marca informada não existe!");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			retorno.setStatus("erro");
			retorno.setMessage("Ocorreu um ao mudar o status da marca! \n Erro: \n" + e.getMessage());
		}
		
		return retorno;
	}
}
