package com.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public void testGreet() {
        Main main = new Main();
        String result = main.greet("World");
        assertEquals("Hello, World!", result);
    }
}
