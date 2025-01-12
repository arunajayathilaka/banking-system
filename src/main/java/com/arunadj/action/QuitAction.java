package com.arunadj.action;

public class QuitAction implements MenuAction {
    @Override
    public void execute() {
        System.out.println("Thank you for banking with AwesomeGIC Bank.\nHave a nice day!");
        System.exit(0);
    }

    @Override
    public String getCmdCode() {
        return "Q";
    }
}
