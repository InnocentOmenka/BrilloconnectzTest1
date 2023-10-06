package com.innocodes;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserValidationApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String username, email, password, dob;
        boolean isValid = true;
        StringBuilder validationErrors = new StringBuilder();

        // Collect user inputs
        System.out.print("Username: ");
        username = scanner.nextLine();

        System.out.print("Email: ");
        email = scanner.nextLine();

        System.out.print("Password: ");
        password = scanner.nextLine();

        System.out.print("Date of Birth (YYYY-MM-DD): ");
        dob = scanner.nextLine();

        // Validation
        if (!isUsernameValid(username)) {
            validationErrors.append("Username: not empty, min 4 characters\n");
            isValid = false;
        }

        if (!isEmailValid(email)) {
            validationErrors.append("Email: not empty, valid email address\n");
            isValid = false;
        }

        if (!isPasswordValid(password)) {
            validationErrors.append("Password: not empty, strong password (1 upper case, 1 special character, 1 number, 8 characters minimum)\n");
            isValid = false;
        }

        if (!isDOBValid(dob)) {
            validationErrors.append("Date of Birth: not empty, should be 16 years or greater\n");
            isValid = false;
        }

        // Display results
        if (isValid) {
            String jwt = generateJWT(username);
            System.out.println("JWT: " + jwt);

            String verificationResult = verifyJWT(jwt);
            System.out.println("Verification result: " + verificationResult);
        } else {
            System.out.println("Validation failed. Errors:\n" + validationErrors.toString());
        }
    }

    // Validation methods
    private static boolean isUsernameValid(String username) {
        return username != null && username.length() >= 4;
    }

    private static boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regex, email);
    }

    private static boolean isPasswordValid(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        String regex = "^(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9]).{8,}$";
        return Pattern.matches(regex, password);
    }

    private static boolean isDOBValid(String dob) {
        if (dob == null || dob.isEmpty()) {
            return false;
        }
        // Assuming the date format is YYYY-MM-DD
        String[] parts = dob.split("-");
        if (parts.length != 3) {
            return false;
        }
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        // Assuming 16 years or greater
        return year <= (2023 - 16);
    }

    // Generate JWT
    public static String generateJWT(String username) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 3600000); // 1 hour

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    // Verify JWT
    public static String verifyJWT(String jwt) {
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return "verification pass";
        } catch (JwtException e) {
            return "verification fails";
        }

    }
}
