package com.company;

import java.util.NoSuchElementException;

public class Optional<T> {
    private T value;

    public Optional(T value) {
        this.value = value;
    }

    public T get() throws NoSuchElementException {
        if (this.value == null) throw new NoSuchElementException("element not exist");
        return this.value;
    }

    public boolean isPresent() {
        return this.value != null;
    }
}
