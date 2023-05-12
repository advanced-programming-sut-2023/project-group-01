package org.example.controller.mainMenuController.gameMenuController;

import org.example.model.Empire;
import org.example.model.People;
import org.example.model.building.Building;
import org.example.model.building.Gatehouse;
import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.Tower;
import org.example.model.building.castleBuilding.Wall;
import org.example.model.building.enums.BuildingName;
import org.example.model.unit.Catapult;
import org.example.model.unit.CatapultName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import static org.example.view.mainMenu.gameMenu.GameMenu.getMap;

public class BestPath {

    private Empire empire;

    public BestPath(Empire empire) {
        this.empire = empire;
    }

    private void addEdge(ArrayList<ArrayList<Integer>> Tiles, int i1, int j1, int i2, int j2, int size) {
        Tiles.get(i1 * size + j1).add(i2 * size + j2);
        Tiles.get(i2 * size + j2).add(i1 * size + j1);
    }


    public LinkedList<Integer> input(Tile[][] tiles, int xStart, int yStart, int xDestination, int yDestination, boolean tunnelerOption, boolean assassinsOption) {
        int size = tiles[0].length;
        ArrayList<ArrayList<Integer>> Tiles = new ArrayList<ArrayList<Integer>>();
        boolean[] visit = new boolean[size * size];
        initializeAndSetNeighbors(Tiles, tiles, size, visit, tunnelerOption, assassinsOption);
        LinkedList<Integer> path = gainShortestPath(Tiles, visit, xStart, yStart, xDestination, yDestination, size);
        assert path != null;
        Collections.reverse(path);
        return path;
    }

    private LinkedList<Integer> gainShortestPath(ArrayList<ArrayList<Integer>> Tiles, boolean[] visit, int xStart, int yStart, int xDestination, int yDestination, int size) {
        int pred[] = new int[size * size];
        int dist[] = new int[size * size];

        if (!BFS(Tiles, visit, xStart, yStart, xDestination, yDestination, pred, dist, size)) return null;

        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = xDestination * size + yDestination;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }
        return path;
    }

    private boolean BFS(ArrayList<ArrayList<Integer>> Tiles, boolean[] visit, int xStart, int yStart, int xDestination, int yDestination, int[] pred, int[] dist, int size) {

        LinkedList<Integer> queue = new LinkedList<Integer>();

        for (int i = 0; i < size * size; i++) {
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        if (visit[xStart * size + yStart]) return false;

        visit[xStart * size + yStart] = true;
        dist[xStart * size + yStart] = 0;
        queue.add(xStart * size + yStart);

        while (!queue.isEmpty()) {
            int hold = queue.remove();
            for (int i = 0; i < Tiles.get(hold).size(); i++) {
                if (!visit[Tiles.get(hold).get(i)]) {
                    visit[Tiles.get(hold).get(i)] = true;
                    dist[Tiles.get(hold).get(i)] = dist[hold] + 1;
                    pred[Tiles.get(hold).get(i)] = hold;
                    queue.add(Tiles.get(hold).get(i));
                    if (Tiles.get(hold).get(i) == xDestination * size + yDestination) return true;
                }
            }
        }
        return false;
    }

    private void initializeAndSetNeighbors(ArrayList<ArrayList<Integer>> Tiles, Tile[][] tiles, int size, boolean[] visit, boolean tunnelerOption, boolean assassinsOption) {

        // Initializing the ArrayList
        for (int i = 0; i < size * size; i++)
            Tiles.add(new ArrayList<Integer>());

        // adding some parameter to the first row
        for (int j = 0; j < size - 1; j++) {
            if (tunnelerOption && checkNeighborForTunneler(tiles[0][j], tiles[0][j + 1]))
                addEdge(Tiles, 0, j, 0, j + 1, size);
            else if (assassinsOption && (checkNeighborForAssassins(tiles[0][j], tiles[0][j + 1]) || checkNeighbor(tiles[0][j], 0, j, tiles[0][j + 1], 0, j + 1, visit)))
                addEdge(Tiles, 0, j, 0, j + 1, size);
            else if (!tunnelerOption && checkNeighbor(tiles[0][j], 0, j, tiles[0][j + 1], 0, j + 1, visit))
                addEdge(Tiles, 0, j, 0, j + 1, size);
        }
        // adding some parameter to the first coloumn
        for (int i = 0; i < size - 1; i++) {
            if (tunnelerOption && checkNeighborForTunneler(tiles[i][0], tiles[i + 1][0]))
                addEdge(Tiles, i, 0, i + 1, 0, size);
            else if (assassinsOption && checkNeighborForAssassins(tiles[i][0], tiles[i + 1][0]) || checkNeighbor(tiles[i][0], i, 0, tiles[i + 1][0], i + 1, 0, visit))
                addEdge(Tiles, i, 0, i + 1, 0, size);
            else if (!tunnelerOption && !assassinsOption && checkNeighbor(tiles[i][0], i, 0, tiles[i + 1][0], i + 1, 0, visit))
                addEdge(Tiles, i, 0, i + 1, 0, size);
        }

        // adding some parameters to other Tiles
        for (int i = 1; i < size; i++)
            for (int j = 1; j < size; j++) {
                if (tunnelerOption) {
                    if (checkNeighborForTunneler(tiles[i][j], tiles[i - 1][j])) addEdge(Tiles, i, j, i - 1, j, size);
                    if (checkNeighborForTunneler(tiles[i][j], tiles[i][j - 1])) addEdge(Tiles, i, j, i, j - 1, size);
                } else if (assassinsOption) {
                    if (checkNeighborForAssassins(tiles[i][j], tiles[i - 1][j]) || checkNeighbor(tiles[i][j], i, j, tiles[i - 1][j], i - 1, j, visit))
                        addEdge(Tiles, i, j, i - 1, j, size);
                    if (checkNeighborForAssassins(tiles[i][j], tiles[i][j - 1]) || checkNeighbor(tiles[i][j], i, j, tiles[i][j - 1], i, j - 1, visit))
                        addEdge(Tiles, i, j, i, j - 1, size);
                } else {
                    if (checkNeighbor(tiles[i][j], i, j, tiles[i - 1][j], i - 1, j, visit))
                        addEdge(Tiles, i, j, i - 1, j, size);
                    if (checkNeighbor(tiles[i][j], i, j, tiles[i][j - 1], i, j - 1, visit))
                        addEdge(Tiles, i, j, i, j - 1, size);
                }
            }
    }

    private boolean checkNeighborForTunneler(Tile tile1, Tile tile2) {
        if (tile1.getTypeOfTile().isWater() || tile2.getTypeOfTile().isWater()) return false;
        return true;
    }

    private boolean checkNeighborForAssassins(Tile tile1, Tile tile2) {
        Building building1 = tile1.getBuilding();
        Building building2 = tile2.getBuilding();
        if (building1 == null && building2 != null && isBuildingOkForAssassinsMove(building2)) return true;
        if (building2 == null && building1 != null && isBuildingOkForAssassinsMove(building1)) return true;
        return false;
    }

    private boolean isBuildingOkForAssassinsMove(Building building) {
        BuildingName buildingName = building.getBuildingName();
        if (building instanceof Gatehouse || buildingName.getType().equals("tower") || building instanceof Wall)
            return true;
        return false;
    }

    private boolean checkNeighbor(Tile tile1, int x1, int y1, Tile tile2, int x2, int y2, boolean[] visit) {

        Building building1 = tile1.getBuilding();
        Building building2 = tile2.getBuilding();
        boolean cross1 = tile1.getTypeOfTile().getCanCross();
        boolean cross2 = tile2.getTypeOfTile().getCanCross();
        boolean siegeTower1 = haveCatapultTower(tile1);
        boolean siegeTower2 = haveCatapultTower(tile2);

        if (!cross1 || !cross2) return false;
        if (building1 != null && building1.getBuildingName().equals(BuildingName.EMPIRE_CASTLE)) return true;
        if (building2 != null && building2.getBuildingName().equals(BuildingName.EMPIRE_CASTLE)) return true;
        if (building1 == null && building2 == null) return true;
        if (building1 == null && building2 != null && building2.getBuildingName().equals(BuildingName.STAIRS))
            return true;
        if (building1 != null && building1.getBuildingName().equals(BuildingName.STAIRS) && building2 == null)
            return true;
        if (building2 != null && building2.getBuildingName().equals(BuildingName.STAIRS) && building1 == null)
            return true;
        if (building1 != null && building2 != null && isNeighborForBuildings(building1, building2)) return true;
        if (checkGateHouse(building1, building2, cross1, cross2, x1, y1, x2, y2, visit)) return true;
        if (building1 != null && building2 != null && isNeighborForWall(building1, building2)) return true;
        if (building1 != null && building2 != null && building1.equals(building2)) return true;
        if (building1 == null && building2 != null && siegeTower2) return true;
        if (building2 == null && building1 != null && siegeTower1) return true;
        return false;
    }

    private boolean isNeighborForBuildings(Building building1, Building building2) {
        boolean bool1 = false;
        boolean bool2 = false;

        if (building1.equals(building2)) return true;
        if (building1 instanceof Wall || building1.getBuildingName().equals(BuildingName.STAIRS) || building1 instanceof Gatehouse || building1 instanceof Tower)
            bool1 = true;
        if (building2 instanceof Wall || building2.getBuildingName().equals(BuildingName.STAIRS) || building2 instanceof Gatehouse || building2 instanceof Tower)
            bool2 = true;
        if (bool1 && bool2) return true;

        return false;
    }

    private boolean isNeighborForWall(Building building1, Building building2) {
        boolean bool1 = false;
        boolean bool2 = false;
        if (building1 != null && building1 instanceof Wall && ((Wall) building1).getHaveLadder()) bool1 = true;
        if (building2 != null && building2 instanceof Wall && ((Wall) building2).getHaveLadder()) bool2 = true;
        if (bool1 || bool2) return true;
        return false;
    }

    private boolean checkGateHouse(Building building1, Building building2, boolean cross1, boolean cross2, int x1, int y1, int x2, int y2, boolean[] visit) {
        boolean bool1 = false;
        boolean bool2 = false;
        int mapSize = getMap().getSize();
        if (building1 != null && building1 instanceof Gatehouse && building2 == null && cross2) bool1 = true;
        if (building2 != null && building2 instanceof Gatehouse && building1 == null && cross1) bool2 = true;
        if (bool1 && ((Gatehouse) building1).getOpen()) visit[x1 * mapSize + y1] = true;
        if (bool2 && ((Gatehouse) building2).getOpen()) visit[x2 * mapSize + y2] = true;
        if (bool1 || bool2) return true;
        return false;
    }

    private boolean haveCatapultTower(Tile tile) {
        for (People person : tile.getPeople())
            if (person instanceof Catapult && ((Catapult) person).getCatapultName().equals(CatapultName.SIEGE_TOWER))
                return true;
        return false;
    }
}
