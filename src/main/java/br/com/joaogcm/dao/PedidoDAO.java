package br.com.joaogcm.dao;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import br.com.joaogcm.entity.Cliente;
import br.com.joaogcm.entity.Pedido;
import br.com.joaogcm.exceptions.DatabaseException;

public class PedidoDAO {

	private final DataSource dataSource;

	public PedidoDAO(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean criarPedido(Pedido pedido) {
		String sql = "INSERT INTO pedido (data_pedido, total, cliente_id) VALUES (?, ?, ?)";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setTimestamp(1, Timestamp.valueOf(pedido.getDataHoraPedido()));
			ps.setBigDecimal(2, pedido.getTotal().setScale(2, RoundingMode.HALF_EVEN));
			ps.setInt(3, pedido.getCliente().getCodigo());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível criar o pedido ", e);
		}
	}

	public boolean atualizarPedidoPorCodigo(Pedido pedido) {
		String sql = "UPDATE pedido SET data_pedido = ?, total = ?, cliente_id = ? WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setTimestamp(1, Timestamp.valueOf(pedido.getDataHoraPedido()));
			ps.setBigDecimal(2, pedido.getTotal().setScale(2, RoundingMode.HALF_EVEN));
			ps.setInt(3, pedido.getCliente().getCodigo());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível atualizar o pedido de código " + pedido.getCodigo(), e);
		}
	}

	public boolean removerPedidoPorCodigo(Integer codigo) {
		String sql = "DELETE FROM pedido WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, codigo);

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível remover o pedido de código " + codigo, e);
		}
	}

	public Set<Pedido> buscarTodosPedidos() {
		Set<Pedido> pedidos = new LinkedHashSet<Pedido>();

		String sql = "SELECT * FROM pedido";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					pedidos.add(mapearPedido(rs));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível buscar todos os pedidos", e);
		}

		return pedidos;
	}

	public Optional<Pedido> buscarPedidoPorCodigo(Integer codigo) {
		String sql = "SELECT * FROM pedido WHERE codigo = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, codigo);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					return Optional.of(mapearPedido(rs));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Não foi possível buscar o pedido pelo código " + codigo, e);
		}

		return Optional.empty();
	}

	public Pedido mapearPedido(ResultSet rs) throws SQLException {
		Pedido pedido = new Pedido();
		pedido.setCodigo(rs.getInt("CODIGO"));
		pedido.setDataHoraPedido(rs.getTimestamp("DATA_PEDIDO").toLocalDateTime());
		pedido.setTotal(rs.getBigDecimal("TOTAL"));

		Cliente cliente = new Cliente();
		cliente.setCodigo(rs.getInt("CLIENTE_ID"));

		pedido.setCliente(cliente);

		return pedido;
	}
}