//Bludiste V1.1.2
package bludiste;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Bludiste extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage =stage;
        Parent root = FXMLLoader.load(getClass().getResource("Bludiste.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Bludiště "+CHNGLG.Version);
        stage.show();
    }
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl","True");
        System.setProperty("sun.java2d.d3d","True");
        System.setProperty("sun.java2d.noddraw","True");
        launch(args);}
    @Override
    public void stop() throws Exception {super.stop();}
}