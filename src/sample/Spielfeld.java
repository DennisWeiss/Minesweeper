package sample;

public class Spielfeld {
    Feld[][] spielfeld;
    int xcoor;
    int ycoor;
    boolean lost = false;
    boolean won = false;
    int points;
    public Spielfeld(int columns, int rows, int mines) {
        this.spielfeld = new Feld[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                this.spielfeld[i][j] = new Feld();
            }
        }
        for (int i = 0; i < mines; i++) {
            placeBomb(columns, rows, mines, this.spielfeld);
        }
    }


    void placeBomb(int columns, int rows, int mines, Feld[][] spielfeld) {
        int x = (int)Math.floor(columns * Math.random());
        int y = (int)Math.floor(rows * Math.random());
        if (spielfeld[x][y].isMine) {
            placeBomb(columns, rows, mines, spielfeld);
        } else {
            spielfeld[x][y].isMine = true;
            try {
                spielfeld[x-1][y-1].neighboredMines++;
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                spielfeld[x][y-1].neighboredMines++;
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                spielfeld[x+1][y-1].neighboredMines++;
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                spielfeld[x-1][y].neighboredMines++;
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                spielfeld[x+1][y].neighboredMines++;
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                spielfeld[x-1][y+1].neighboredMines++;
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                spielfeld[x][y+1].neighboredMines++;
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                spielfeld[x+1][y+1].neighboredMines++;
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
    }


    void linksKlick(int x, int y, int columns, int rows, int mines) {
        if (!this.spielfeld[x][y].opened && !this.lost) {
            if (this.spielfeld[x][y].isMine) {
                lose(x, y, columns, rows);
            } else {
                if (this.spielfeld[x][y].neighboredMines == 0) {
                    open(x, y);
                } else {
                    this.spielfeld[x][y].opened = true;
                    this.points++;
                }
            }
        }
        if (this.points == columns * rows - mines) {
            win();
        }
    }

    void open(int x, int y) {
        if (!this.spielfeld[x][y].opened && !this.spielfeld[x][y].isMine) {
            this.spielfeld[x][y].opened = true;
            this.points++;
            try {
                if (this.spielfeld[x-1][y-1].neighboredMines == 0) {
                    open(x-1, y-1);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                if (this.spielfeld[x][y-1].neighboredMines == 0) {
                    open(x, y-1);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                if (this.spielfeld[x+1][y-1].neighboredMines == 0) {
                    open(x+1, y-1);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                if (this.spielfeld[x-1][y].neighboredMines == 0) {
                    open(x-1, y);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                if (this.spielfeld[x+1][y].neighboredMines == 0) {
                    open(x+1, y);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                if (this.spielfeld[x-1][y+1].neighboredMines == 0) {
                    open(x-1, y+1);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                if (this.spielfeld[x][y+1].neighboredMines == 0) {
                    open(x, y+1);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            try {
                if (this.spielfeld[x+1][y+1].neighboredMines == 0) {
                    open(x+1, y+1);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }

            if (this.spielfeld[x][y].neighboredMines == 0) {
                try {
                    if (!this.spielfeld[x-1][y-1].isMine && !this.spielfeld[x-1][y-1].opened) {
                        this.spielfeld[x-1][y-1].opened = true;
                        this.points++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                try {
                    if (!this.spielfeld[x][y-1].isMine && !this.spielfeld[x][y-1].opened) {
                        this.spielfeld[x][y-1].opened = true;
                        this.points++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                try {
                    if (!this.spielfeld[x+1][y-1].isMine && !this.spielfeld[x+1][y-1].opened) {
                        this.spielfeld[x+1][y-1].opened = true;
                        this.points++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                try {
                    if (!this.spielfeld[x-1][y].isMine && !this.spielfeld[x-1][y].opened) {
                        this.spielfeld[x-1][y].opened = true;
                        this.points++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                try {
                    if (!this.spielfeld[x+1][y].isMine && !this.spielfeld[x+1][y].opened) {
                        this.spielfeld[x+1][y].opened = true;
                        this.points++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                try {
                    if (!this.spielfeld[x-1][y+1].isMine && !this.spielfeld[x-1][y+1].opened) {
                        this.spielfeld[x-1][y+1].opened = true;
                        this.points++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                try {
                    if (!this.spielfeld[x][y+1].isMine && !this.spielfeld[x][y+1].opened) {
                        this.spielfeld[x][y+1].opened = true;
                        this.points++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                try {
                    if (!this.spielfeld[x+1][y+1].isMine && !this.spielfeld[x+1][y+1].opened) {
                        this.spielfeld[x+1][y+1].opened = true;
                        this.points++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }

    void lose(int x, int y, int columns, int rows) {
        if (!won) {
            this.lost = true;
            this.xcoor = x;
            this.ycoor = y;
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < rows; j++) {
                    if (this.spielfeld[i][j].isMine) {
                        this.spielfeld[i][j].neighboredMines = 0;
                    }
                    this.spielfeld[i][j].opened = true;
                }
            }
        }
    }

    void win() {
        won = true;
    }

    void reset(int columns, int rows, int mines) {
        this.xcoor = 0;
        this.ycoor = 0;
        this.lost = false;
        this.won = false;
        this.points = 0;
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                this.spielfeld[i][j].isMine = false;
                this.spielfeld[i][j].opened = false;
                this.spielfeld[i][j].neighboredMines = 0;
                this.spielfeld[i][j].flag = false;
            }
        }
        for (int i = 0; i < mines; i++) {
            placeBomb(columns, rows, mines, this.spielfeld);
        }
    }
}