package by.toukachmikhail.taskmanagementsystem.services.impl;

import static by.toukachmikhail.taskmanagementsystem.exception_handling.enums.NotFoundExceptionMessage.ROLE_NOT_FOUND;

import by.toukachmikhail.taskmanagementsystem.entities.Role;
import by.toukachmikhail.taskmanagementsystem.exception_handling.exception.NotFoundException;
import by.toukachmikhail.taskmanagementsystem.repositories.RoleRepository;
import by.toukachmikhail.taskmanagementsystem.services.RoleService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

private final RoleRepository roleRepository;

  /**
   * @param role
   * @return
   */
  @Override
  public List<Role> findRoleByName(String role) {
    return List.of(roleRepository.findByName("USER").get());
  }
}
