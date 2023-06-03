package org.example.view.graphicView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.Main;
import org.example.model.Data;
import org.example.model.User;
import org.example.view.LoginMenu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static org.example.controller.mainMenuController.ProfileMenuController.currentUser;

public class ScoreBoard extends Application {
    public ImageView image1;
    public Label rank1;
    public ImageView image3;
    public Label rank3;
    public ImageView image5;
    public Label rank5;
    public ImageView image7;
    public Label rank7;
    public ImageView image9;
    public Label rank9;
    public Label rank2;
    public ImageView image2;
    public Label rank4;
    public ImageView image4;
    public Label rank6;
    public ImageView image6;
    public Label rank8;
    public ImageView image8;
    public Label rank10;
    public ImageView image10;
    public VBox leftUsernameVBox;
    public VBox rightUsernameVBox;

    private int start;

    @Override
    public void start(Stage stage) throws Exception {
        start = 0;
        stage = Main.stage;
        URL url = LoginMenu.class.getResource("/FXML/ScoreBoard.fxml");
        AnchorPane anchorPane = FXMLLoader.load(url);
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        scene.setCursor(new ImageCursor(new Image(Objects.requireNonNull(Main.class.getResource("/Images/SwordIcon.png")).openStream())));
        stage.setTitle("Score Board");
        stage.show();
    }

    public void changeAvatarFromGallery(MouseEvent mouseEvent) throws MalformedURLException {
        ImageView imageView = (ImageView) mouseEvent.getSource();
        if (imageView.getImage()!=null){
            currentUser.setAvatarUrl(new URL(imageView.getImage().getUrl()));
            setData(start);
        }
    }

    public void initialize() throws IOException {
        setData(0);

        checkScroll(leftUsernameVBox);

        checkScroll(rightUsernameVBox);
    }

    private void checkScroll(VBox usernameVBox) {
        usernameVBox.setOnScroll((ScrollEvent event) -> {

            double deltaY = event.getDeltaY();
            if (deltaY > 0){
                if (start>0)
                    start-=1;
            }
            if (deltaY < 0){
                if (start< Data.getUsers().size()-10)
                    start+=1;
            }

            setData(start);
        });
    }

    private void setData(int start){

        Data.getUsers().sort(new User.Sort());
        int count;
        if (Data.getUsers().size()>=10+start){
            count = 10;
        }else count = Data.getUsers().size()-start;

        for (int i=start ; i<start+count;i++){
            if (i==start){
                Circle clip = new Circle(83/2, 83/2, 83/2);
                image1.setClip(clip);
                image1.setImage(new Image(Data.getUsers().get(i).getAvatarUrl().toString()));
                rank1.setText(Data.getUsers().get(i).getUsername()+" | Score : "+Data.getUsers().get(i).getHighScore());
            }else if (i==start+1){
                Circle clip = new Circle(83/2, 83/2, 83/2);
                image2.setClip(clip);
                image2.setImage(new Image(Data.getUsers().get(i).getAvatarUrl().toString()));
                rank2.setText(Data.getUsers().get(i).getUsername()+" | Score : "+Data.getUsers().get(i).getHighScore());
            }else if (i==start+2){
                Circle clip = new Circle(83/2, 83/2, 83/2);
                image3.setClip(clip);
                image3.setImage(new Image(Data.getUsers().get(i).getAvatarUrl().toString()));
                rank3.setText(Data.getUsers().get(i).getUsername()+" | Score : "+Data.getUsers().get(i).getHighScore());
            }else if (i==start+3){
                Circle clip = new Circle(83/2, 83/2, 83/2);
                image4.setClip(clip);
                image4.setImage(new Image(Data.getUsers().get(i).getAvatarUrl().toString()));
                rank4.setText(Data.getUsers().get(i).getUsername()+" | Score : "+Data.getUsers().get(i).getHighScore());
            }else if (i==start+4){
                Circle clip = new Circle(83/2, 83/2, 83/2);
                image5.setClip(clip);
                image5.setImage(new Image(Data.getUsers().get(i).getAvatarUrl().toString()));
                rank5.setText(Data.getUsers().get(i).getUsername()+" | Score : "+Data.getUsers().get(i).getHighScore());
            }else if (i==start+5){
                Circle clip = new Circle(83/2, 83/2, 83/2);
                image6.setClip(clip);
                image6.setImage(new Image(Data.getUsers().get(i).getAvatarUrl().toString()));
                rank6.setText(Data.getUsers().get(i).getUsername()+" | Score : "+Data.getUsers().get(i).getHighScore());
            }else if (i==start+6){
                Circle clip = new Circle(83/2, 83/2, 83/2);
                image7.setClip(clip);
                image7.setImage(new Image(Data.getUsers().get(i).getAvatarUrl().toString()));
                rank7.setText(Data.getUsers().get(i).getUsername()+" | Score : "+Data.getUsers().get(i).getHighScore());
            }else if (i==start+7){
                Circle clip = new Circle(83/2, 83/2, 83/2);
                image8.setClip(clip);
                image8.setImage(new Image(Data.getUsers().get(i).getAvatarUrl().toString()));
                rank8.setText(Data.getUsers().get(i).getUsername()+" | Score : "+Data.getUsers().get(i).getHighScore());
            }else if (i==start+8){
                Circle clip = new Circle(83/2, 83/2, 83/2);
                image9.setClip(clip);
                image9.setImage(new Image(Data.getUsers().get(i).getAvatarUrl().toString()));
                rank9.setText(Data.getUsers().get(i).getUsername()+" | Score : "+Data.getUsers().get(i).getHighScore());
            }else if (i==start+9){
                Circle clip = new Circle(83/2, 83/2, 83/2);
                image10.setClip(clip);
                image10.setImage(new Image(Data.getUsers().get(i).getAvatarUrl().toString()));
                rank10.setText(Data.getUsers().get(i).getUsername()+" | Score : "+Data.getUsers().get(i).getHighScore());
            }
        }
    }

    public void backToProfile(MouseEvent mouseEvent) throws Exception {
        new ProfileMenuApp().start(Main.stage);
    }
}
