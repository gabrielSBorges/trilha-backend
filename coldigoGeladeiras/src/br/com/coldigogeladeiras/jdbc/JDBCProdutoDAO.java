package br.com.coldigogeladeiras.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import br.com.coldigogeladeiras.jdbcinterface.ProdutoDAO;
import br.com.coldigogeladeiras.modelo.Produto;

public class JDBCProdutoDAO implements ProdutoDAO {
	private Connection conexao;
	private JsonObject produto;
	
	public JDBCProdutoDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	public boolean inserir(Produto produto) {
		String comando = "INSERT INTO produtos "
		+ "(categoria, modelo, capacidade, valor, marcas_id)"
		+ "VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			
			p.setInt(1, produto.getCategoria());
			p.setString(2, produto.getModelo());
			p.setInt(3, produto.getCapacidade());
			p.setFloat(4, produto.getValor());
			p.setInt(5, produto.getMarcaId());
			
			p.execute();
			
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
			
			return false;
		}
	}
	
	public List<JsonObject> buscarPorNome(String nome) {
		String comando = "SELECT produtos.*, marcas.nome as marca FROM produtos INNER JOIN marcas ON produtos.marcas_id = marcas.id ";
		
		if (!nome.contentEquals("")) {
			comando += "WHERE modelo LIKE '%" + nome + "%' ";
		}
		
		comando += "ORDER BY categoria ASC, marcas.nome ASC, modelo ASC";
		
		List<JsonObject> listaProdutos = new ArrayList<JsonObject>();
		produto = null;
		
		try {
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String categoria = rs.getString("categoria");
				String modelo = rs.getString("modelo");
				int capacidade = rs.getInt("capacidade");
				float valor = rs.getFloat("valor");
				String marcaNome = rs.getString("marca");
				
				if (categoria.contentEquals("1")) {
					categoria = "Geladeira";
				} else if (categoria.contentEquals("2")) {
					categoria = "Freezer";
				}
				
				produto = new JsonObject();
				produto.addProperty("id", id);
				produto.addProperty("categoria", categoria);
				produto.addProperty("modelo", modelo);
				produto.addProperty("capacidade", capacidade);
				produto.addProperty("valor", valor);
				produto.addProperty("marcaNome", marcaNome);
				
				listaProdutos.add(produto);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return listaProdutos;
	}
	
	public boolean deletar(int id) {
		String comando = "DELETE FROM produtos WHERE id = ?";
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, id);
			p.execute();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public Produto buscarPorId(int prodId) {
		String comando = "select * from produtos where produtos.id = ?";
		Produto produto = new Produto();
		
		try {
			PreparedStatement p = this.conexao.prepareStatement(comando);
			p.setInt(1, prodId);
			ResultSet rs = p.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				int categoria = rs.getInt("categoria");
				String modelo = rs.getString("modelo");
				int capacidade = rs.getInt("capacidade");
				float valor = rs.getFloat("valor");
				int marcaId = rs.getInt("marcas_id");

				produto.setId(id);
				produto.setCategoria(categoria);
				produto.setMarcaId(marcaId);
				produto.setModelo(modelo);
				produto.setCapacidade(capacidade);
				produto.setValor(valor);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return produto;
	}
	
	public boolean alterar(Produto produto) {
		String comando = "UPDATE produtos SET categoria=?, modelo=?, capacidade=?, valor=?, marcas_id=? WHERE id=?";
		PreparedStatement p;
		
		try {
			p = this.conexao.prepareStatement(comando);
			p.setInt(1, produto.getCategoria());
			p.setString(2, produto.getModelo());
			p.setInt(3, produto.getCapacidade());
			p.setFloat(4, produto.getValor());
			p.setInt(5, produto.getMarcaId());
			p.setInt(6, produto.getId());
			p.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
