package com.group4;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CardCheckoutScreenController {

   double TAX_RATE = 0.08;

   @FXML private Label subtotalLbl;
   @FXML private Label taxLbl;
   @FXML private Label totalLbl;
   @FXML private TextField cardNumberInput;
   @FXML private TextField ccvInput;
   @FXML private Button backBtn;
   @FXML private Button payBtn;

   @FXML
   public void initialize() {
      subtotalLbl.setText(Util.convertMoneyToString(Util.getSubTotal()));
      taxLbl.setText(Util.convertMoneyToString(Util.getTax()));
      totalLbl.setText(Util.convertMoneyToString(Util.getTotal()));
   }   

   @FXML
   private void backAction() throws IOException {
      App.setRoot("checkoutScreen");
   }

   @FXML
   private void doneAction() throws IOException {
      App.getOrder().clearItems();
      App.setRoot("primary");
   }

   @FXML
   private void checkFilled() throws IOException {

      String cardNum = cardNumberInput.getText();
      String ccv = ccvInput.getText();

      if((cardNum.length() == 16) && ccv.length() == 3) {
         payBtn.setDisable(false);
      } else {
         payBtn.setDisable(true);
      }
   }
}
