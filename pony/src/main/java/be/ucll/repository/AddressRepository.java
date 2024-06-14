package be.ucll.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import be.ucll.model.Address;

@Repository
public class AddressRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AddressRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Address> allAddresses(){
        return jdbcTemplate.query("SELECT * FROM my_addresses", new AddressRowMapper());
    }

    public Address save(Address address){
        jdbcTemplate.update("INSERT INTO MY_ADDRESSES(STREET, NUMBER, PLACE) VALUES (?,?,?)", address.getStreet(), address.getNumber(), address.getPlace());
        return getAddress(address.getStreet(), address.getNumber());
    }
    
    public Address getAddress(String street, int number){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM my_addresses WHERE street=? AND number=?", new AddressRowMapper(), street, number);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Address getAddressById(Long addressId){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM my_addresses WHERE ID = ?", new AddressRowMapper(), addressId);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public boolean addressExists(String street, int number){
        List<Address> result = jdbcTemplate.query("SELECT * FROM my_addresses WHERE street=? AND number=?", new AddressRowMapper(), street, number);
        return result.size()>0;
    }
}
