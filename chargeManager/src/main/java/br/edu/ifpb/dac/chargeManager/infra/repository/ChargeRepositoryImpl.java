package br.edu.ifpb.dac.chargeManager.infra.repository;

import br.edu.ifpb.dac.chargeManager.business.model.Charge;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class ChargeRepositoryImpl implements ChargeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChargeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Charge> chargeRowMapper = (rs, rowNum) -> Charge.builder()
            .id(rs.getLong("id"))
            .userId(rs.getLong("user_id"))
            .name(rs.getString("name"))
            .amount(rs.getBigDecimal("amount"))
            .paymentType(rs.getString("payment_type"))
            .customer(rs.getString("customer"))
            .dueDate(rs.getString("due_date"))
            .status(rs.getString("status"))
            .externalId(rs.getString("external_id"))
            .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
            .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
            .build();

    @Override
    public Charge save(Charge charge) {
        String sql = "INSERT INTO charges (user_id, name, amount, payment_type, customer, due_date, status, external_id, created_at, updated_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setLong(1, charge.getUserId());
            ps.setString(2, charge.getName());
            ps.setBigDecimal(3, charge.getAmount());
            ps.setString(4, charge.getPaymentType());
            ps.setString(5, charge.getCustomer());
            ps.setString(6, charge.getDueDate());
            ps.setString(7, charge.getStatus());
            ps.setString(8, charge.getExternalId());
            return ps;
        }, keyHolder);

        charge.setId(keyHolder.getKey().longValue());
        return charge;
    }

    @Override
    public Optional<Charge> findById(Long id) {
        String sql = "SELECT * FROM charges WHERE id = ?";
        List<Charge> charges = jdbcTemplate.query(sql, chargeRowMapper, id);
        return charges.isEmpty() ? Optional.empty() : Optional.of(charges.get(0));
    }

    @Override
    public Optional<Charge> findByExternalId(String externalId) {
        String sql = "SELECT * FROM charges WHERE external_id = ?";
        List<Charge> charges = jdbcTemplate.query(sql, chargeRowMapper, externalId);
        return charges.isEmpty() ? Optional.empty() : Optional.of(charges.get(0));
    }

    @Override
    public List<Charge> findByUserId(Long userId) {
        String sql = "SELECT * FROM charges WHERE user_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, chargeRowMapper, userId);
    }

    @Override
    public List<Charge> findAll() {
        String sql = "SELECT * FROM charges ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, chargeRowMapper);
    }

    @Override
    public void update(Charge charge) {
        String sql = "UPDATE charges SET user_id = ?, name = ?, amount = ?, payment_type = ?, " +
                "customer = ?, due_date = ?, status = ?, external_id = ?, updated_at = NOW() WHERE id = ?";

        jdbcTemplate.update(sql,
                charge.getUserId(),
                charge.getName(),
                charge.getAmount(),
                charge.getPaymentType(),
                charge.getCustomer(),
                charge.getDueDate(),
                charge.getStatus(),
                charge.getExternalId(),
                charge.getId());
    }
}
