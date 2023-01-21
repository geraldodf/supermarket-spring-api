package br.com.supermarket.services;

import br.com.supermarket.models.Role;
import br.com.supermarket.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public ArrayList<Role> getAllRoles() {
        return (ArrayList<Role>) roleRepository.findAll();
    }

    public Role getRoleById(Long id) throws Exception {

        if (id == null)
            throw new Exception("The role is is null.");

        Optional<Role> optionalRole = Optional.empty();
        try {
            optionalRole = roleRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Error when searching role by ID");
        }
        return optionalRole.get();
    }

    public ArrayList<Role> getRolesByName(String name) throws Exception {
        if (name == null || "".equals(name)) {
            throw new Exception("Enter a search name.");
        }
        try {
            roleRepository.searchByName(name);
        } catch (Exception e) {
            throw new Exception("Error searching role by name");
        }
        return roleRepository.searchByName(name);
    }

    public Role createRole(Role role) throws Exception {
        if (role.getName() == null || role.getName().length() <= 3)
            throw new Exception("Role name must be specified and be at least 3 characters long.");
        try {
            roleRepository.save(role);
        } catch (Exception e) {
            throw new Exception("Error saving role in database.");
        }
        return role;
    }

    public Role updateRole(Long id, Role role) throws Exception {
        Optional<Role> optionalRole;
        try {
            optionalRole = roleRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Error searching for role to be changed!");
        }

        Role roleUpdating = optionalRole.get();

        if (role.getName() == null || role.getName().length() <= 3)
            throw new Exception("Role name must be specified and be at least 3 characters long");

        roleUpdating.setName(role.getName());

        try {
            roleRepository.save(roleUpdating);
        } catch (Exception e) {
            throw new Exception("Error saving the new role in the database!");
        }

        return roleUpdating;
    }

    public void deleteRole(Long id) throws Exception {

        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Role has not been deleted.!");
        }
    }
}
