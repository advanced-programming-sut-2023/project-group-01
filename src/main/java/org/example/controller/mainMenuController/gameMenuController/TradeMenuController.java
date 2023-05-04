package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.Trade;
import org.example.model.building.Material;
import org.example.model.building.enums.MaterialType;
import org.example.view.enums.Outputs;
import org.example.view.mainMenu.gameMenu.TradeMenu;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class TradeMenuController {

    TradeMenu tradeMenu;

    public TradeMenuController(TradeMenu tradeMenu) {
        this.tradeMenu = tradeMenu;
    }

    public Outputs trad(String resourceType, int resourceAmount, int price, String message) {
        try {
           MaterialType materialType = MaterialType.valueOf(resourceType);
           if(materialType.getTypeOfProduct().equals("different")) return Outputs.INVALID_MATERIAL_TYPE;
           if(resourceAmount <= 0) return Outputs.INVALID_RESOURCE_AMOUNT_TRADE;
           if(price < 0) return Outputs.INVALID_PRICE_TRADE;
           int id = tradeMenu.getEmpireForTrade().getTradeHistory().size() +
                   tradeMenu.getEmpireForTrade().getTrades().size() + 1;
           Trade trade = new Trade(id, new Material(materialType), resourceAmount, price, message, getThisEmpire());
            tradeMenu.getEmpireForTrade().addToTrades(trade);
            tradeMenu.getEmpireForTrade().addToNewTrade(trade);
            getThisEmpire().addToTradeHistory(trade);
        }
        catch (IllegalArgumentException e){
            return Outputs.INVALID_MATERIAL_TYPE;
        }
        return Outputs.SUCCESS;
    }

    public Outputs tradeAccept(int id, String message) {
        Trade trade = getThisEmpire().getTradeWhitId(id);
        if (trade == null) return Outputs.INVALID_ID;
        if (!getThisEmpire().havingMaterial(trade.getMaterial(), trade.getAmountMaterial()))
            return Outputs.NOT_ENOUGH_MATERIAL;
        if(tradeMenu.getEmpireForTrade().getGold() < trade.getPrice())
            return Outputs.TRADE_NOT_ENOUGH_GOLD;
        tradeMenu.getEmpireForTrade().reduceGold(trade.getPrice());
        getThisEmpire().reduceMaterial(trade.getMaterial(), trade.getAmountMaterial());
        trade.setAcceptMessage(message);
        getThisEmpire().addToTradeHistory(trade);
        return Outputs.SUCCESS;
    }
}
