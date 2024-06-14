package be.ucll.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import be.ucll.model.Address;
import be.ucll.model.DomainException;
import be.ucll.model.Stable;
import be.ucll.service.AddressService;
import be.ucll.service.ServiceException;
import be.ucll.service.StableService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/address")
public class AddressRestController {
    @Autowired
    private AddressService addressService; 
    private StableService stableService;
    
    public AddressRestController(AddressService addressService, StableService stableService){
        this.addressService = addressService;
        this.stableService = stableService;
    }

    @GetMapping()
    public List<Address> getAddresses() {
        return addressService.getAddresses();
    }  

    @PostMapping()
    public Address AddAddress(@RequestBody @Valid Address address) {
        return addressService.addAddress(address);    
    }
    
    @PostMapping("/stable")
    public Stable addStableAndAddress(@RequestBody @Valid Stable stable) {
        return stableService.addStableAndAddress(stable);
    }
    
    @PostMapping("/{addressId}/{stableId}")
    public Stable addAddressToStable(@PathVariable Long addressId, @PathVariable Long stableId) {
        return stableService.addAddressToStable(addressId, stableId);
    }
    
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    public Map<String,String> handleValidationException(ConstraintViolationException e){
        Map<String,String> response = new HashMap<>();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> error : violations){
            response.put("ValidationException", error.getMessageTemplate());
        }
        return response;
    }    

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DomainException.class})
    public Map<String, String> handleDomainException(DomainException e){
        Map<String, String> response = new HashMap<>();
        response.put("DomainException", e.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HandlerMethodValidationException.class})
    public Map<String, String> handleMethodValidationException(HandlerMethodValidationException e){
        Map<String, String> response = new HashMap<>();
        response.put("MethodValidationException", e.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class})
    public Map<String, String> handleServiceException(ServiceException e){
        Map<String, String> response = new HashMap<>();
        response.put("ServiceException", e.getMessage());
        return response;
    }
}
