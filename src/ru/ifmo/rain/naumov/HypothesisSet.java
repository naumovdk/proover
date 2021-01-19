package ru.ifmo.rain.naumov;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HypothesisSet extends HashMap<String, Boolean> {
    public HypothesisSet() {
        super();
    }

    HypothesisSet(Map<String, Boolean> source) {
        this.putAll(source);
    }
}
