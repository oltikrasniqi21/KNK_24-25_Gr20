package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NewsModalController {

    @FXML private VBox modalRoot;
    @FXML private ImageView modalImageView;
    @FXML private Label modalTitleLabel;
    @FXML private Label modalSummaryLabel;
    @FXML private Label modalTagLabel;

    StudentHomePageController studentHomePageController = new StudentHomePageController();



    public void setModalData(String title, String summary, String tag, String imagePath) {
        modalTitleLabel.setText(title);
        modalSummaryLabel.setText(summary);
        modalTagLabel.setText("Scholarship Tag: " + tag);

        if (imagePath != null && !imagePath.isEmpty()) {
            String imageUrl = imagePath;
            if (imagePath.matches("^[a-zA-Z]:\\\\.*")) {
                imageUrl = "file:///" + imagePath.replace("\\", "/");
            }
            Image image = new Image(imageUrl);
            modalImageView.setImage(image);
            modalImageView.setPreserveRatio(false);
            modalImageView.setSmooth(true);
        }
    }

}