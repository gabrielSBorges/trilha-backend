package br.com.coldigogeladeiras.rest;

import java.sql.Connection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.coldigogeladeiras.db.Conexao;
import br.com.coldigogeladeiras.jdbc.JDBCMarcaDAO;
import br.com.coldigogeladeiras.modelo.Marca;
import br.com.coldigogeladeiras.modelo.Retorno;

@Path("marca")
public class MarcaRest extends UtilRest {
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar() {
		try {
			Conexao con = new Conexao();
			Connection conexao = con.abrirConexao();
			
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			Retorno retorno = jdbcMarca.buscar();
			
			con.fecharConexao();
			
			if (retorno.getStatus() == "sucesso") {
				return this.buildResponse(retorno.getListMarcas());				
			}
			else {
				return this.buildErrorResponse(retorno.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return this.buildErrorResponse("Ocorreu um erro ao tentar as marcas! \n Erro: \n" + e.getMessage());
		}
	}
	
	@GET
	@Path("/buscar/nome")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscaPorNome(@QueryParam("valorBusca") String nome) {
		try {	
			Conexao con = new Conexao();
			Connection conexao = con.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			
			Retorno retorno = jdbcMarca.buscarPorNome(nome);
			
			con.fecharConexao();
			
			if (retorno.getStatus() == "sucesso") {
				String json = new Gson().toJson(retorno.getListJson());
				return this.buildResponse(json);				
			}
			else {
				return this.buildErrorResponse(retorno.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return this.buildErrorResponse("Ocorreu um erro ao tentar listar as marcas! \n Erro: \n" + e.getMessage());
		}
	}
	
	@GET
	@Path("/buscarPorId")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorId(@QueryParam("id") int id) {
		try {	
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
	
			Retorno retorno = jdbcMarca.buscarPorId(id);
	
			conec.fecharConexao();
	
			if (retorno.getStatus() == "sucesso") {
				return this.buildResponse(retorno.getMarca());			
			}
			else {
				return this.buildErrorResponse(retorno.getMessage());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return this.buildErrorResponse("Ocorreu um erro ao tentar buscar a marca! \n Erro: \n" + e.getMessage());
		}
	}
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String marcaParam) {
		try {	
			Marca marca = new Gson().fromJson(marcaParam, Marca.class);
			Conexao con = new Conexao();
			Connection conexao = con.abrirConexao();
			
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);			
			Retorno retorno = jdbcMarca.inserir(marca);
			
			con.fecharConexao();
	
			if (retorno.getStatus() == "sucesso") {
				return this.buildResponse(retorno.getMessage());				
			}
			else {
				return this.buildErrorResponse(retorno.getMessage());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return this.buildErrorResponse("Ocorreu um erro ao tentar cadastrar a marca! \n Erro: \n" + e.getMessage());
		}
	} 

	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id") int id) {
		try {	
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			Retorno retorno = jdbcMarca.deletar(id);
			
			conec.fecharConexao();
			
			if (retorno.getStatus() == "sucesso") {
				return this.buildResponse(retorno.getMessage());				
			}
			else {
				return this.buildErrorResponse(retorno.getMessage());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return this.buildErrorResponse("Ocorreu um erro ao tentar remover o produto! \n Erro: \n" + e.getMessage());
		}
	}

	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String marcaParam) {
		try {	
			Marca marca = new Gson().fromJson(marcaParam, Marca.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			Retorno retorno = jdbcMarca.alterar(marca);
			
			conec.fecharConexao();
			
			if (retorno.getStatus() == "sucesso") {
				return this.buildResponse(retorno.getMessage());				
			}
			else {
				return this.buildErrorResponse(retorno.getMessage());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return this.buildErrorResponse("Ocorreu um erro ao tentar alterar a marca! \n Erro: \n" + e.getMessage());
		}
	}
	
	@PUT
	@Path("/alterarStatus/{id}/{status}")
	@Consumes("application/*")
	public Response alterarStatus(@PathParam("id") int id, @PathParam("status") int status) {
		try {	
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			Retorno retorno = jdbcMarca.alterarStatus(id, status);
			
			conec.fecharConexao();
			
			if (retorno.getStatus() == "sucesso") {
				return this.buildResponse(retorno.getMessage());				
			}
			else {
				return this.buildErrorResponse(retorno.getMessage());				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return this.buildErrorResponse("Ocorreu um erro ao tentar alterar o status da marca! \n Erro: \n" + e.getMessage());
		}
	}
}
