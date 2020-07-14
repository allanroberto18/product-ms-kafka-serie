package com.alr.product.infrastructure.repositories;

import com.alr.product.ProductApplication;
import com.alr.product.domain.entities.Group;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ContextConfiguration(classes = { ProductApplication.class })
@ActiveProfiles("test")
public class GroupRepositoryTest {

  @Autowired
  private GroupRepository groupRepository;

  @BeforeEach
  void setUp() {
    Group groupExpected = Group.builder()
        .name("group 1")
        .build();

    groupRepository.save(groupExpected);
  }

  @Test
  public void save_WithObject_MustReturnNewGroup() {
    Group groupExpected = Group.builder()
        .name("group 2")
        .build();

    Group group = groupRepository.save(groupExpected);

    Assertions.assertNotNull(group.getId());
    Assertions.assertEquals(groupExpected.getName(), group.getName());
  }

  @Test
  public void save_WithId_MustReturnSameGroup() {
    Group groupExpected = Group.builder()
        .id(1)
        .name("group 1a")
        .build();

    Group group = groupRepository.save(groupExpected);

    Assertions.assertEquals(groupExpected.getName(), group.getName());
  }

  @Test
  public void findById_WithValidId_MustReturnAnOptionalOfGroup() {
    Integer id = 1;

    Optional<Group> optGroup = groupRepository.findById(id);

    Assertions.assertTrue(optGroup.isPresent());
  }

  @Test
  public void findAll_NoArguments_MustReturnAListOfGroup() {
    List<Group> groups = groupRepository.findAll();

    Assertions.assertTrue(groups.size() > 0);
  }
}
