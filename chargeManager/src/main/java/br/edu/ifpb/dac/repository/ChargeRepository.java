package br.edu.ifpb.dac.repository;

import br.edu.ifpb.dac.dto.ChargeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChargeRepository {
    private final JdbcTemplate jdbcTemplate;

    public ChargeDTO save(ChargeDTO charge) {
        String sql = "INSERT INTO charges (amount, customer_name, status) VALUES (?, ?, ?) RETURNING id";
        Long id = jdbcTemplate.queryForObject(sql, Long.class,
                charge.getAmount(),
                charge.getCustomerName(),
                charge.getStatus() != null ? charge.getStatus() : "PENDING");
        charge.setId(id);
        return charge;
    }
}
