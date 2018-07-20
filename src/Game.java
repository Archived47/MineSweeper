import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Game extends Application {

    static PlayField playField;
    private static Stage stage;
    private static List<String> items;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        newGame();

        primaryStage.show();
        primaryStage.setTitle("Minesweeper Classic style, by Lucas");
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }

    static void newGame() {
        Pane root = new Pane();
        VBox vbox = new VBox(10);

        Scene scene = new Scene(root, 400, 400);

        Text text = new Text("Choose a size");
        ComboBox<String> fields = new ComboBox<>();
        items = new ArrayList<>();
        //TODO change this to a config file, so you can add custom modes
        for (int i = 1; i <= 5; i++) {
            items.add((i * 5) + " x " + (i * 5));
        }
        ObservableList<String> obsItems = FXCollections.observableArrayList(items);
        fields.setItems(obsItems);
        fields.setPrefWidth(200);
        Button confirm = new Button("Confirm");
        confirm.setPrefWidth(200);
        confirm.setOnAction(event -> {
            confirm(fields);
        });

        vbox.getChildren().addAll(text, fields, confirm);
        root.getChildren().addAll(vbox);

        stage.setScene(scene);

        vbox.layoutXProperty().bind(root.widthProperty().divide(4));
        vbox.layoutYProperty().bind(root.heightProperty().divide(4));

        root.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) confirm(fields);
        });
    }

    private static void confirm(ComboBox fields) {
        try {
            int index = fields.getSelectionModel().getSelectedIndex();
            String string = items.get(index);
            byte[] array = string.getBytes();
            boolean switched = false;
            StringBuilder sVertical = new StringBuilder();
            StringBuilder sHorizontal = new StringBuilder();
            for (byte bt : array) {
                if ((char)bt == ' ' || (char)bt == 'x') {
                    switched = true;
                } else {
                    if (switched) {
                        sHorizontal.append((char) bt);
                    } else {
                        sVertical.append((char) bt);
                    }
                }
            }
            int vertical = Integer.parseInt(sVertical.toString());
            int horizontal = Integer.parseInt(sHorizontal.toString());
            int nrFields = vertical * horizontal;

            playField = new PlayField(vertical, horizontal, nrFields);
            stage.setScene(new Scene(playField));
        } catch (NumberFormatException ex) {
            System.out.println(ex);
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.show();
        }
    }
}
