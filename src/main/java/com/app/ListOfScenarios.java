package com.app;

import java.util.List;

public record ListOfScenarios(List<Integer> scenarios) {

    public List<Integer> getScenarios() {
        return scenarios;
    }
}
