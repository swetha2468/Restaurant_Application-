package com.group4;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;

public class CheckoutScreenController {
   
   @FXML private Pane currOrderHolder;
   @FXML private TreeView<String> currOrderTree;
   @FXML private Label subtotalLbl;
   @FXML private Label taxLbl;
   @FXML private Label totalLbl;


   @FXML
   public void initialize() {
      // System.out.println("initialize");
      currOrderTree = new TreeView<>();

      Order currOrder = App.getOrder();
      
      subtotalLbl.setText(Util.convertMoneyToString(Util.getSubTotal()));
      taxLbl.setText(Util.convertMoneyToString(Util.getTax()));
      totalLbl.setText(Util.convertMoneyToString(Util.getTotal()));

      _buildOrderTree();
   }

   @FXML
   private void cashCheckout() throws IOException {
      App.setRoot("cashCheckoutScreen");
   }

   @FXML
   private void creditCheckout() throws IOException {
      App.setRoot("cardCheckoutScreen");
   }

   @FXML
   private void debitCheckout() throws IOException {
      App.setRoot("cardCheckoutScreen");
   }

   @FXML
   private void cancelCheckout() throws IOException {
      App.setRoot("primary");
   }

   void _buildOrderTree()
   {
      TreeItem<String> root = new TreeItem<String>("root");
      for(Item item : App.getOrder().getItems())
      {
         String itemName = item.getConf().getName() + " - " + Util.convertMoneyToString(item.getTotalCost());

         String base = item.getBase();
         String baseName = base + " - " + Util.convertMoneyToString(item.getConf().getBase_varients().get(base));
         
         TreeItem<String> itemTree = new TreeItem<String>(itemName);
         itemTree.setExpanded(true);

         TreeItem<String> baseTree = new TreeItem<String>(baseName);
         itemTree.getChildren().add(baseTree);


         for(String variant : item.getVarients())
         {
            String variantName = variant + " - " + Util.convertMoneyToString(item.getConf().getAdditions_varients().get(variant));
            TreeItem<String> variantTree = new TreeItem<String>(variantName);
            itemTree.getChildren().add(variantTree);
         }
         root.getChildren().add(itemTree);
      }
      currOrderTree.setRoot(root);
      currOrderTree.setShowRoot(false);
      currOrderHolder.getChildren().add(currOrderTree);
   }
    
}
