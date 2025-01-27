package by.toukachmikhail.taskmanagementsystem.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class EnumSerializationTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void testTaskPrioritySerialization() throws Exception {
    String json = objectMapper.writeValueAsString(TaskPriority.HIGH);
    assertEquals("\"HIGH\"", json);
  }

  @Test
  void testTaskStatusSerialization() throws Exception {
    String json = objectMapper.writeValueAsString(TaskStatus.WAITING);
    assertEquals("\"WAITING\"", json);
  }

  @Test
  void testUserRoleSerialization() throws Exception {
    String json = objectMapper.writeValueAsString(UserRole.ADMIN);
    assertEquals("\"ADMIN\"", json);
  }

  @Test
  void testTaskPriorityDeserialization() throws Exception {
    TaskPriority priority = objectMapper.readValue("\"MIDDLE\"", TaskPriority.class);
    assertEquals(TaskPriority.MIDDLE, priority);
  }
}
