package repository_layer.repository.role;

import bussiness_layer.models.Role;

public interface RoleRepository {

  boolean insert (Role role);

  Role findByName(String name);

  Role findById(Long id);
}
