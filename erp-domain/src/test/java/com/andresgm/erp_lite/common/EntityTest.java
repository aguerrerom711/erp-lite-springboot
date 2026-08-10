package com.andresgm.erp_lite.common;

import com.andresgm.erp_lite.domain.common.Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Entity Domain Test")
public class EntityTest {

    private static class TestEntity extends Entity<String> {
        TestEntity(String id) {
            super(id);
        }
    }

    private static class OtherEntity extends Entity<String> {
        OtherEntity(String id) {
            super(id);
        }
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when id is null")
    void shouldThrowIllegalArgumentExceptionWhenIdIsNull(){
        IllegalArgumentException targetException = assertThrows(IllegalArgumentException.class,
                () -> new TestEntity(null));

        assertEquals("Entity ID cannot be null", targetException.getMessage());
    }

    @Test
    @DisplayName("Should return the id via getId")
    void shouldReturnId(){
        TestEntity entity = new TestEntity("id-1");

        assertEquals("id-1", entity.getId());
    }

    @Test
    @DisplayName("Should be equal to itself")
    void shouldBeEqualToItself(){
        TestEntity entity = new TestEntity("id-1");

        assertTrue(entity.equals(entity));
    }

    @Test
    @DisplayName("Should not be equal to null")
    void shouldNotBeEqualToNull(){
        TestEntity entity = new TestEntity("id-1");

        assertNotEquals(entity, null);
    }

    @Test
    @DisplayName("Should not be equal when classes differ")
    void shouldNotBeEqualWhenClassesDiffer(){
        TestEntity entity = new TestEntity("id-1");
        OtherEntity other = new OtherEntity("id-1");

        assertNotEquals(entity, other);
    }

    @Test
    @DisplayName("Should not be equal when ids differ")
    void shouldNotBeEqualWhenIdsDiffer(){
        TestEntity entityA = new TestEntity("id-1");
        TestEntity entityB = new TestEntity("id-2");

        assertNotEquals(entityA, entityB);
    }

    @Test
    @DisplayName("Should be equal and share hashCode when ids match")
    void shouldBeEqualWhenIdsMatch(){
        TestEntity entityA = new TestEntity("id-1");
        TestEntity entityB = new TestEntity("id-1");

        assertEquals(entityA, entityB);
        assertEquals(entityA.hashCode(), entityB.hashCode());
    }
}
