package sample;

public class Feld {
    boolean isMine;
    boolean opened;
    int neighboredMines;
    boolean flag;

    public Feld() {
        this.isMine = false;
        this.opened = false;
        this.neighboredMines = 0;
        this.flag = false;
    }
}