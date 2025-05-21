package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NewsCardController {

        @FXML
        private ImageView newsImageView;

        @FXML
        private Label titleLabel;

        @FXML
        private Label summaryLabel;

        @FXML
        private Label scholarshipTagLabel;

        @FXML
        private VBox rootVBox;

        private String currentImagePath;

        @FXML
        public void initialize(){
        rootVBox.setOnMouseClicked(e -> {
        if (e.getClickCount() == 1) {
            openModal();
        }
    });
        }
    private void openModal() {
        System.out.println("Image path for modal: " + currentImagePath);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/news_modal.fxml"));
            VBox modalRoot = loader.load();

            NewsModalController controller = loader.getController();
            controller.setModalData(
                    titleLabel.getText(),
                    summaryLabel.getText(),
                    scholarshipTagLabel.getText().replace("Scholarship Tag: ", ""),
                    currentImagePath
            );

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(modalRoot));
            modalStage.setTitle("News Details");
            modalStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setData(String title, String summary, String scholarshipTagName, String imagePath) {
            titleLabel.setText(title);
            summaryLabel.setText(summary);
            scholarshipTagLabel.setText(scholarshipTagName);



            if (imagePath != null && !imagePath.isEmpty()) {
                String imageUrl = imagePath;
                if (imagePath.matches("^[a-zA-Z]:\\\\.*")) {  // crude check for Windows path
                    imageUrl = "file:///" + imagePath.replace("\\", "/");
                }
                Image image = new Image(imageUrl);
                newsImageView.setImage(image);
            }


            newsImageView.setPreserveRatio(false);
            newsImageView.setSmooth(true);

            this.currentImagePath = imagePath;

    }



    }


