package org.example.model;

import org.example.model.building.*;
import org.example.model.building.castleBuilding.enums.EmpireBuilding;
import org.example.model.building.enums.BuildingName;
import org.example.model.building.enums.MaterialType;
import org.example.model.enums.Color;
import org.example.model.enums.FoodType;
import org.example.model.unit.Catapult;
import org.example.model.unit.Engineer;
import org.example.model.unit.Lord;
import org.example.model.unit.MilitaryUnit;
import org.example.model.unit.enums.MilitaryUnitName;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class Empire {
    private EmpireBuilding empireBuilding;
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
    private ArrayList<Disease> diseases = new ArrayList<>();

    private LinkedHashMap<Material, Integer> materials = new LinkedHashMap<>();
    private LinkedHashMap<FoodType, Float> foods = new LinkedHashMap<>(4);
    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<People> people = new ArrayList<>();
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
        this.maxPopulation = 20;
        this.lord = new Lord(getMap().getTile(empireBuilding.getX() - 1, empireBuilding.getY() - 1), this, MilitaryUnitName.LORD,
                empireBuilding.getX() - 1, empireBuilding.getY() - 1);
        foods.put(FoodType.BREED, (float) 0);
        foods.put(FoodType.APPLE, (float) 0);
        foods.put(FoodType.MEET, (float) 0);
        foods.put(FoodType.CHEESE, (float) 0);
        initializeMaterials();
        initializePeople();
    }

    private void initializeMaterials() {
        for (MaterialType materialType : MaterialType.values()) {
            this.materials.put(new Material(materialType), 0);
        }
    }

    private void initializePeople() {
        for (int i = 0; i < 10; i++) {
            people.add(new People(getMap().getTile(empireBuilding.getX(), empireBuilding.getY()), this));
        }
    }

    public void initializeOil() {
        for (People person : people) {
            if (person instanceof Engineer) {
                ((Engineer) person).addOil();
            }
        }
    }

    public int havingStockpile() {
        int capacity = 0;
        int isFull = 0;
        for (Building building : buildings){
            if (building.getBuildingName().equals(BuildingName.STOCKPILE)) {
                capacity += ((Storage) building).getCapacity();
            }
        }


        for (Material material : materials.keySet())
            if (material.getMaterialType().getTypeOfProduct().equals("source")) isFull += materials.get(material);

        System.out.println(capacity);
        System.out.println(isFull);
        System.out.println("---------------------");
        return capacity - isFull;
    }

    public int havingGranary() {
        int capacity = 0;
        int isFull = 0;
        for (Building building : buildings)
            if (building.getBuildingName().equals(BuildingName.GRANARY)) capacity += ((Storage) building).getCapacity();

        for (Material material : materials.keySet())
            if (material.getMaterialType().getTypeOfProduct().equals("food")) isFull += materials.get(material);

        return capacity - isFull;
    }

    public int havingArmoury() {
        int capacity = 0;
        int isFull = 0;
        for (Building building : buildings)
            if (building.getBuildingName().equals(BuildingName.ARMOURY)) capacity += ((Storage) building).getCapacity();

        for (Material material : materials.keySet())
            if (material.getMaterialType().getTypeOfProduct().equals("warfare")) isFull += materials.get(material);

        return capacity - isFull;
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

    public LinkedHashMap<FoodType, Float> getFoods() {
        return foods;
    }
    public void reduceMaterial(MaterialType materialType) {
        for (Material material : materials.keySet()) {
            if (material.getMaterialType().equals(materialType)) {
                materials.replace(material,materials.get(material) - 1);
            }
        }
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
        int diseaseRate = 0;
        for (Disease disease : diseases)
            diseaseRate += disease.getReducePopularityAmount();
        popularity = (fearPopularity + foodPopularity + taxPopularity + religionPopularity - diseaseRate);
    }

    public void setFearPopularity() {
        fearPopularity += fearRate;
    }

    public void setFoodPopularity(int variety) {
        foodPopularity += (4 * foodRate);
        foodPopularity += variety;
    }

    public int getVarietyFood() {
        int variety = -1;
        for (FoodType foodType : FoodType.values()) {
            if (foods.get(foodType) > 0) variety++;
        }
        return variety;
    }

    public void setTaxPopularity() {
        if (taxRate <= 0) taxPopularity += ((taxRate * -2) + 1);
        else if (taxRate <= 4) taxPopularity -= taxRate * 2;
        else {
            switch (taxRate) {
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

    public int getTotalAmountOfFoods() {
        int amount = 0;
        for (FoodType foodType : FoodType.values())
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
        if (this.population + amount <= this.maxPopulation) this.population += amount;
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

    public void decreaseGold(float count) {
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
        return people.size();
    }

    public boolean havingMaterial(MaterialType materialType, int count) {

        for (Material material1 : materials.keySet()) {
            if (material1.getMaterialType().getName().equals(materialType.getName()) && materials.get(material1) >= count) {
                return true;
            }
        }
        return false;
    }

    public boolean haveBuilding(BuildingName buildingName) {
        for (Building building : buildings)
            if (building.getBuildingName().equals(buildingName)) return true;
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

    public void removeTrade(Trade trade) {
        trades.remove(trade);
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
            if (trades.get(i).getId() == id) return trades.get(i);
        return null;
    }


    public void addGold(float amount) {
        this.gold += amount;
    }

    public void addMaterial(Material material, int amount) {
        this.materials.replace(material, this.materials.get(material) + amount);
    }

    public void reduceMaterial(Material material, int amount) {
        for (Material myMaterial : materials.keySet()) {
            if (myMaterial.getMaterialType().getName().equals(material.getMaterialType().getName())) {
                materials.replace(myMaterial, materials.get(myMaterial) - amount);
            }
        }
    }

    public void addToBuildings(Building building) {
        buildings.add(building);
        if (building.getBuildingName().equals(BuildingName.SMALL_STONE_GATEHOUSE)) this.maxPopulation += 8;
        if (building.getBuildingName().equals(BuildingName.BIG_STONE_GATEHOUSE)) this.maxPopulation += 10;
        if (building.getBuildingName().equals(BuildingName.HOVEL)) this.maxPopulation += 8;
    }

    public void removeFromBuildings(Building building) {
        buildings.remove(building);
    }

    public void addToReligionPopularity(int amount) {
        this.religionPopularity += amount;
    }

    public void addToFoodPopularity(int amount) {
        this.foodPopularity += amount;
    }

    public int getNormalPopulation() {
        int number = 0;
        for (People people1 : people)
            if (!(people1 instanceof MilitaryUnit) && !(people1 instanceof Worker)) number++;
        return number;
    }

    public ArrayList<Worker> changePeopleToWorker(int numberOfWorkers, int x, int y) {
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
        ArrayList<Worker> workers = new ArrayList<>();
        for (int i = 0; i < numberOfWorkers; i++) {
            Worker worker1 = new Worker(getMap().getTile(x, y), this);
            people.add(worker1);
        }
        return workers;
    }

    public void setAttackOfUnits() {
        for (People unit : people) {
            int newAttack;
            if (unit instanceof MilitaryUnit && !(unit instanceof Catapult)) {
                newAttack = ((MilitaryUnit) unit).getMilitaryUnitName().getMaxAttack() * (100 + -5 * fearRate) / 100;
                ((MilitaryUnit) unit).getMilitaryUnitName().setAttack(newAttack);
            }else if (unit instanceof Catapult){
                newAttack =  ((Catapult) unit).getCatapultName().getDamage();
                ((Catapult) unit).getCatapultName().setDamage(newAttack);
            }
        }
    }

    public void createMaterial() {
        for (Building building : buildings) {
            if (building instanceof FirstProducer) {
                ((FirstProducer) building).setRate(fearRate + 5);
                ((FirstProducer) building).createMaterial();
            }
            if (building instanceof SecondProducer) {
                ((SecondProducer) building).setRate(fearRate + 5);
                ((SecondProducer) building).createMaterial();
            }
        }
    }

    public boolean checkCapacity(MaterialType materialType, int count) {
        int capacity = 0;
        if (materialType.getTypeOfProduct().equals("source")) capacity = havingStockpile();
        else if (materialType.getTypeOfProduct().equals("food")) capacity = havingGranary();
        else if (materialType.getTypeOfProduct().equals("warfare")) capacity = havingArmoury();
        if (count > capacity) return false;
        else return true;
    }


    public boolean havingSmallGate() {
        for (Building building : buildings)
            if (building.getBuildingName().equals(BuildingName.SMALL_STONE_GATEHOUSE)) return true;
        return false;
    }

    public ArrayList<MilitaryUnit> getUnits() {
        ArrayList<MilitaryUnit> units = new ArrayList<>();
        for (People people1 : people)
            if (people1 instanceof MilitaryUnit)
                units.add((MilitaryUnit)people1);
        return units;
    }

    public ArrayList<Disease> getDiseases() {
        return diseases;
    }

}
