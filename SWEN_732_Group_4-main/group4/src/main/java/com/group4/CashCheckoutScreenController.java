package com.group4;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.lang.Math; 

public class CashCheckoutScreenController {


   @FXML private Label subtotalLbl;
   @FXML private Label taxLbl;
   @FXML private Label totalLbl;
   @FXML private TextField cashTenderedInput;
   @FXML private Label changeLbl;
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
   private void calcChange() throws IOException {
      int total = Util.getTotal();
      double tender = 0.0;
      try {
         String text = cashTenderedInput.getText();
         tender = Double.parseDouble(text);
      } catch (Exception e) {
         System.out.println(e);
         tender = 0.0;
      }

      int tenderInt = (int)(tender * 100);
      int change = tenderInt - total;

      if(change >= 0)
      {
         String changeText = Util.convertMoneyToString(change);
         changeLbl.setText(changeText);
         payBtn.setDisable(false);
      }
      else
      {
         changeLbl.setText("N/A");
         payBtn.setDisable(true);
      }

   }
}
