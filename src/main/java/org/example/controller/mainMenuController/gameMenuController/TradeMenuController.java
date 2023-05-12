package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.Trade;
import org.example.model.building.Material;
import org.example.model.building.enums.MaterialType;
import org.example.view.enums.Outputs;
import org.example.view.mainMenu.gameMenu.GameMenu;
import org.example.view.mainMenu.gameMenu.ShopMenu;
import org.example.view.mainMenu.gameMenu.TradeMenu;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class TradeMenuController {

    TradeMenu tradeMenu;

    public TradeMenuController(TradeMenu tradeMenu) {
        this.tradeMenu = tradeMenu;
    }

    public Outputs trade(String resourceType, int resourceAmount, int price, String message) {
        MaterialType materialType = MaterialType.getMaterialTypeWithName(resourceType);
        if(message.charAt(0) == '\"') message = message.substring(1,message.length()-1);
        if (materialType == null) return Outputs.INVALID_MATERIAL_TYPE;
        if (materialType.getTypeOfProduct().equals("different")) return Outputs.INVALID_MATERIAL_TYPE;
        if (resourceAmount <= 0) return Outputs.INVALID_RESOURCE_AMOUNT_TRADE;
        if (price < 0) return Outputs.INVALID_PRICE_TRADE;
        if(!getThisEmpire().checkCapacity(materialType, resourceAmount))
            return Outputs.NOT_ENOUGH_CAPACITY;
        int id = tradeMenu.getEmpireForTrade().getTradeHistory().size() +
                tradeMenu.getEmpireForTrade().getTrades().size() + 1;
        Trade trade = new Trade(id, new Material(materialType), resourceAmount, price,
                message, getThisEmpire(), tradeMenu.getEmpireForTrade());
        tradeMenu.getEmpireForTrade().addToTrades(trade);
        tradeMenu.getEmpireForTrade().addToNewTrade(trade);
        getThisEmpire().addToTradeHistory(trade);
        return Outputs.SUCCESS;
    }

    public Outputs tradeAccept(int id, String message) {
        if(message.charAt(0) == '\"') message = message.substring(1,message.length()-1);
        Trade trade = getThisEmpire().getTradeWhitId(id);
        if (trade == null) return Outputs.INVALID_ID;
        if (!getThisEmpire().havingMaterial(trade.getMaterial().getMaterialType(), trade.getAmountMaterial()))
            return Outputs.NOT_ENOUGH_MATERIAL;
        if (trade.getEmpireRequester().getGold() < trade.getPrice())
            return Outputs.TRADE_NOT_ENOUGH_GOLD;
        if(!trade.getEmpireRequester().checkCapacity(trade.getMaterial().getMaterialType(), trade.getAmountMaterial()))
            return Outputs.NOT_ENOUGH_CAPACITY;
        trade.getEmpireRequester().decreaseGold(trade.getPrice());
        getThisEmpire().addGold(trade.getPrice());
        getThisEmpire().reduceMaterial(trade.getMaterial(), trade.getAmountMaterial());
        trade.getEmpireRequester().addMaterial(trade.getMaterial().getMaterialType().getName(), trade.getAmountMaterial());
        trade.setAcceptMessage(message);
        getThisEmpire().addToTradeHistory(trade);
        getThisEmpire().removeTrade(trade);
        return Outputs.SUCCESS;
    }


}
