package hellofx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;


public class HelloFX extends Application {

    MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        HBox mainButton = new HBox();


        Button playButton = new Button("Play");
        playButton.setOnAction(event -> {
            if (mediaPlayer == null) {
                String bip = "test.mp3";
                Media hit = new Media(new File(bip).toURI().toString());
                mediaPlayer = new MediaPlayer(hit);
            }
            mediaPlayer.play();
        });

        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(event -> {
            mediaPlayer.pause();
        });

        ScrollBar scrollBar = new ScrollBar();

        scrollBar.setOnMouseClicked(event -> {
            double value = scrollBar.getValue() / 100;
            System.out.println(value);
            Duration endTime = mediaPlayer.getStopTime();
            mediaPlayer.seek(Duration.millis(value * endTime.toMillis()));

        });

        mainButton.getChildren().addAll(playButton, pauseButton);
        root.setCenter(mainButton);
        root.setBottom(scrollBar);

        Scene scene = new Scene(root, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MusicPlayer");
        primaryStage.show();

        new Timer().schedule(
                new TimerTask() {

                    @Override
                    public void run() {
                        if (mediaPlayer != null){
                            Duration currentTime = mediaPlayer.getCurrentTime();
                            Duration endTime = mediaPlayer.getStopTime();
                            double progress = currentTime.toMillis() / endTime.toMillis();
                            scrollBar.setValue(progress * 100);

                        }
                    }
                }, 0, 500
        );
    }

    public static void main(String[] args) {
        launch();
    }


}