
package CricEdge.model;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ImageTextCell extends ListCell<Product> {
    
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.CANADA);

    @Override
    protected void updateItem(Product item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null); // doesn't show anything
        } else {
            // create the cell
            HBox hbox = new HBox(8.0); // Gap between controls
            hbox.setAlignment(Pos.CENTER_RIGHT);

            // set cover image
            ImageView coverImageView = new ImageView(new Image(item.getImage()));
            coverImageView.setPreserveRatio(false);
            coverImageView.setFitHeight(50.0);
            coverImageView.setFitWidth(50.0);

            // set text
            Label title = new Label(item.getProductName());
            title.setFont(Font.font("System", FontWeight.BOLD, 12));
            title.setTextAlignment(TextAlignment.LEFT);
            
            Label price = new Label();
            String currencyPrice = currencyFormatter.format(item.getPrice());
            price.setText(currencyPrice);
            price.setFont(Font.font("System", FontWeight.BOLD, 12));
                                   
            Pane pane = new Pane();
                        
            hbox.getChildren().addAll(coverImageView, title, pane, price);
            HBox.setHgrow(pane, Priority.ALWAYS);
            setGraphic(hbox);
            setPrefWidth(USE_PREF_SIZE);
        }
    }
    
}
