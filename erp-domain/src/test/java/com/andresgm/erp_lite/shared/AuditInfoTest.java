package com.andresgm.erp_lite.shared;

import com.andresgm.erp_lite.domain.shared.AuditInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("AuditInfo Domain Test")
public class AuditInfoTest {

    @Test
    @DisplayName("Should create AuditInfo via create factory")
    void shouldCreateAuditInfo(){
        Instant now = Instant.now();

        AuditInfo auditInfo = AuditInfo.create("tester", now);

        assertEquals("tester", auditInfo.createdBy());
        assertEquals(now, auditInfo.createdAt());
        assertEquals(now, auditInfo.updatedAt());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when createdBy is null")
    void shouldThrowWhenCreatedByIsNull(){
        Instant now = Instant.now();

        assertThrows(IllegalArgumentException.class, () -> new AuditInfo(null, now, now));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when createdBy is blank")
    void shouldThrowWhenCreatedByIsBlank(){
        Instant now = Instant.now();

        assertThrows(IllegalArgumentException.class, () -> new AuditInfo("  ", now, now));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when createdAt is null")
    void shouldThrowWhenCreatedAtIsNull(){
        Instant now = Instant.now();

        assertThrows(IllegalArgumentException.class, () -> new AuditInfo("tester", null, now));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when updatedAt is null")
    void shouldThrowWhenUpdatedAtIsNull(){
        Instant now = Instant.now();

        assertThrows(IllegalArgumentException.class, () -> new AuditInfo("tester", now, null));
    }

    @Test
    @DisplayName("Should update the timestamp keeping createdBy and createdAt")
    void shouldUpdateTimestamp(){
        Instant createdAt = Instant.now().minusSeconds(60);
        AuditInfo auditInfo = AuditInfo.create("tester", createdAt);

        AuditInfo updated = auditInfo.updateTimestamp();

        assertEquals("tester", updated.createdBy());
        assertEquals(createdAt, updated.createdAt());
        assertFalse(updated.updatedAt().isBefore(createdAt));
    }
}
