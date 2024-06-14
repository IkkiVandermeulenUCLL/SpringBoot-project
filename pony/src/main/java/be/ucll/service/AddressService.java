package be.ucll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.ucll.model.Address;
import be.ucll.repository.AddressRepository;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    
    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public List<Address> getAddresses(){
        return addressRepository.allAddresses();
    }
    public Address getAddress(Long addressId){
        return addressRepository.getAddressById(addressId);
    }


    public Address addAddress(Address address){
        if (addressRepository.addressExists(address.getStreet(), address.getNumber())){
            throw new ServiceException("Address already in database");
        }else{
            return addressRepository.save(address);
        }
    }

}
