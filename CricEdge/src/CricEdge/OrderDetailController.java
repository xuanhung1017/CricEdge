package CricEdge;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import CricEdge.model.PurchasedProductQueries;
import CricEdge.model.PurchasedProduct;
import CricEdge.model.Order;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OrderDetailController {
	
	private PurchasedProductQueries PurchasedProductQueries;
    private List<PurchasedProduct> results;
    private ObservableList<PurchasedProduct> purchasedProducts = FXCollections.observableArrayList();
	
	@FXML private Label orderNumberLabel;
    @FXML private Label statusLabel;
    @FXML private Label subtotalLabel;
    @FXML private Label taxLabel;
    @FXML private Label totalLabel;

	@FXML private TableView<PurchasedProduct> orderDetailTable;
    @FXML private TableColumn<PurchasedProduct, PurchasedProduct> imageCol;
    @FXML private TableColumn<PurchasedProduct, String> productNameCol;
    @FXML private TableColumn<PurchasedProduct, String> sizeCol;
    @FXML private TableColumn<PurchasedProduct, Double> priceCol;
    @FXML private TableColumn<PurchasedProduct, Integer> quantityCol;  
    @FXML private TableColumn<PurchasedProduct, Double> subtotalCol;
    
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.CANADA);
    Double subtotal, tax, total;
    
	public void setOrderDetailPage(Order order) {
		orderNumberLabel.setText(order.getOrderNumber());
		statusLabel.setText(order.getStatus());
		
		PurchasedProductQueries = new PurchasedProductQueries();
        results = PurchasedProductQueries.getAllPurchasedProducts(order.getOrderNumber());
        
        purchasedProducts.addAll(results);
        orderDetailTable.setItems(purchasedProducts);
        
        orderDetailTable.setSelectionModel(null);
        orderDetailTable.setPlaceholder(new Label("Your order history is empty."));
        
        imageCol.setCellValueFactory(img -> new ReadOnlyObjectWrapper<>(img.getValue()));
        imageCol.setCellFactory(img -> new TableCell<PurchasedProduct, PurchasedProduct>(){
            private final ImageView coverImageView = new ImageView();
            @Override 
            protected void updateItem(PurchasedProduct purchasedProduct, boolean empty) {
                super.updateItem(purchasedProduct, empty);
                if(purchasedProduct == null) {
                    setGraphic(null);
                    return;
                }
                coverImageView.setImage(new Image(purchasedProduct.getImage()));
                coverImageView.setFitHeight(100);
                coverImageView.setFitWidth(100);
                setGraphic(coverImageView);
            }
        });
        
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        
        sizeCol.setCellValueFactory(new PropertyValueFactory<>("Size"));
        
        priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        priceCol.setCellFactory(col -> new TableCell<PurchasedProduct, Double>(){
            @Override
            public void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if(empty) {
                    setGraphic(null);
                } else {
                    String currencyPrice = currencyFormatter.format(price);
                    Label priceLabel = new Label(currencyPrice);
                    setGraphic(priceLabel);
                }
            }
        });
        
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("Subtotal"));
        subtotalCol.setCellFactory(col -> new TableCell<PurchasedProduct, Double>(){
            @Override
            public void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if(empty) {
                    setGraphic(null);
                } else {
                    String currencySubtotal = currencyFormatter.format(price);
                    Label subtotalLabel = new Label(currencySubtotal);
                    setGraphic(subtotalLabel);
                }
            }
        });
              
		subtotal = 0.0;
		for (PurchasedProduct purchasedProduct : orderDetailTable.getItems()) {
            subtotal = subtotal + purchasedProduct.getSubtotal();
        }
        String currencySubtotal = currencyFormatter.format(subtotal);
        subtotalLabel.setText(currencySubtotal);
        
        tax = subtotal * 12 / 100;
        String currencyTax = currencyFormatter.format(tax);
        taxLabel.setText(currencyTax);
        
        total = subtotal + tax;
        String currencyTotal = currencyFormatter.format(total);
        totalLabel.setText(currencyTotal);  
	}
}
