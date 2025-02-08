package br.com.joaogcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import br.com.joaogcm.entity.Cliente;
import br.com.joaogcm.exceptions.DatabaseException;

@Repository
public class ClienteDAO {

	private final DataSource dataSource;

	public ClienteDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean criarCliente(Cliente cliente) {
		String sql = "INSERT INTO cliente (nome, email, telefone, cpf, senha) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, cliente.getTelefone());
			ps.setString(4, cliente.getCpf());
			ps.setString(5, cliente.getSenha());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível criar o cliente: ", e);
		}
	}

	public boolean atualizarClientePorCodigo(Cliente cliente) {
		String sql = "UPDATE cliente SET nome = ?, email = ?, telefone = ?, senha = ? WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, cliente.getTelefone());
			ps.setString(4, cliente.getSenha());
			ps.setInt(5, cliente.getCodigo());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível atualizar o cliente de código " + cliente.getCodigo(), e);
		}
	}

	public boolean removerClientePorCodigo(Integer codigo) {
		String sql = "DELETE FROM cliente WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, codigo);

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível remover o cliente de código " + codigo, e);
		}
	}

	public Set<Cliente> buscarTodosClientes() {
		Set<Cliente> clientes = new LinkedHashSet<Cliente>();

		String sql = "SELECT * FROM cliente";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					clientes.add(mapearCliente(rs));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível buscar todos os clientes", e);
		}

		return clientes;
	}

	public Optional<Cliente> buscarClientePorCodigo(Integer codigo) {
		String sql = "SELECT * FROM cliente WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, codigo);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return Optional.of(mapearCliente(rs));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível buscar o cliente pelo código " + codigo, e);
		}

		return Optional.empty();
	}

	private Cliente mapearCliente(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setCodigo(rs.getInt("CODIGO"));
		cliente.setNome(rs.getString("NOME"));
		cliente.setEmail(rs.getString("EMAIL"));
		cliente.setTelefone(rs.getString("TELEFONE"));
		cliente.setCpf(rs.getString("CPF"));

		return cliente;
	}
}