package org.example;

import com.google.gson.Gson;
import org.example.model.Data;

import java.io.FileWriter;

public class readyMap {

    public static void main(String[] args) throws Exception {
        FileWriter fileWriter = new FileWriter("my map.txt");
        // Meadow
        for (int i = 0; i < 40; i++)
            for (int j = 0; j < 400; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t meadow\n");
        for (int i = 180; i < 220; i++)
            for (int j = 0; j < 400; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t meadow\n");
        for (int i = 360; i < 400; i++)
            for (int j = 0; j < 400; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t meadow\n");
        for (int i = 0; i < 400; i++)
            for (int j = 0; j < 40; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t meadow\n");
        for (int i = 0; i < 400; i++)
            for (int j = 180; j < 220; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t meadow\n");
        for (int i = 0; i < 400; i++)
            for (int j = 360; j < 400; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t meadow\n");
        for (int i = 0; i < 400; i++)
            for (int j = 0; j < 40; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t meadow\n");

        // IroneMine and StoneMine
        for (int i = 90; i < 100; i++)
            for (int j = 0; j < 10; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t ironMine\n");
        for (int i = 100; i < 110; i++)
            for (int j = 0; j < 10; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t stoneMine\n");
        for (int i = 290; i < 300; i++)
            for (int j = 0; j < 10; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t ironMine\n");
        for (int i = 300; i < 310; i++)
            for (int j = 0; j < 10; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t stoneMine\n");

        for (int i = 0; i < 10; i++)
            for (int j = 90; j < 100; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t ironMine\n");
        for (int i = 0; i < 10; i++)
            for (int j = 100; j < 110; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t stoneMine\n");
        for (int i = 390; i < 400; i++)
            for (int j = 90; j < 100; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t ironMine\n");
        for (int i = 390; i < 400; i++)
            for (int j = 100; j < 110; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t stoneMine\n");

        for (int i = 0; i < 10; i++)
            for (int j = 290; j < 300; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t ironMine\n");
        for (int i = 0; i < 10; i++)
            for (int j = 300; j < 310; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t stoneMine\n");
        for (int i = 390; i < 400; i++)
            for (int j = 290; j < 300; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t ironMine\n");
        for (int i = 390; i < 400; i++)
            for (int j = 300; j < 310; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t stoneMine\n");

        for (int i = 90; i < 100; i++)
            for (int j = 390; j < 400; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t ironMine\n");
        for (int i = 100; i < 110; i++)
            for (int j = 390; j < 400; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t stoneMine\n");
        for (int i = 290; i < 300; i++)
            for (int j = 390; j < 400; j++)
                fileWriter.write("set texture -x " + i + " -y " + j + " -t ironMine\n");
        for (int i = 300; i < 310; i++)
            for (int j = 390; j < 400; j++)
                System.out.println("set texture -x " + i + " -y " + j + " -t stoneMine\n");

//        // TODO set the tree here
//        for (int i = 195; i < 205; i++)
//            for (int j = 195; j < 205; j++)
//                fileWriter.write();
    }


}
