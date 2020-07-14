package com.alr.product.application.mappers;

import com.alr.product.domain.entities.Group;
import com.alr.product.infrastructure.requests.GroupDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GroupMapper implements Function<GroupDTO, Group> {

  @Override
  public Group apply(GroupDTO groupDTO) {
    return Group.builder()
        .name(groupDTO.getName())
        .build();
  }
}
