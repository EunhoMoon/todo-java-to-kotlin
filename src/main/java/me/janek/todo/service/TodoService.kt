package me.janek.todo.service

import me.janek.todo.api.model.TodoRequest
import me.janek.todo.domain.Todo
import me.janek.todo.domain.TodoRepository
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.domain.Sort.by
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class TodoService(
  private val todoRepository: TodoRepository
) {

  fun findAll(): List<Todo> = todoRepository.findAll(by(DESC, "id"))

  fun findById(id: Long): Todo =
    todoRepository.findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

  @Transactional
  fun create(request: TodoRequest?): Todo {
    checkNotNull(request) { "TodoRequest is null" }

    val todo = Todo(
      title = request.title,
      description = request.description,
      done = request.done,
      createdAt = LocalDateTime.now()
    )
    return todoRepository.save(todo)
  }

  @Transactional
  fun update(id: Long, request: TodoRequest?): Todo {
    checkNotNull(request) { "TodoRequest is null" }

    return findById(id).let {
      it.update(request.title, request.description, request.done)
      todoRepository.save(it)
    }
  }

  @Transactional
  fun delete(id: Long) = todoRepository.deleteById(id)

}