package com.innocodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationAppTest {
    @Test
    public void testValidToken() {
        // Replace with an actual valid JWT
        String validJwt = "valid.jwt";

        // Perform verification
        String result = UserValidationApp.verifyJWT(validJwt);

        // Assert that verification passes
        assertEquals("verification pass", result);
    }

    @Test
    public void testInvalidToken() {
        // Replace with an actual invalid JWT
        String invalidJwt = "invalid.jwt";

        // Perform verification
        String result = UserValidationApp.verifyJWT(invalidJwt);

        // Assert that verification fails
        assertEquals("verification fails", result);
    }


}