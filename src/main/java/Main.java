import DAO.User;
import controller.Controller;
import controller.DBApp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.List;

public class Main extends Application {


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
//        System.out.println(dbApp.getEmail("root"));
//        System.out.println(dbApp.checkPlayerID(1));
//        System.out.println(dbApp.getEmail("qwe"));
//        System.out.println(dbApp.validatePassword("qwe","qwe"));
//        System.out.println(dbApp.validateUsername("root"));
//        System.out.println(dbApp.validateEmail("rootz@yahoo.com"));



        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

}
