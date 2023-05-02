package org.example.view.mainMenu.gameMenu;

import org.example.model.building.Material;
import org.example.model.building.enums.MaterialType;
import org.example.view.enums.Outputs;
import org.example.view.enums.commands.GameMenuCommands.ShopMenuCommands;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu {

    public void run(Scanner scanner) {
        System.out.println("Now you are in Shop Menu!");

        String inputLine;
        Matcher matcher;

        while (true){
            inputLine = scanner.nextLine();

            if (ShopMenuCommands.getMatcher(inputLine,ShopMenuCommands.SHOW_PRICE_LIST).find())
                System.out.println(showPriceList());
            else if ((matcher=ShopMenuCommands.getMatcher(inputLine,ShopMenuCommands.BUY)).find())
                System.out.println(buy(matcher).toString());
            else if ((matcher=ShopMenuCommands.getMatcher(inputLine,ShopMenuCommands.SELL)).find())
                System.out.println(sell(matcher).toString());
            else if (inputLine.equals("exit shop menu")){
                //TODO:back
                break;
            }
            else System.out.println("Invalid command in Shop Menu");

        }
    }

    public Outputs buy(Matcher matcher){

        String name = matcher.group("name");
        int count = Integer.parseInt(matcher.group("count"));

        LinkedHashMap<Material,Integer> materials = GameMenu.getThisEmpire().getMaterials();

        for (Material material : materials.keySet()){
            if (material.getMaterialType().getName().equals(name)){
                if (GameMenu.getThisEmpire().getGold()<material.getMaterialType().getBuyingPrice()*count)
                    return Outputs.NOT_ENOUGH_GOLD;
                materials.put(material,materials.get(material)+count);
                GameMenu.getThisEmpire().decreaseGold(material.getMaterialType().getBuyingPrice()*count);
                return Outputs.SUCCESS_BUY;
            }
        }

        return Outputs.INVALID_MATERIAL_NAME;
    }

    public Outputs sell(Matcher matcher){

        String name = matcher.group("name");
        int count = Integer.parseInt(matcher.group("count"));

        LinkedHashMap<Material,Integer> materials = GameMenu.getThisEmpire().getMaterials();

        for (Material material : materials.keySet()){
            if (material.getMaterialType().getName().equals(name)){
                if (materials.get(material)<count)
                    return Outputs.NOT_ENOUGH_MATERIAL;
                materials.put(material,materials.get(material)-count);
                GameMenu.getThisEmpire().increaseGold(material.getMaterialType().getBuyingPrice()*count);
                return Outputs.SUCCESS_SELL;
            }
        }

        return Outputs.INVALID_MATERIAL_NAME;
    }

    public String showPriceList(){
        String output="";

        for (Material material : GameMenu.getThisEmpire().getMaterials().keySet()){
            output+="Material : "+material.getMaterialType().getName()+" | Buying Price : "+material.getMaterialType().getBuyingPrice()
                    +" | Selling  Price : "+material.getMaterialType().getSellingPrice()+" | inventory : "
                    +GameMenu.getThisEmpire().getMaterials().get(material)+"\n";
        }

        return output;
    }
}
