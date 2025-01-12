package com.arunadj;

import java.util.Scanner;

/**
 * A simple wrapper for {@code Scanner} to read input from the console.
 */
public class ScannerWrapper {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Reads the next line of input from the console.
     *
     * @return the input line as a {@code String}
     */
    public String nextLine() {
        return scanner.nextLine();
    }
}
