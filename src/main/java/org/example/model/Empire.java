
package org.example.model;

import org.example.model.building.Building;
import org.example.model.building.Material;
import org.example.model.building.castleBuilding.EmpireBuilding;
import org.example.model.building.enums.MaterialType;
import org.example.model.enums.Color;
import org.example.model.enums.FoodType;
import org.example.model.unit.Lord;
import org.example.model.unit.MilitaryUnit;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Empire {
    private EmpireBuilding empireBuilding;
    //TODO set
    private Lord lord;
    private final User player;
    private int popularity;
    private int foodRate;
    private int taxRate;
    private int fearRate;
    private int religionRate;
    private int population;
    private int MaxPopulation;
    private int gold;
    private Color color;
    private Map map;
    private LinkedHashMap<Material,Integer> materials = new LinkedHashMap<>();
    private LinkedHashMap<FoodType,Integer> foods = new LinkedHashMap<>(4);
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<People> people = new ArrayList<>();
    //TODO: should be set
    private LinkedHashMap<String , Boolean> CanBuildOrProduceSomething = new LinkedHashMap<>();
    private ArrayList<Trade> newTrade = new ArrayList<>();
    private ArrayList<Trade> tradeHistory = new ArrayList<>();
    private ArrayList<Trade> trades= new ArrayList<>();

    public Empire(EmpireBuilding empireBuilding, User player) {
        this.empireBuilding = empireBuilding;
        //this.color = empireBuilding.getColor();
        this.player = player;
        this.popularity = 0;
        this.foodRate = 0;
        this.taxRate = 0;
        this.fearRate = 0;
        foods.put(FoodType.BREED,0);
        foods.put(FoodType.APPLE,0);
        foods.put(FoodType.MEET,0);
        foods.put(FoodType.CHEESE,0);
        initializeMaterials();
    }

    private void initializeMaterials(){
        for (MaterialType materialType: MaterialType.values()){
            this.materials.put(new Material(materialType),0);
        }
    }

    public EmpireBuilding getEmpireBuilding() {
        return empireBuilding;
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

    public void increasePopulation(int numberOfPeople) {


    }
    public void reducePopulation(){
        for (People person : people) {
            if (!(person instanceof MilitaryUnit)) {
                people.remove(person);
                return;
            }
        }
    }

    public int getGold() {
        return gold;
    }

    public void increaseGold(int count) {
        this.gold+=count;
    }

    public void decreaseGold(int count) {
        this.gold-=count;
    }
    public void addMaterial(String materialName, int count) {
        for (Material material : materials.keySet()) {
            if (material.getMaterialType().getName().equals(materialName)) {
                int newCount = materials.get(material) - count;
                materials.replace(material, newCount);
            }
        }
    }
    public int getMaxPopulation() {
        return MaxPopulation;
    }

    public Lord getLord() {
        return lord;
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

    public LinkedHashMap<Material, Integer> getMaterials() {
        return materials;
    }

    public User getPlayer() {
        return this.player;
    }

    public void addToNewTrade(Trade trade){
        newTrade.add(trade);
    }

    public void addToTradeHistory(Trade trade){
        tradeHistory.add(trade);
    }

    public void addToTrades(Trade trade){
        trades.add(trade);
    }

    public ArrayList<Trade> getTradeHistory() {
        return tradeHistory;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public ArrayList<Trade> getNewTrade() {
        return newTrade;
    }
    public void clearNewTrades(){
        newTrade.clear();
    }

    public Trade getTradeWhitId(int id){
        for (int i = 0; i < trades.size(); i++)
            if(trades.get(i).getId() == id)
                return trades.get(i);
        return null ;
    }

    public void reduceGold(int amount){
        this.gold -= amount;
    }

    public void addGold(int amount){
        this.gold += amount;
    }

    public void addMaterial(Material material, int amount){
        this.materials.replace(material, this.materials.get(material) + amount);
    }

    public void reduceMaterial(Material material, int amount){
        this.materials.replace(material, this.materials.get(material) - amount);
    }

}
