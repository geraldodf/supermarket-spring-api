package br.com.supermarket.resources;


import br.com.supermarket.models.Address;
import br.com.supermarket.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/addresses")
@RestController
public class AddressResource {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ArrayList<Address> getAll() {
        return addressService.getAll();
    }

    @GetMapping("/{id}")
    public Address getById(@PathVariable("id") Long id) {
        return addressService.getById(id);
    }

    @GetMapping("/page")
    public Page<Address> pagedSearch(Pageable pageable){
        return addressService.pagedSearch(pageable);
    }

    @PostMapping
    public Address create(@RequestBody Address address) {
        return addressService.create(address);
    }

    @PutMapping("/{id}")
    public Address update(@PathVariable("id") Long id, @RequestBody Address address) {
        return addressService.update(id, address);
    }

    @DeleteMapping("/{id}")
    public void update(@PathVariable("id") Long id) {
        addressService.delete(id);
    }

}
