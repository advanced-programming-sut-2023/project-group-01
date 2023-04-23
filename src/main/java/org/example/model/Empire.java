package org.example.model;

import org.example.model.building.Building;
import org.example.model.building.Material;
import org.example.model.enums.Color;
import org.example.model.enums.FoodType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;

public class Empire {
    private int popularity;
    private int foodRate;
    private int taxRate;
    private int fearRate;
    private int religionRate;
    private int population;
    private int MaxPopulation;
    private int gold;
    private final Color color;
    private Map map;
    private LinkedHashMap<Material,Integer> materials = new LinkedHashMap<>();
    private LinkedHashMap<FoodType,Integer> foods = new LinkedHashMap<>(4);
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<People> people = new ArrayList<>();
    //TODO: should be set
    private LinkedHashMap<String , Boolean> CanBuildOrProduceSomething = new LinkedHashMap<>();

    public Empire() {
        this.color = Color.RED;
        this.popularity = 0;
        this.foodRate = 0;
        this.taxRate = 0;
        this.fearRate = 0;
        foods.put(FoodType.BREED,0);
        foods.put(FoodType.APPLE,0);
        foods.put(FoodType.MEET,0);
        foods.put(FoodType.CHEESE,0);
    }

    public int getPopularity() {
        return popularity;
    }

    public int getFoodRate() {
        return foodRate;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public int getFearRate() {
        return fearRate;
    }

    public LinkedHashMap<FoodType, Integer> getFoods() {
        return foods;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public void increaseFoods(FoodType foodType ,int foodNumber) {
        foods.replace(foodType, foods.get(foodType) + foodNumber);
    }
    public void reduceFoods(int foodNumber) {

    }

    public void increasePopulation() {

    }
    public void reducePopulation(){

    }

    public int getGold() {
        return gold;
    }

    public int getMaxPopulation() {
        return MaxPopulation;
    }

    public int getPopulation() {
        return population;
    }

    public boolean havingMaterial(Material material, int count) {

        for (Material material1 : materials.keySet()) {
            if (material1.getMaterialType().getName().equals(material.getMaterialType().getName())
                    && materials.get(material) > count) {
                return true;
            }
        }
        return false;
    }
    public Map getMap() {
        return map;
    }
}
