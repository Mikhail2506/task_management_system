package by.toukachmikhail.taskmanagementsystem.services;

import by.toukachmikhail.taskmanagementsystem.entities.Role;
import java.util.List;
import java.util.Set;

public interface RoleService {

  List<Role> findRoleByName(String role);
}
