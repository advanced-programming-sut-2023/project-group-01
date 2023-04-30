package org.example.view.enums;

public enum ShowMapAsciiArt {
    S1("..###"), S2(".#..."), S3("..#.."), S4("...#."), S5("###.."),
    W1("#...#"), W2("#...#"), W3("#.#.#"), W4("##.##"), W5("#...#"),
    B1("####."), B2("#...#"), B3("####."), B4("#...#"), B5("####."),
    T1("#####"), T2("..#.."), T3("..#.."), T4("..#.."), T5("..#..");
    private final String asciiArt;

    ShowMapAsciiArt(String asciiArt){
        this.asciiArt = asciiArt;
    }

    @Override
    public String toString() {
        return asciiArt;
    }
}
