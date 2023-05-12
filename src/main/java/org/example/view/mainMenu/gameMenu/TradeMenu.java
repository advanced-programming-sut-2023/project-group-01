package org.example.view.mainMenu.gameMenu;

import org.example.controller.mainMenuController.gameMenuController.TradeMenuController;
import org.example.model.Empire;
import org.example.model.Trade;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.GameMenuCommands.TradeMenuCommands;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import static org.example.view.mainMenu.gameMenu.GameMenu.*;

public class TradeMenu {

    TradeMenuController tradeMenuController;

    private Empire empireForTrade;

    public TradeMenu() {
        tradeMenuController = new TradeMenuController(this);
    }

    public void setEmpireForTrade(Scanner scanner) {
        System.out.println("please enter username of empire you want trade");
        Empire empire;
        while ((empire = getEmpireWhitUsername(scanner.nextLine())) == null)
            System.out.println("The empire not found please try again");
        empireForTrade = empire;
    }

    public Empire getEmpireForTrade() {
        return empireForTrade;
    }

    public void run(Scanner scanner) {
        showNewTrades();
        while (true) {
            String command = scanner.nextLine();
            Matcher matcher;
            if (command.equals("exit")) return;
            else if (command.equals("trade with an empire")) {
                outer:
                while (true) {
                    showEmpires();
                    setEmpireForTrade(scanner);
                    while (true) {
                        command = scanner.nextLine();
                        if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE)) != null)
                            tradeChecker(matcher);
                        else if (command.equals("choose another empire")) break;
                        else if (command.equals("cancel")) {
                            empireForTrade = null;
                            break outer;
                        } else if (command.equals("exit")) return;
                        else System.out.println("Invalid command in Trade whit empire !");
                    }
                }
            } else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_LIST)) != null)
                tradeList();
            else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_ACCEPT)) != null)
                tradeAcceptChecker(matcher);
            else if ((matcher = TradeMenuCommands.getMatcher(command, TradeMenuCommands.TRADE_HISTORY)) != null)
                tradeHistory();
            else System.out.println("Invalid command in Trade Menu !");
        }
    }

    public void showNewTrades() {
        ArrayList<Trade> newTrades = getThisEmpire().getNewTrade();
        printTradeInfo(newTrades);
        getThisEmpire().clearNewTrades();
    }

    public void showEmpires() {
        int numberOfEmpire = 1;
        for (Empire empire : GameMenu.getEmpires())
            if (!empire.equals(getThisEmpire()))
                System.out.println("Empire" + numberOfEmpire++ + ". " + empire.getPlayer().getUsername());
    }

    public void tradeChecker(Matcher matcher) {
        matcher.find();
        String type = matcher.group("type");
        int amount = Integer.parseInt(matcher.group("amount"));
        int price = Integer.parseInt(matcher.group("price"));
        String message = matcher.group("message");
        Outputs outputs = tradeMenuController.trade(type, amount, price, message);
        System.out.println(outputs.toString());
    }

    public void tradeList() {
        ArrayList<Trade> trades = getThisEmpire().getTrades();
        printTradeInfo(trades);
    }

    private void printTradeInfo(ArrayList<Trade> trades) {
        for (int i = 0; i < trades.size(); i++) {
            System.out.print("from empire: " + trades.get(i).getEmpireRequester().getPlayer().getUsername() +
                    "| to empire: " + trades.get(i).getToEmpire().getPlayer().getUsername() +
                    "| material: " + trades.get(i).getMaterial().getMaterialType().getName() +
                    "| amount: " + trades.get(i).getAmountMaterial() +
                    "| price: " + trades.get(i).getPrice() +
                    "| id: " + trades.get(i).getId() +
                    "| request message: " + trades.get(i).getRequestMessage());
            if(trades.equals(getThisEmpire().getTradeHistory()))
                System.out.println("| accept answer: " + trades.get(i).getAcceptMessage());
            else System.out.println();
        }
    }

    public void tradeAcceptChecker(Matcher matcher) {
        matcher.find();
        int id = Integer.parseInt(matcher.group("id"));
        String message = matcher.group("message");
        Outputs outputs = tradeMenuController.tradeAccept(id, message);
        System.out.println(outputs.toString());
    }

    public void tradeHistory() {
        ArrayList<Trade> historyTrades = getThisEmpire().getTradeHistory();
        printTradeInfo(historyTrades);
    }

}
