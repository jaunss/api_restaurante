package br.com.joaogcm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import br.com.joaogcm.entity.Lanche;
import br.com.joaogcm.exceptions.DatabaseException;

public class LancheDAO {

	private final DataSource dataSource;

	public LancheDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean criarLanche(Lanche lanche) {
		String sql = "INSERT INTO lanche (nome, descricao_conteudo, preco) VALUES (?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, lanche.getNome());
			ps.setString(2, lanche.getDescricao_conteudo());
			ps.setBigDecimal(3, lanche.getPreco());
			ps.setInt(4, lanche.getCodigo());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível criar o Lanche: ", e);
		}
	}

	public boolean atualizarLanchePorCodigo(Lanche lanche) {
		String sql = "UPDATE lanche nome = ?, descricao_conteudo = ?, preco = ? WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, lanche.getNome());
			ps.setString(2, lanche.getDescricao_conteudo());
			ps.setBigDecimal(3, lanche.getPreco());
			ps.setInt(4, lanche.getCodigo());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível atualizar o lanche de código " + lanche.getCodigo(), e);
		}
	}

	public boolean removerLanchePorCodigo(Integer codigo) {
		String sql = "DELETE FROM lanche WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, codigo);

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível remover o Lanche de código " + codigo, e);
		}
	}

	public Set<Lanche> buscarTodosLanches() {
		Set<Lanche> lanches = new LinkedHashSet<Lanche>();

		String sql = "SELECT * FROM lanche";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					lanches.add(mapearLanche(rs));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível buscar todos os Lanches", e);
		}

		return lanches;
	}

	public Optional<Lanche> buscarLanchePorCodigo(Integer codigo) {
		String sql = "SELECT * FROM lanche WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, codigo);

			try (ResultSet rs = ps.executeQuery()) {
				Optional.of(mapearLanche(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível buscar o Lanche de código " + codigo, e);
		}

		return Optional.empty();
	}

	private Lanche mapearLanche(ResultSet rs) throws SQLException {
		Lanche lanche = new Lanche();
		lanche.setCodigo(rs.getInt("CODIGO"));
		lanche.setNome(rs.getString("NOME"));
		lanche.setDescricao_conteudo(rs.getString("DESCRICAO_CONTEUDO"));
		lanche.setPreco(rs.getBigDecimal("PRECO"));

		return lanche;
	}
}