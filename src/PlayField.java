import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class PlayField extends Pane {

    List<Field> safe = new ArrayList<>();

    PlayField(int vertical, int horizontal, int nrFields) {
        Random random = new Random();
        boolean mine = false;

        Field[][] fields = new Field[vertical][horizontal];
        int fieldSize = 30;
        setMinSize(horizontal * fieldSize, vertical * fieldSize);

        // Initialize all the fields
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                mine = random.nextFloat() <= 0.3f;
                fields[i][j] = new Field(i, j, mine, fields);
                mine = false;
            }
        }
        // Give the fields their data
        for (int i = 0; i < vertical; i++) {
            for (int j = 0; j < horizontal; j++) {
                if (!fields[i][j].isMine()) safe.add(fields[i][j]);
//                fields[i][j].calcBombs(fields);
                getChildren().add(fields[i][j]);
                int x = i;
                int y = j;
                fields[i][j].setOnMousePressed(event -> {
                    Field field = fields[x][y];
                    if (event.getButton() == MouseButton.SECONDARY) {
                        field.flagged = !field.flagged;
                        // TODO change to image
                        if (field.flagged) {
                            field.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                        } else {
                            field.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                    }
                });
                fields[i][j].setPrefSize(fieldSize, fieldSize);
                fields[i][j].setLayoutX(j * fields[i][j].getPrefWidth());
                fields[i][j].setLayoutY(i * fields[i][j].getPrefHeight());
                fields[i][j].setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                fields[i][j].setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        }
    }

}
