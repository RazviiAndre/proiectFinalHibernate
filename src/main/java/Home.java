import controller.DBApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Home extends Application {


    public static void main(String[] args) {
        launch(args);


//        DBApp app = new DBApp();
        //      app.insertUser();
//        app.updateUser();
//        app.deleteUser();
//        app.testMerge();

//        app.checkOneToOne();
//        app.checkOneToMany();

    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {


//        DBApp dbApp = new DBApp();
//      dbApp.insertGame("qwe","test","test");
//        System.out.println(dbApp.getScore("root"));
        //        System.out.println(dbApp.getScoreID("qwe"));
//        System.out.println(dbApp.getScore("qwe"));
//        System.out.println(dbApp.getEmail("root"));
//        System.out.println(dbApp.checkPlayerID(1));
//        System.out.println(dbApp.getEmail("qwe"));
//        System.out.println(dbApp.validatePassword("qwe","qwe"));
//        System.out.println(dbApp.validateUsername("root"));
//        System.out.println(dbApp.validateEmail("rootz@yahoo.com"));



        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.getIcons().add(new Image("/img/snake.png"));
        primaryStage.show();
    }

}
