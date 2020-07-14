package com.alr.product.application.actions;

import com.alr.product.domain.contracts.services.GroupService;
import com.alr.product.domain.entities.Group;
import com.alr.product.infrastructure.requests.GroupDTO;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Cacheable(cacheNames = { "groups", "group" })
public class GroupAction {

  private GroupService groupService;

  public GroupAction(GroupService groupService) {
    this.groupService = groupService;
  }

  @Cacheable(cacheNames = "group", key = "#result.get().id", unless = "#result.empty()")
  public Optional<Group> getGroup(final Integer id) {
    return groupService.find(id);
  }

  @Cacheable(cacheNames = "groups")
  public List<Group> getGroups() {
    return groupService.all();
  }

  @Caching(
      put = { @CachePut("groups") },
      cacheable = { @Cacheable(cacheNames = "group", key = "#result.id") }
  )
  public Group save(final GroupDTO groupDTO) {
    return groupService.save(groupDTO);
  }

  @Caching(
      put = { @CachePut("groups") },
      cacheable = { @Cacheable(cacheNames = "group", key = "#result.id") }
  )
  public Group save(final Integer id, final GroupDTO groupDTO) {
    return groupService.save(id, groupDTO);
  }
}
