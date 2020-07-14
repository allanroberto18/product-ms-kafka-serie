package com.alr.product.domain.services;

import com.alr.product.ProductApplication;
import com.alr.product.domain.contracts.services.GroupService;
import com.alr.product.domain.entities.Group;
import com.alr.product.infrastructure.repositories.GroupRepository;
import com.alr.product.infrastructure.requests.GroupDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = { ProductApplication.class })
@ActiveProfiles("test")
public class GroupServiceTest {

  @Autowired
  private GroupService groupService;

  @Mock
  private GroupRepository groupRepository;

  @Mock
  private Function<GroupDTO, Group> groupMapper;

  @BeforeEach
  void setUp() {
    groupService = new GroupServiceImpl(
        groupMapper,
        groupRepository
    );
  }

  @Test
  public void save_withGroupDTO_mustReturnGroup() {
    String expectedName = "Group Test";

    GroupDTO groupDTO = getGroupDTO(expectedName);
    Group groupExpected = getGroup(expectedName);

    Mockito.when(groupMapper.apply(groupDTO)).thenReturn(groupExpected);
    Mockito.when(groupRepository.save(groupExpected)).thenReturn(groupExpected);

    Group group = groupService.save(groupDTO);

    Assertions.assertNotNull(group);
    Assertions.assertEquals(expectedName, group.getName());
  }

  @Test
  public void save_withIdAndGroupDTO_mustReturnGroup() {
    Integer id = 1;
    String expectedName = "Group Test";

    GroupDTO groupDTO = getGroupDTO(expectedName);
    Group groupExpected = getGroup(id, expectedName);

    Mockito.when(groupRepository.findById(id)).thenReturn(Optional.of(groupExpected));
    Mockito.when(groupRepository.save(groupExpected)).thenReturn(groupExpected);

    Group group = groupService.save(id, groupDTO);

    Assertions.assertNotNull(group);
    Assertions.assertEquals(expectedName, group.getName());
  }

  @Test
  public void save_withIdAndGroupDTO_WithNotFoundGroup_mustReturnGroup() {
    Integer id = 1;
    String expectedName = "Group Test";

    GroupDTO groupDTO = getGroupDTO(expectedName);
    Group groupExpected = getGroup(expectedName);

    Mockito.when(groupRepository.findById(id)).thenReturn(Optional.empty());
    Mockito.when(groupMapper.apply(groupDTO)).thenReturn(groupExpected);
    Mockito.when(groupRepository.save(groupExpected)).thenReturn(groupExpected);

    Group group = groupService.save(id, groupDTO);

    Assertions.assertNotNull(group);
    Assertions.assertEquals(expectedName, group.getName());
  }

  @Test
  public void find_WithValidId_MustReturnAGroup() {
    Integer id = 1;
    String expectedName = "Group Test";

    Group groupExpected = getGroup(id, expectedName);

    Mockito.when(groupRepository.findById(id)).thenReturn(Optional.of(groupExpected));

    Optional<Group> group = groupService.find(id);

    Assertions.assertTrue(group.isPresent());
    Assertions.assertEquals(id, group.get().getId());
    Assertions.assertEquals(expectedName, group.get().getName());
  }

  @Test
  public void find_WithInvalidId_MustReturnAGroup() {
    Integer id = 1;

    Mockito.when(groupRepository.findById(id)).thenReturn(Optional.empty());

    Optional<Group> group = groupService.find(id);

    Assertions.assertFalse(group.isPresent());
  }

  @Test
  public void all_NoArgumentes_ReturnListOfGroup() {
    Integer id = 1;
    String name = "Group Test";

    Group group = getGroup(id, name);
    List<Group> groupsExpected = List.of(group);

    Mockito.when(groupRepository.findAll()).thenReturn(groupsExpected);

    List<Group> groups = groupService.all();

    Assertions.assertEquals(groupsExpected.size(), groups.size());
  }

  private GroupDTO getGroupDTO(String expectedName) {
    return GroupDTO.builder()
        .name(expectedName)
        .build();
  }

  private Group getGroup(Integer id, String expectedName) {
    Group group = getGroup(expectedName);
    group.setId(id);

    return group;
  }

  private Group getGroup(String expectedName) {
    return Group.builder()
        .name(expectedName)
        .build();
  }
}
