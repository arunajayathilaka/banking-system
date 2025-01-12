package com.arunadj;

import com.arunadj.action.MenuAction;

import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class BankingSystem {

    public static void main(String[] args) {
        Map<String, MenuAction> actionMap = ServiceLoader.load(MenuAction.class)
                .stream()
                .collect(Collectors.toMap((action) -> action.get().getCmdCode(), ServiceLoader.Provider::get));
        while (true) {
            System.out.println("""
                    Welcome to AwesomeGIC Bank! What would you like to do?
                    [T] Input transactions\s
                    [I] Define interest rules
                    [P] Print statement
                    [Q] Quit
                    >""");
            String choice = ScannerFactory.getScanner().nextLine().trim().toUpperCase();

            Optional.ofNullable(actionMap.get(choice))
                    .ifPresentOrElse(
                            MenuAction::execute,
                            () -> System.out.println("Invalid choice. Please select T, I, P, or Q.")
                    );

        }
    }

}
