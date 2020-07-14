package com.alr.product.infrastructure.controllers;

import com.alr.product.application.actions.GroupAction;
import com.alr.product.domain.entities.Group;
import com.alr.product.infrastructure.requests.GroupDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping(path = "/api/group")
public class GroupController {

  private GroupAction groupAction;

  public GroupController(GroupAction groupAction) {
    this.groupAction = groupAction;
  }

  @GetMapping(path = "/find/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Group> getGroup(@PathVariable("id") @NotBlank Integer id) {
    return groupAction.getGroup(id)
        .map(group -> ResponseEntity.ok(group))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
  }

  @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Group>> getGroups() {
    return ResponseEntity.ok(
        groupAction.getGroups()
    );
  }

  @PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Group> saveCustomer(@RequestBody @Valid GroupDTO groupDTO) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(groupAction.save(groupDTO));
  }

  @PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Group> saveCustomer(@PathVariable("id") @NotBlank Integer id, @RequestBody @Valid GroupDTO groupDTO) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(groupAction.save(id, groupDTO));
  }
}
