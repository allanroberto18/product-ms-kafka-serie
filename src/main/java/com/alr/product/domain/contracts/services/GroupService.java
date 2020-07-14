package com.alr.product.domain.contracts.services;

import com.alr.product.domain.entities.Group;
import com.alr.product.infrastructure.requests.GroupDTO;

import java.util.List;
import java.util.Optional;

public interface GroupService {
  Group save(final GroupDTO groupDTO);
  Group save(final Integer id, final GroupDTO groupDTO);
  Optional<Group> find(final Integer id);
  List<Group> all();
}
