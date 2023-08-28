package me.janek.todo.api

import me.janek.todo.api.model.TodoListResponse
import me.janek.todo.api.model.TodoRequest
import me.janek.todo.api.model.TodoResponse
import me.janek.todo.service.TodoService
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todos")
class TodoController(
  private val todoService: TodoService,
) {

  @GetMapping
  fun getAll() =
    ResponseEntity.status(OK).body(TodoListResponse.of(todoService.findAll()))

  @GetMapping("/{id}")
  fun get(@PathVariable id: Long) =
    ResponseEntity.status(OK).body(TodoResponse.of(todoService.findById(id)))

  @PostMapping
  fun create(@RequestBody request: TodoRequest) =
    ResponseEntity.status(OK).body(TodoResponse.of(todoService.create(request)))

  @PutMapping("/{id}")
  fun update(
    @PathVariable id: Long,
    @RequestBody request: TodoRequest
  ) = ResponseEntity.status(OK).body(TodoResponse.of(todoService.update(id, request)))

  @DeleteMapping("/{id}")
  fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
    todoService.delete(id)
    return ResponseEntity.noContent().build()
  }

}