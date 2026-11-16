package com.example.securepass;

import java.util.List;

public record CheckResult(int score, String rating, List<String> feedback) {}
