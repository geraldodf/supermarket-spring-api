package br.com.supermarket.services;

import br.com.supermarket.models.Address;
import br.com.supermarket.repositories.AddresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AddressService {

    @Autowired
    private AddresRepository addresRepository;

    public ArrayList<Address> getAll() {
        return (ArrayList<Address>) addresRepository.findAll();
    }

    public Address getById(Long id) {
        return addresRepository.findById(id).get();
    }

    public Address create(Address address) {
        return addresRepository.save(address);
    }

    public Address update(Long id, Address address) {
        Address addressToUpdate = getById(id);

        addressToUpdate.setCep(address.getCep());
        addressToUpdate.setComplement(address.getComplement());
        addressToUpdate.setCity(address.getCity());
        addressToUpdate.setNeighborhood(address.getNeighborhood());
        addressToUpdate.setNumber(address.getNumber());
        addressToUpdate.setStreetName(address.getStreetName());
        addressToUpdate.setReferencePoint(address.getReferencePoint());

        return addresRepository.save(addressToUpdate);
    }

    public void delete(Long id) {
        addresRepository.deleteById(id);
    }
}
