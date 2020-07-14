package com.alr.product.application.mappers;

import com.alr.product.ProductApplication;
import com.alr.product.domain.entities.Group;
import com.alr.product.infrastructure.requests.GroupDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {ProductApplication.class })
@ActiveProfiles("test")
public class GroupMapperTest {

  @Autowired
  private GroupMapper groupMapper;

  @Test
  public void apply_WithGroupDTO_MustReturnAGroup() {
    String name = "Group Test";

    GroupDTO customerDTO = GroupDTO.builder()
        .name(name)
        .build();

    Group group = groupMapper.apply(customerDTO);

    Assertions.assertNotNull(group);
    Assertions.assertEquals(name, group.getName());
  }
}
