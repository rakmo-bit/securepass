package com.example.securepass;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PasswordServiceTest {

    private final PasswordService service = new PasswordService();

    @Test
    void generatedPasswordHasRequestedLengthAndAllCharacterTypes() {
        String p = service.generate(20);
        assertEquals(20, p.length());
        assertTrue(p.chars().anyMatch(Character::isLowerCase));
        assertTrue(p.chars().anyMatch(Character::isUpperCase));
        assertTrue(p.chars().anyMatch(Character::isDigit));
        assertTrue(p.chars().anyMatch(c -> !Character.isLetterOrDigit(c)));
    }

    @Test
    void commonPasswordIsRatedWeak() {
        assertEquals("Weak", service.check("password").rating());
    }

    @Test
    void longMixedPasswordIsRatedVeryStrong() {
        assertEquals("Very Strong", service.check("Passw0rd!2026").rating());
    }
}
