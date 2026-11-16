package com.example.securepass;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{};:,.?";
    private static final Set<String> COMMON = Set.of(
        "password", "123456", "12345678", "qwerty", "admin",
        "letmein", "welcome", "password1", "iloveyou");

    private final SecureRandom random = new SecureRandom();

    public String generate(int length) {
        String all = LOWER + UPPER + DIGITS + SYMBOLS;
        List<Character> chars = new ArrayList<>();
        chars.add(pick(LOWER));
        chars.add(pick(UPPER));
        chars.add(pick(DIGITS));
        chars.add(pick(SYMBOLS));
        while (chars.size() < length) {
            chars.add(pick(all));
        }
        Collections.shuffle(chars, random);
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            sb.append(c);
        }
        return sb.toString();
    }

    public CheckResult check(String password) {
        List<String> feedback = new ArrayList<>();
        int score = (int) (Math.min(password.length(), 16) * 2.5);

        if (has(password, LOWER)) { score += 15; } else { feedback.add("Add lowercase letters"); }
        if (has(password, UPPER)) { score += 15; } else { feedback.add("Add uppercase letters"); }
        if (has(password, DIGITS)) { score += 15; } else { feedback.add("Add digits"); }
        if (has(password, SYMBOLS)) { score += 15; } else { feedback.add("Add symbols"); }
        if (password.length() < 12) { feedback.add("Use at least 12 characters"); }

        if (COMMON.contains(password.toLowerCase())) {
            score = Math.min(score, 10);
            feedback.add("This is a very common password");
        }

        String rating;
        if (score < 40) { rating = "Weak"; }
        else if (score < 70) { rating = "Medium"; }
        else if (score < 90) { rating = "Strong"; }
        else { rating = "Very Strong"; }

        return new CheckResult(score, rating, feedback);
    }

    private char pick(String set) {
        return set.charAt(random.nextInt(set.length()));
    }

    private boolean has(String password, String set) {
        return password.chars().anyMatch(c -> set.indexOf(c) >= 0);
    }
}
