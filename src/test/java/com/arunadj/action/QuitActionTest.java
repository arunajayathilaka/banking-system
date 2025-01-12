package com.arunadj.action;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuitActionTest {

    @Test
    void testGetCmdCode() {
        QuitAction quitAction = new QuitAction();

        assertEquals("Q", quitAction.getCmdCode());
    }
}
