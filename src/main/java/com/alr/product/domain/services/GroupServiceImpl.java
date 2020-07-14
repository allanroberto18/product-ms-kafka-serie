package com.alr.product.domain.services;

import com.alr.product.domain.contracts.services.GroupService;
import com.alr.product.domain.entities.Group;
import com.alr.product.infrastructure.repositories.GroupRepository;
import com.alr.product.infrastructure.requests.GroupDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
public class GroupServiceImpl implements GroupService {

  private Function<GroupDTO, Group> groupMapper;
  private GroupRepository groupRepository;

  public GroupServiceImpl(
      Function<GroupDTO, Group> groupMapper,
      GroupRepository groupRepository
  ) {
    this.groupMapper = groupMapper;
    this.groupRepository = groupRepository;
  }

  @Override
  public Group save(GroupDTO groupDTO) {
    return Optional.of(groupDTO)
        .map(dto -> groupMapper.apply(dto))
        .map(group -> groupRepository.save(group))
        .get();
  }

  @Override
  public Group save(Integer id, GroupDTO groupDTO) {
    return groupRepository.findById(id)
        .map(group -> {
          group.setName(groupDTO.getName());

          return groupRepository.save(group);
        }).orElseGet(() -> save(groupDTO));
  }

  @Override
  public Optional<Group> find(Integer id) {
    return groupRepository.findById(id);
  }

  @Override
  public List<Group> all() {
    return groupRepository.findAll();
  }
}
