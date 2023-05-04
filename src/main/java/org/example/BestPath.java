package org.example;

import org.example.model.Empire;
import org.example.model.building.Gatehouse;
import org.example.model.building.Tile;
import org.example.model.building.castleBuilding.CastleBuilding;
import org.example.model.building.castleBuilding.Stairs;
import org.example.model.building.castleBuilding.Wall;

import java.util.ArrayList;
import java.util.LinkedList;

public class BestPath {
    //TODO set the empire
    //TODO reverse the LinkedList
    private Empire empire;

    public BestPath(Empire empire) {
        this.empire = empire;
    }
    public void addEdge(ArrayList<ArrayList<Integer>> Tiles, int i1, int j1, int i2, int j2, int size) {
        Tiles.get(i1 * size + j1).add(i2 * size + j2);
        Tiles.get(i2 * size + j2).add(i1 * size + j1);
    }

    public LinkedList<Integer> input(Tile[][] tiles, int xStart, int yStart,
                                     int xDestination, int yDestination, boolean tunnelerOption) {
        int size = tiles[0].length;
        ArrayList<ArrayList<Integer>> Tiles = new ArrayList<ArrayList<Integer>>();
        boolean[] visit = new boolean[size * size];
        initializeAndSetNeighbors(Tiles, tiles, size, visit, tunnelerOption);
        return gainShortestPath(Tiles, visit, xStart, yStart, xDestination, yDestination, size);
    }

    public LinkedList<Integer> gainShortestPath(ArrayList<ArrayList<Integer>> Tiles, boolean[] visit,
                                                int xStart, int yStart, int xDestination, int yDestination, int size) {
        int pred[] = new int[size * size];
        int dist[] = new int[size * size];

        if (!BFS(Tiles, visit, xStart, yStart, xDestination, yDestination, pred, dist, size)) {
            return null;
        }

        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = xDestination * size + yDestination;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }
        return path;
    }

    public boolean BFS(ArrayList<ArrayList<Integer>> Tiles, boolean[] visit, int xStart, int yStart, int xDestination, int yDestination, int[] pred, int[] dist, int size) {

        LinkedList<Integer> queue = new LinkedList<Integer>();

        for (int i = 0; i < size * size; i++) {
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        //TODO
        if (visit[xStart * size + yStart]) {
            return false;
        }

        visit[xStart * size + yStart] = true;
        dist[xStart * size + yStart] = 0;
        queue.add(xStart * size + yStart);

        while (!queue.isEmpty()) {
            int hold = queue.remove();
            for (int i = 0; i < Tiles.get(hold).size(); i++) {
                if (visit[Tiles.get(hold).get(i)] == false) {
                    visit[Tiles.get(hold).get(i)] = true;
                    dist[Tiles.get(hold).get(i)] = dist[hold] + 1;
                    pred[Tiles.get(hold).get(i)] = hold;
                    queue.add(Tiles.get(hold).get(i));
                    if (Tiles.get(hold).get(i) == xDestination * size + yDestination)
                        return true;
                }
            }
        }
        return false;
    }

    //ببین یه دیوار و یه زمین عادی اگه داخل زمین عادی نردبان باشه اوکیه یا نه؟
    //برای دروازه بیا چک کن اگه مالک ان یکی دیگه بود ارتباط نده اگر نه ارتباط بده
    //اگه دروازه مال خودش بود باز بود بولینش ترو هست اگر نه بولینش فالس هست
    public void initializeAndSetNeighbors(ArrayList<ArrayList<Integer>> Tiles, Tile[][] tiles, int size, boolean[] visit, boolean tunnelerOption) {

        // Initializing the ArrayList
        for (int i = 0; i < size * size; i++)
            Tiles.add(new ArrayList<Integer>());

        // adding some parameter to the first row
        for (int j = 0; j < size - 1; j++)
            if (tunnelerOption && checkNeighborForTunneler(tiles[0][j], tiles[0][j + 1]))
                    addEdge(Tiles, 0 , j, 0, j + 1, size);
            else if (!tunnelerOption && checkNeighbor(tiles[0][j], 0, j, tiles[0][j + 1], 0, j + 1, visit))
                    addEdge(Tiles, 0, j, 0, j + 1, size);

        // adding some parameter to the first coloumn
        for (int i = 0; i < size - 1; i++)
            if (tunnelerOption && checkNeighborForTunneler(tiles[i][0], tiles[i + 1][0]))
                addEdge(Tiles, i, 0, i + 1, 0, size);
            else if (!tunnelerOption && checkNeighbor(tiles[i][0], i, 0, tiles[i + 1][0], i + 1, 0, visit))
                    addEdge(Tiles, i, 0, i + 1, 0, size);

        // adding some parameters to other Tiles
        for (int i = 1; i < size; i++)
            for (int j = 1; j < size; j++)
                if (tunnelerOption) {
                    if (checkNeighborForTunneler(tiles[i][j], tiles[i - 1][j]))
                        addEdge(Tiles, i, j, i - 1, j, size);
                    if (checkNeighborForTunneler(tiles[i][j], tiles[i][j - 1]))
                        addEdge(Tiles, i, j, i, j - 1, size);
                } else {
                    if (checkNeighbor(tiles[i][j], i, j, tiles[i - 1][j], i - 1, j, visit))
                        addEdge(Tiles, i, j, i - 1, j, size);
                    if (checkNeighbor(tiles[i][j], i, j, tiles[i][j - 1], i, j - 1, visit))
                        addEdge(Tiles, i, j, i, j - 1, size);
                }

    }

    public boolean checkNeighborForTunneler(Tile tile1, Tile tile2) {
        if (tile1.getTypeOfTile().isWater() || tile2.getTypeOfTile().isWater())
            return false;
        return true;
    }
    public boolean checkNeighbor(Tile tile1, int x1, int y1, Tile tile2, int x2, int y2, boolean[] visit) {

        //TODO پل آبی را برسی کن

        if (((tile1.getBuilding() instanceof Wall) || (tile1.getBuilding() instanceof Stairs) ||
                (tile1.getBuilding() instanceof Gatehouse) || (tile1.getBuilding() instanceof CastleBuilding)) &&
                ((tile2.getBuilding() instanceof Wall) || (tile2.getBuilding() instanceof Stairs) ||
                        (tile2.getBuilding() instanceof Gatehouse) || (tile2.getBuilding() instanceof CastleBuilding))) {
            return true;
        } else if (((tile1.getBuilding() instanceof Stairs || tile1.getBuilding() == null)) &&
                (tile2.getBuilding() instanceof Stairs || tile2.getBuilding() == null)) {
            return true;
        } else if (tile1.getBuilding() == null && tile1.getTypeOfTile().getCanCross() &&
                tile2.getBuilding() == null && tile2.getTypeOfTile().getCanCross()) {
            return true;
        } else if (tile1.getBuilding() instanceof Gatehouse && tile1.getBuilding().getEmpire().equals(empire) &&
                tile2.getBuilding() == null && tile2.getTypeOfTile().getCanCross()) {
            if (((Gatehouse)( tile1.getBuilding())).getClosed()) {
                int size = (int) Math.sqrt(visit.length);
                visit[x1 * size + y1] = true;
            }
            return true;
        } else if (tile2.getBuilding() instanceof Gatehouse && tile2.getBuilding().getEmpire().equals(empire) &&
                tile1.getBuilding() == null && tile1.getTypeOfTile().getCanCross()) {
            if (((Gatehouse)((Gatehouse) tile2.getBuilding())).getClosed()) {
                int size = (int) Math.sqrt(visit.length);
                visit[x2 * size + y2] = true;
            }
            return true;
        }

        if ((tile1.getBuilding() instanceof Wall && ((Wall) tile1.getBuilding()).getHaveLadder()) ||
        tile2.getBuilding() instanceof Wall && ((Wall) tile2.getBuilding()).getHaveLadder()) {
            return true;
        }
        return false;
    }

//    public static void main(String[] args) {
//        Tile[][] tiles = new Tile[20][];
//        for (int i = 0; i < 20; i++) {
//            tiles[i] = new Tile[20];
//            for (int j = 0; j < 20; j++) {
//                tiles[i][j] = new Tile();
//            }
//        }
//
//        BestPath bestPath = new BestPath(new Empire(null, null));
//        for (Integer x : bestPath.input(tiles, 0, 0, 10, 5)) {
//            System.out.println("x : " + x/ 20 + " | y : " + x % 20);
//        }
//
//    }
}
