package CricEdge;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import CricEdge.Main;
import CricEdge.model.Order;
import CricEdge.model.OrderQueries;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class OrdersController {
    
    private OrderQueries orderQueries;
    private List<Order> results;
    private ObservableList<Order> orders = FXCollections.observableArrayList();
    public BorderPane mainBorderPaneForOrderDetailUse;
    
    @FXML private TableView<Order> orderTable;
    @FXML private TableColumn<Order, String> orderNumberCol;
    @FXML private TableColumn<Order, Date> orderDateCol;
    @FXML private TableColumn<Order, String> statusCol;
    @FXML private TableColumn<Order, Double> totalPriceCol;  
    @FXML private TableColumn<Order, Order> viewDetailCol;
    
    int userID = Main.getInstance().getLoggedCustomer().getCustomerID();
    
    public void initialize() {
        orderQueries = new OrderQueries();
        results = orderQueries.getAllOrders(userID);
        
        orders.addAll(results);
        orderTable.setItems(orders);
        
        orderTable.setSelectionModel(null);
        orderTable.setPlaceholder(new Label("Your order history is empty."));
        
        orderNumberCol.setCellValueFactory(new PropertyValueFactory<>("OrderNumber"));
        orderDateCol.setCellValueFactory(new PropertyValueFactory<>("OrderDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
        
        viewDetailCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        viewDetailCol.setCellFactory(param -> new TableCell<Order, Order>(){
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if(order == null) {
                    setGraphic(null);
                    return;
                }
                Hyperlink viewDetail = new Hyperlink("View Detail");
                viewDetail.setStyle("-fx-text-fill: #428BCA");
                viewDetail.getStyleClass().add("view_detail");
                viewDetail.setOnAction(e -> {
					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderDetail.fxml"));
						VBox orderDetail = loader.load();
						OrderDetailController orderDetailController = loader.getController();
						orderDetailController.setOrderDetailPage(order);
						mainBorderPaneForOrderDetailUse.setCenter(orderDetail);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                });
                setGraphic(viewDetail);
            }
        });
        
        
    }
}
