package org.example.view.mainMenu.gameMenu;

import org.example.model.building.Material;
import org.example.model.building.enums.MaterialType;
import org.example.model.enums.FoodType;
import org.example.view.enums.BackgroundColor;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.GameMenuCommands.ShopMenuCommands;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

import static org.example.view.mainMenu.gameMenu.GameMenu.getThisEmpire;

public class ShopMenu {

    public void run(Scanner scanner) {
//        System.out.println("Now you are in Shop Menu!");
//
//        String inputLine;
//        Matcher matcher;
//
//        while (true) {
//            inputLine = scanner.nextLine();
//
//            if (ShopMenuCommands.getMatcher(inputLine, ShopMenuCommands.SHOW_PRICE_LIST).find())
//                showPriceList();
//            else if ((matcher = ShopMenuCommands.getMatcher(inputLine, ShopMenuCommands.BUY)).find())
//                System.out.println(buy(matcher).toString());
//            else if ((matcher = ShopMenuCommands.getMatcher(inputLine, ShopMenuCommands.SELL)).find())
//                System.out.println(sell(matcher).toString());
//            else if (inputLine.equals("show golds"))
//                System.out.println("You have " + getThisEmpire().getGold() + " golds.");
//            else if (inputLine.equals("exit")) {
//                return;
//            } else System.out.println("Invalid command in Shop Menu");
//
//        }
    }

    public static Outputs buy(Material material) {
        int count = 5;
        LinkedHashMap<Material, Integer> materials = getThisEmpire().getMaterials();

        if (getThisEmpire().getGold() < material.getMaterialType().getBuyingPrice() * count)
            return Outputs.NOT_ENOUGH_GOLD;
        if (!getThisEmpire().checkCapacity(material.getMaterialType(), count))
            return Outputs.NOT_ENOUGH_CAPACITY;
        materials.put(material, materials.get(material) + count);
        setFoods(material);
        getThisEmpire().decreaseGold(material.getMaterialType().getBuyingPrice() * count);
        return Outputs.SUCCESS_BUY;
    }

    private static void setFoods(Material material) {
        if (material.getMaterialType().getName().equals("bread"))
            getThisEmpire().getFoods().put(FoodType.BREED, (float) getThisEmpire().getMaterials().get(material));
        if (material.getMaterialType().getName().equals("meat"))
            getThisEmpire().getFoods().put(FoodType.MEET, (float) getThisEmpire().getMaterials().get(material));
        if (material.getMaterialType().getName().equals("apple"))
            getThisEmpire().getFoods().put(FoodType.APPLE, (float) getThisEmpire().getMaterials().get(material));
        if (material.getMaterialType().getName().equals("cheese"))
            getThisEmpire().getFoods().put(FoodType.CHEESE, (float) getThisEmpire().getMaterials().get(material));
    }

    public static Outputs sell(Material material) {
        LinkedHashMap<Material, Integer> materials = getThisEmpire().getMaterials();

        int count  = Math.min(5, materials.get(material));
        if (count <= 0)
            return Outputs.NOT_ENOUGH_MATERIAL;
        materials.put(material, materials.get(material) - count);
        setFoods(material);
        getThisEmpire().increaseGold(material.getMaterialType().getSellingPrice() * count);
        return Outputs.SUCCESS_SELL;
    }

    public static void showPriceList() {

        BackgroundColor.changeColor(BackgroundColor.ANSI_GREEN_BACKGROUND);
        BackgroundColor.changeColor(BackgroundColor.ANSI_BLACK_TEXT);
        System.out.println("-------------------------------------------------------------------------------");
        System.out.format("%15s %20s %20s %15s\n", "Material", "Buying Price", "Selling Price", "Inventory");
        System.out.println("-------------------------------------------------------------------------------");
        BackgroundColor.changeColor(BackgroundColor.ANSI_BLUE_BACKGROUND);
        for (Material material : getThisEmpire().getMaterials().keySet()) {
            if (material.getMaterialType().getSellingPrice() != 0)
                System.out.format("%15s %16s %17s %18s\n", material.getMaterialType().getName(), material.getMaterialType().getBuyingPrice(), material.getMaterialType().getSellingPrice(), getThisEmpire().getMaterials().get(material));
        }
        BackgroundColor.changeColor(BackgroundColor.ANSI_RESET);

    }
}
