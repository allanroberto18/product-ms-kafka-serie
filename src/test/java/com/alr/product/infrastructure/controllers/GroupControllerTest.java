package com.alr.product.infrastructure.controllers;

import com.alr.product.application.actions.GroupAction;
import com.alr.product.domain.entities.Group;
import com.alr.product.infrastructure.requests.GroupDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class GroupControllerTest {

  private MockMvc mvc;

  @Mock
  private GroupAction groupAction;

  @InjectMocks
  private GroupController groupController;

  private String basePath = "/api/group";
  private JacksonTester<Group> jsonGroup;
  private JacksonTester<List<Group>> jsonGroups;
  private JacksonTester<GroupDTO> jsonGroupDTO;

  @BeforeEach
  void setUp() {
    JacksonTester.initFields(this, new ObjectMapper());
    mvc = MockMvcBuilders.standaloneSetup(groupController)
        .setControllerAdvice(new RestControllerExceptionHandler())
        .build();
  }

  @Test
  public void find_WithId_MustReturnGroup() throws Exception {
    int id = 1;
    String name = "Group 1";
    String path = String.format("%s/find/%d", basePath, id);
    Group group = new Group(id, name);
    Mockito.when(groupAction.getGroup(id)).thenReturn(Optional.of(group));

    ResultActions resultActions = mvc.perform(
        get(path).contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    resultActions.andExpect(status().isOk());
    Assertions.assertEquals(
        resultActions.andReturn().getResponse().getContentAsString(),
        jsonGroup.write(group).getJson()
    );
  }

  @Test
  public void find_WithId_MustReturnNotFound() throws Exception {
    int id = 1;
    String path = String.format("%s/find/%d", basePath, id);
    Mockito.when(groupAction.getGroup(id)).thenReturn(Optional.empty());

    ResultActions resultActions = mvc.perform(
        get(path).contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    resultActions.andExpect(status().isNotFound());
  }

  @Test
  public void all_NoArguments_MustReturnListOfGroup() throws Exception {
    String path = String.format("%s/all", basePath);
    List<Group> groups = List.of(
        new Group(1, "Group 1"),
        new Group(2, "Group 2")
    );

    Mockito.when(groupAction.getGroups()).thenReturn(groups);

    ResultActions resultActions = mvc.perform(
        get(path).contentType(MediaType.APPLICATION_JSON_VALUE)
    );

    resultActions.andExpect(status().isOk());
    Assertions.assertEquals(
        resultActions.andReturn().getResponse().getContentAsString(),
        jsonGroups.write(groups).getJson()
    );
  }

  @Test
  public void save_WithGroupDTO_MustReturnGroup() throws Exception {
    String path = String.format("%s/save", basePath);
    String name = "Group 1";

    ResultActions resultActions = performPostRequest(path, name);

    resultActions.andExpect(status().isCreated());
  }

  @Test
  public void save_WithIdAndGroupDTONoName_MustReturnBadRequest() throws Exception {
    String path = String.format("%s/save", basePath);
    String name = "";

    ResultActions resultActions = performPostRequest(path, name);

    resultActions.andExpect(status().isBadRequest());
  }

  @Test
  public void update_WithIdAndGroupDTO_MustReturnGroup() throws Exception {
    int id = 1;
    String path = String.format("%s/update/%d", basePath, id);
    String name = "Group 1";

    ResultActions resultActions = performPutRequest(path, name);

    resultActions.andExpect(status().isAccepted());
  }

  @Test
  public void update_WithIdAndGroupDTONoName_MustReturnBadRequest() throws Exception {
    int id = 1;
    String path = String.format("%s/update/%d", basePath, id);
    String name = "";

    ResultActions resultActions = performPutRequest(path, name);

    resultActions.andExpect(status().isBadRequest());
  }

  private ResultActions performPostRequest(String path, String name) throws Exception {
    GroupDTO groupDTO = getGroupDTO(name);

    String jsonRequest = jsonGroupDTO.write(groupDTO).getJson();
    return mvc.perform(
        post(path)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonRequest)
    );
  }

  private ResultActions performPutRequest(String path, String name) throws Exception {
    GroupDTO groupDTO = getGroupDTO(name);

    String jsonRequest = jsonGroupDTO.write(groupDTO).getJson();
    return mvc.perform(
        put(path)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(jsonRequest)
    );
  }

  private GroupDTO getGroupDTO(String name) {
    return GroupDTO.builder()
        .name(name)
        .build();
  }
}
