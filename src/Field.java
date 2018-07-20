import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

class Field extends Button {

    private boolean win = false;
    private final int vertical;
    private final int horizontal;
    private final boolean mine;
    private boolean used;
    boolean flagged;
    private int bombs;

    Field(int vertical, int horizontal, boolean mine, Field[][] fields) {
        super();
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.mine = mine;
        bombs = 0;
        setOnAction(event -> {
            if (!flagged) {
                if (mine) {
                    gameOver();
                } else {
                    // Make a field activated
                    setDisable(true);
                    used = true;

                    // Get the number of bombs nearby
                    calcBombs(fields);

                    // Remove all the nearby ones that can't be a bomb because it has 0 bombs nearby.
                    if (bombs == 0) {
                        //Top
                        if (vertical != 0) {
                            int vertical2 = this.vertical - 1;
                            fields[vertical2][horizontal].fire();
                            // Top Left
                            if (horizontal != 0) {
                                fields[vertical2][horizontal - 1].fire();
                            }
                            // Top Right
                            if (horizontal < fields[vertical2].length - 1) {
                                fields[vertical2][horizontal + 1].fire();
                            }
                        }
                        //Bottom
                        if (vertical < fields.length - 1) {
                            int vertical2 = this.vertical + 1;
                            fields[vertical2][horizontal].fire();
                            // Bottom Left
                            if (horizontal != 0) {
                                fields[vertical2][horizontal - 1].fire();
                            }
                            // Bottom Right
                            if (horizontal < fields[vertical2].length - 1) {
                                fields[vertical2][horizontal + 1].fire();
                            }
                        }
                        //Right
                        if (horizontal < fields[vertical].length - 1) {
                            fields[vertical][horizontal + 1].fire();
                        }
                        //Left
                        if (horizontal != 0) {
                            fields[vertical][horizontal - 1].fire();
                        }
                    } else {
                        setText(String.valueOf(bombs));
                    }

                    // Set win to true, will become false if needed.
                    win = true;
                    for (Field field : Game.playField.safe) {
                        if (!field.used) {
                            win = false;
                        }
                    }
                    if (win) Win();
                }
            }
        });
    }

    private void calcBombs(Field[][] fields) {
        //Top
        if (vertical != 0) {
            int vertical = this.vertical - 1;
            bombs = fields[vertical][horizontal].mine ? bombs + 1 : bombs;
            // Top Left
            if (horizontal != 0) {
                bombs = fields[vertical][horizontal - 1].mine ? bombs + 1 : bombs;
            }
            // Top Right
            if (horizontal < fields[vertical].length - 1) {
                bombs = fields[vertical][horizontal + 1].mine ? bombs + 1 : bombs;
            }
        }
        //Bottom
        if (vertical < fields.length - 1) {
            int vertical = this.vertical + 1;
            bombs = fields[vertical][horizontal].mine ? bombs + 1 : bombs;
            // Bottom Left
            if (horizontal != 0) {
                bombs = fields[vertical][horizontal - 1].mine ? bombs + 1 : bombs;
            }
            // Bottom Right
            if (horizontal < fields[vertical].length - 1) {
                bombs = fields[vertical][horizontal + 1].mine ? bombs + 1 : bombs;
            }
        }
        //Right
        if (horizontal < fields[vertical].length - 1) {
            bombs = fields[vertical][horizontal + 1].mine ? bombs + 1 : bombs;
        }
        //Left
        if (horizontal != 0) {
            bombs = fields[vertical][horizontal - 1].mine ? bombs + 1 : bombs;
        }
    }

    private void gameOver() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You lost you dickhead", ButtonType.CLOSE);
        alert.setTitle("LOSER");
        alert.show();
        Game.newGame();
    }

    private void Win() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You won!", ButtonType.FINISH);
        alert.setTitle("WINNER");
        alert.show();
        Game.newGame();
    }

    boolean isMine() {
        return mine;
    }
}
