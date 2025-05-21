package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
        private VBox rootVBox; // add fx:id="rootVBox" in your VBox in FXML

        @FXML

        public void setImage(Image image) {
            newsImageView.setImage(image);

            double iw = image.getWidth();
            double ih = image.getHeight();

            double vw = newsImageView.getFitWidth();
            double vh = newsImageView.getFitHeight();

            // Calculate scale to fill imageView area
            double scale = Math.max(vw / iw, vh / ih);

            // Calculate size of viewport in image pixels
            double viewportWidth = vw / scale;
            double viewportHeight = vh / scale;

            // Crop viewport centered
            double viewportX = (iw - viewportWidth) / 2;
            double viewportY = (ih - viewportHeight) / 2;

            Rectangle2D viewport = new Rectangle2D(viewportX, viewportY, viewportWidth, viewportHeight);
            newsImageView.setViewport(viewport);

            newsImageView.setPreserveRatio(false);
        }


    public void setData(String title, String summary, String scholarshipTagName, String imagePath) {
            titleLabel.setText(title);
            summaryLabel.setText(summary);
            scholarshipTagLabel.setText("Scholarship Tag: " + scholarshipTagName);



            if (imagePath != null && !imagePath.isEmpty()) {
                // Convert Windows file path to URL
                String imageUrl = imagePath;
                if (imagePath.matches("^[a-zA-Z]:\\\\.*")) {  // crude check for Windows path
                    imageUrl = "file:///" + imagePath.replace("\\", "/");
                }
                Image image = new Image(imageUrl);
                newsImageView.setImage(image);
            }
            newsImageView.setFitWidth(200);
            newsImageView.setFitHeight(150);
            newsImageView.setPreserveRatio(false);  // false so image fills the box without stretching proportionally
            newsImageView.setSmooth(true);
        }

    }


