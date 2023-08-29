package me.janek.todo.service

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import me.janek.todo.domain.Todo
import me.janek.todo.domain.TodoRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
class TodoServiceTest {

  @MockkBean
  lateinit var repository: TodoRepository

  lateinit var service: TodoService

  val stub : Todo by lazy {
    Todo(
      id = 1,
      title = "Test",
      description = "detail",
      done = false,
      createdAt = LocalDateTime.now(),
      updatedAt = LocalDateTime.now()
    )
  }

  @BeforeEach
  fun setUp() {
    service = TodoService(repository)
  }

  @Test
  fun `한개의 TODO를 반환해야 한다`() {
    // given
    every { repository.findByIdOrNull(1) } returns stub

    // when
    val  actual = service.findById(1L)

    // then
    assertThat(actual).isNotNull
    assertThat(actual).isEqualTo(stub)
  }

}