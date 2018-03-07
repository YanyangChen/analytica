package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        String str = "Hello I'm your String";
//        String[] splited = str.split(" ");
//        System.out.println(splited[1]);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("News Tittles");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
