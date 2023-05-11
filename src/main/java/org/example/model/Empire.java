
package org.example.model;

import org.example.model.building.Building;
import org.example.model.building.Material;
import org.example.model.building.Stable;
import org.example.model.building.castleBuilding.EmpireBuilding;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.MaterialType;
import org.example.model.enums.Color;
import org.example.model.enums.FoodType;
import org.example.model.unit.Lord;
import org.example.model.unit.MilitaryUnit;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class Empire {
    private EmpireBuilding empireBuilding;
    //TODO set
    private Lord lord;
    private final User player;
    private int popularity;
    private int foodRate;
    private int taxRate;
    private int fearRate;
    private int foodPopularity;
    private int taxPopularity;
    private int religionPopularity;
    private int fearPopularity;
    private int population;
    private int maxPopulation;
    private float gold;
    private Color color;

    private Map map;
    private LinkedHashMap<Material, Integer> materials = new LinkedHashMap<>();
    private LinkedHashMap<FoodType, Float> foods = new LinkedHashMap<>(4);
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<People> people = new ArrayList<>();
    //TODO: should be set
    private LinkedHashMap<String, Boolean> CanBuildOrProduceSomething = new LinkedHashMap<>();
    private ArrayList<Trade> newTrade = new ArrayList<>();
    private ArrayList<Trade> tradeHistory = new ArrayList<>();
    private ArrayList<Trade> trades = new ArrayList<>();

    public Empire(EmpireBuilding empireBuilding, User player) {
        this.empireBuilding = empireBuilding;
        this.color = empireBuilding.getColor();
        this.player = player;
        this.popularity = 0;
        this.foodRate = 0;
        this.taxRate = 0;
        this.fearRate = 0;
        this.foodPopularity = 0;
        this.taxPopularity = 0;
        this.fearPopularity = 0;
        this.religionPopularity = 0;
        foods.put(FoodType.BREED, (float) 0);
        foods.put(FoodType.APPLE, (float) 0);
        foods.put(FoodType.MEET, (float) 0);
        foods.put(FoodType.CHEESE, (float) 0);
        initializeMaterials();
    }

    private void initializeMaterials() {
        for (MaterialType materialType : MaterialType.values()) {
            this.materials.put(new Material(materialType), 0);
        }
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public ArrayList<People> getPeople() {
        return people;
    }

    public EmpireBuilding getEmpireBuilding() {
        return empireBuilding;
    }

    public int getPopularity() {
        return people.size();
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

    public LinkedHashMap<FoodType, Float> getFoods() {
        return foods;
    }

    public int getFearPopularity() {
        return fearPopularity;
    }

    public int getFoodPopularity() {
        return foodPopularity;
    }

    public int getReligionPopularity() {
        return religionPopularity;
    }

    public int getTaxPopularity() {
        return taxPopularity;
    }

    public void setPopularity() {
        popularity = (fearPopularity + foodPopularity + taxPopularity + religionPopularity);
    }

    public void setFearPopularity() {
        fearPopularity += fearRate;
    }

    public void setFoodPopularity(int variety) {
        foodPopularity += (4*foodRate);
        foodPopularity += variety;
    }

    public int getVarietyFood(){
        int variety = -1;
        for(FoodType foodType: FoodType.values()){
            if(foods.get(foodType) > 0) variety++;
        }
        return variety;
    }

    public void setTaxPopularity() {
        if(taxRate <= 0)
            taxPopularity += ((taxRate*-2) + 1);
        else if(taxRate <= 4)
            taxPopularity -= taxRate*2;
        else{
            switch (taxRate){
                case 5:
                    taxPopularity -= 12;
                    break;
                case 6:
                    taxPopularity -= 16;
                    break;
                case 7:
                    taxPopularity -= 20;
                    break;
                case 8:
                    taxPopularity -= 24;
                    break;
            }
        }
    }

    public void setFoodRate(int foodRate) {
        this.foodRate = foodRate;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setFearRate(int fearRate) {
        this.fearRate = fearRate;
    }

    public void increaseFoods(FoodType foodType, int foodNumber) {
        foods.replace(foodType, foods.get(foodType) + foodNumber);
    }

    public void reduceFoods(int foodNumber) {

    }

   public int getTotalAmountOfFoods(){
        int amount = 0;
        for(FoodType foodType : FoodType.values())
            amount += foods.get(foodType);
        return amount;
    }

    public void increaseMaxPopulation(int amount) {
        this.maxPopulation += amount;
    }

    public void reduceMaxPopulation(int amount) {
        this.maxPopulation -= amount;
    }

    public void increasePopulation(int amount) {
        if (this.population + amount <= this.maxPopulation)
            this.population += amount;
    }

    public void reducePopulation() {

    }

    public void removePeople(People people) {
        this.people.remove(people);
    }
    public void addPeople(People people) {
        this.people.add(people);
    }

    public void removePeople() {
        for (People person : people) {
            if (!(person instanceof MilitaryUnit) && !(person instanceof Worker)) {
                people.remove(person);
                return;
            }
        }
    }

    public void reduceHorseForDestroy(Stable stable) {
        this.reduceMaterial("horse", stable.getNumberOfHorse());

    }

    public void addUnit(MilitaryUnit militaryUnit) {
        people.add(militaryUnit);
    }


    public float getGold() {
        return gold;
    }

    public void increaseGold(int count) {
        this.gold += count;
    }

    public void decreaseGold(int count) {
        this.gold -= count;
    }

    public void addMaterial(String materialName, int count) {
        for (Material material : materials.keySet()) {
            if (material.getMaterialType().getName().equals(materialName)) {
                int newCount = materials.get(material) + count;
                materials.replace(material, newCount);
            }
        }
    }


    public void reduceMaterial(String materialName, int count) {
        for (Material material : materials.keySet()) {
            if (material.getMaterialType().getName().equals(materialName)) {
                int newCount = materials.get(material) - count;
                materials.replace(material, newCount);
            }
        }
    }

    public int getMaxPopulation() {
        return maxPopulation;
    }

    public Lord getLord() {
        return lord;
    }

    public int getPopulation() {
        return population;
    }

    //TODO check
    public boolean havingMaterial(MaterialType materialType, int count) {

        for (Material material1 : materials.keySet()) {
            if (material1.getMaterialType().getName().equals(materialType.getName())
                    && materials.get(material1) > count) {
                return true;
            }
        }
        return false;
    }

    public LinkedHashMap<Material, Integer> getMaterials() {
        return materials;
    }

    public User getPlayer() {
        return this.player;
    }

    public void addToNewTrade(Trade trade) {
        newTrade.add(trade);
    }

    public void addToTradeHistory(Trade trade) {
        tradeHistory.add(trade);
    }

    public void addToTrades(Trade trade) {
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

    public void clearNewTrades() {
        newTrade.clear();
    }

    public Trade getTradeWhitId(int id) {
        for (int i = 0; i < trades.size(); i++)
            if (trades.get(i).getId() == id)
                return trades.get(i);
        return null;
    }

    public void reduceGold(float amount) {
        this.gold -= amount;
    }

    public void addGold(float amount) {
        this.gold += amount;
    }

    public void addMaterial(Material material, int amount) {
        this.materials.replace(material, this.materials.get(material) + amount);
    }

    public void reduceMaterial(Material material, int amount) {
        this.materials.replace(material, this.materials.get(material) - amount);
    }

    public void addToBuildings(Building building) {
        buildings.add(building);
        if (building.getBuildingName().equals(BuildingName.SMALL_STONE_GATEHOUSE)) this.maxPopulation += 8;
        if (building.getBuildingName().equals(BuildingName.BIG_STONE_GATEHOUSE)) this.maxPopulation += 10;
        if (building.getBuildingName().equals(BuildingName.HOVEL)) this.maxPopulation += 8;
        //TODO check this whit ali
    }

    public void removeFromBuildings(Building building) {
        buildings.remove(building);
    }

    public void addToReligionPopularity(int amount){
        this.religionPopularity += amount;
    }

    public void addToFoodPopularity(int amount){
        this.foodPopularity += amount;
    }

    public int getNormalPopulation() {
        int number = 0;
        for (People people1 : people) {
            if (!(people1 instanceof MilitaryUnit) && !(people1 instanceof Worker))
                number++;
        }
        return number;
    }

    public void changePeopleToWorker(int numberOfWorkers ,int x, int y) {
        ArrayList<People> worker = new ArrayList<>();
        int count = 0;
        for (People person : people) {
            if (count == numberOfWorkers) break;
            if (!(person instanceof Worker) && !(person instanceof MilitaryUnit)) {
                worker.add(person);
                count++;
            }
        }
        people.removeAll(worker);
        for (People person : worker)
            person.getPosition().removeUnit(person);
        for (int i = 0; i < numberOfWorkers; i++)
            people.add(new Worker(getMap().getTile(x, y), this));
    }
}
