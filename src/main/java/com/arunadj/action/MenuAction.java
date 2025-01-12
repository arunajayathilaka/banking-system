package com.arunadj.action;

/**
 * This is interface for actions in banking system.
 */
public interface MenuAction {

    /**
     * Execute the action based on user input
     */
    void execute();

    /**
     * Gets cmd code specific for action
     * @return cmd code
     */
    String getCmdCode();
}
