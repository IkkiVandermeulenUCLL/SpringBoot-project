package be.ucll.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import be.ucll.model.Address;

public class AddressRowMapper implements RowMapper<Address>{
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Address(rs.getLong("ID"), rs.getString("STREET"), rs.getInt("NUMBER"), rs.getString("PLACE"));
    }
}
