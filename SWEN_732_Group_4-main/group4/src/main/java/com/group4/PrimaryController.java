package com.group4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PrimaryController {
    @FXML private GridPane item_catalogue;

    //IAA: Items for current order
    @FXML private Pane current_order_pane;
    @FXML private Label subtotal_lbl;
    @FXML private Label tax_lbl;
    @FXML private Label total_lbl;

    // When the Scene first loads and the window is displayed this method is called automatically.
    @FXML
    public void initialize() {
        // Print output to show function being called any time primary screen shown.
        System.out.println("Primary Controller: Initialize");
        
        try {
            makeItemCatalogue(item_catalogue);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        _updateDollarAmounts();
        _updateCurrentOrder();
    }   

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void itemCustomizationWindow(ItemConfiguration conf) {
        // Overloaded call for new item. NOTE: -1 is add to end of list, null means make new item.
        itemCustomizationWindow(conf, -1, null);
    }

    @FXML
    private void itemCustomizationWindow(ItemConfiguration conf, int order_index, Item starting_item) {
        final double POPUP_WIDTH = 500d;
        final double POPUP_HEIGHT = 500d;

        // Get all String keys for both base and addition options.
        String[] base_confs = conf.getBase_varients().keySet().toArray(new String[0]);
        String[] addition_confs = conf.getAdditions_varients().keySet().toArray(new String[0]);

        // Make the item representing the current selected configuration.
        Item item;
        if (starting_item == null)
        {
            item = new Item(conf, base_confs[0], new String[0]);
        }
        else
        {
            item = starting_item;
        }

        // Make a new stage for the popup confirmation window.  // NOTE: Application Modality will let popup block access to underlying windows.
        final Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);

        // Create Boxes to organize Window. On Top Configuration selection and display (Left/Right). Along bottom cancel/add buttons.
        VBox popupVBox = new VBox(100);  // Overall VBox to layout Popup Window.
        HBox configurationHBox = new HBox(20);  // HBOX to hold side by side VBOXs for item configuration.
        VBox leftVbox = new VBox(20);   // VBOX to hold buttons to set configurations 
        VBox rightVbox = new VBox(20);  // VBOX to hold labels to reflect current configuration.
        HBox doneHBox = new HBox(0);   // HBOX to hold buttons to either stop or add Order.

        // Set widths So buttons can extend 
        leftVbox.setMinWidth((POPUP_WIDTH-leftVbox.getSpacing())/2);
        rightVbox.setMinWidth((POPUP_WIDTH-rightVbox.getSpacing())/2);
        doneHBox.setMinWidth(POPUP_WIDTH);

        // Labels for current Selection.
        Label name = new Label(conf.getName());
        Label currentBaseTitle = new Label("Base:");
        Label base = new Label("\t" + item.getBase());
        Label currentAdditionTitle = new Label("Additions:");
        Label additions = new Label(item.getItemAdditionsString("\t","\n"));
        rightVbox.getChildren().addAll(name, currentBaseTitle, base, currentAdditionTitle, additions);

        // Add all "Base" choices as buttons that set item base.
        Label baseTitle = new Label("Base Options:");
        leftVbox.getChildren().add(baseTitle);
        for (String base_option : base_confs)
        {
            // Stop Cancellation Button. Action to close popup WITHOUT clearing transaction.
            Button base_button = new Button(base_option);
            base_button.setMinWidth(leftVbox.getMinWidth());
            base_button.setOnAction(actionEvent -> {item.setBase(base_option); base.setText("\t" + item.getBase());});
            leftVbox.getChildren().add(base_button);
        }

        // Add all "Additions" choices as buttons that toggle whether item has addition selected.
        Label additionsTitle = new Label("Addition Options:");
        leftVbox.getChildren().add(additionsTitle);
        for (String addition_option : addition_confs)
        {
            Button addition_button = new Button(addition_option);
            addition_button.setMinWidth(leftVbox.getMinWidth());
            addition_button.setOnAction(actionEvent -> {item.toggleVarient(addition_option); additions.setText(item.getItemAdditionsString("\t","\n"));});
            leftVbox.getChildren().add(addition_button);
        }
        
        // Button to cancel adding (and close popup).
        Button cancel_button = new Button("Cancel");
        cancel_button.setMinWidth(doneHBox.getMinWidth()/2);
        cancel_button.setOnAction(actionEvent -> {popup.close();});
        doneHBox.getChildren().add(cancel_button);
        
        // Button to Add current item to order (and close popup).
        Button add_button;
        if (order_index < 0)
        {
            add_button = new Button("Add to Order");
			add_button.setOnAction(actionEvent -> {popup.close(); App.getOrder().addItem(item); _updateDollarAmounts(); _updateCurrentOrder();});
        }
        else
        {
            add_button = new Button("Modify");
            add_button.setOnAction(actionEvent -> {popup.close(); App.getOrder().replaceItem(order_index, item); _updateDollarAmounts(); _updateCurrentOrder();});
        }
        add_button.setMinWidth(doneHBox.getMinWidth()/2);
        doneHBox.getChildren().add(add_button);

        // Add the Configuration VBOXs to the Configuration Layout HBOX.
        configurationHBox.getChildren().add(leftVbox);
        configurationHBox.getChildren().add(rightVbox);

        // Add the Configuration Layout to the VBox.
        popupVBox.getChildren().add(configurationHBox);
        popupVBox.getChildren().add(doneHBox);

        // Create a new Scene for the popup and show the window.
        Scene popupScene = new Scene(popupVBox, POPUP_WIDTH, POPUP_HEIGHT);
        popup.setScene(popupScene);
        popup.show();
    }

    @FXML
    private void clearOrder() {
        // Make a new stage for the popup confirmation window.
        final Stage popup = new Stage();

        // NOTE: Application Modality will let popup block access to underlying windows.
        popup.initModality(Modality.APPLICATION_MODAL);
        
        // VBOX to hold all buttons.
        VBox popupVbox = new VBox(20);
        
        // Confirm Cancellation Button. Action to close popup and clear transaction.
        Button confirm_button = new Button("Confirm Cancellation");
        confirm_button.setOnAction(actionEvent -> {popup.close(); App.getOrder().clearItems();  _updateDollarAmounts(); _updateCurrentOrder();});
        popupVbox.getChildren().add(confirm_button);

        // Stop Cancellation Button. Action to close popup WITHOUT clearing transaction.
        Button stop_button = new Button("Stop Cancellation");
        stop_button.setOnAction(actionEvent -> {popup.close();});
        popupVbox.getChildren().add(stop_button);

        // Create a new Scene for the popup and show the window.
        Scene popupScene = new Scene(popupVbox, 150,100);
        popup.setScene(popupScene);
        popup.show();
    }

    @FXML
    private void checkout() throws IOException {
        App.setRoot("checkoutScreen");
    }

    private void _updateCurrentOrder() {
        // Start fresh!
        current_order_pane.getChildren().clear();
        
        VBox order_vbox = new VBox(5);
        Insets order_vbox_insets = new Insets(5, 0, 0, 10);
        order_vbox.setPadding(order_vbox_insets);
        List<Item> items = App.getOrder().getItems();
        for(int i = 0; i < items.size(); i++) {
            final int current_index = i;
            Item item = items.get(current_index);
            VBox item_vbox = new VBox(0);
            String item_name_and_price = item.getConf().getName() + " - " + Util.convertMoneyToString(item.getTotalCost());

            Label item_lbl = new Label(item_name_and_price, null);
            Font item_lbl_font = new Font(16);

            item_lbl.setFont(item_lbl_font);
            item_lbl.setAlignment(Pos.CENTER);

            Button edit_btn = new Button("Edit");
            Font edit_btn_font = new Font(8);
            edit_btn.setFont(edit_btn_font);
            edit_btn.setAlignment(Pos.CENTER);
            edit_btn.setOnAction(actionEvent -> {itemCustomizationWindow(item.getConf(), current_index, item); _updateDollarAmounts(); _updateCurrentOrder();});

            Button del_btn = new Button("Delete");
            Font del_btn_font = new Font(8);
            del_btn.setFont(del_btn_font);
            del_btn.setAlignment(Pos.CENTER);
            del_btn.setOnAction(actionEvent -> {App.getOrder().removeItem(current_index);  _updateDollarAmounts(); _updateCurrentOrder();});

            HBox item_box = new HBox(5, item_lbl, edit_btn, del_btn);
            item_box.setAlignment(Pos.CENTER_LEFT);
            item_vbox.getChildren().add(item_box);

            String base = item.getBase();
            Label base_lbl = new Label("    " + base);
            base_lbl.setFont(item_lbl_font);
            base_lbl.setAlignment(Pos.CENTER);
            item_vbox.getChildren().add(base_lbl);

            for(String variant : item.varients)
            {
                Label variant_lbl = new Label("        " + variant);
                variant_lbl.setFont(item_lbl_font);
                variant_lbl.setAlignment(Pos.CENTER);
                item_vbox.getChildren().add(variant_lbl);
            }

            order_vbox.getChildren().add(item_vbox);
        }
        
        //Adding some extra blank to allow us to scroll to the bottom easier...
        Pane blank_pane = new Pane();
        blank_pane.setMinHeight(10);
        order_vbox.getChildren().add(blank_pane);

        current_order_pane.getChildren().add(order_vbox);
    }

    private void _updateDollarAmounts() {

        subtotal_lbl.setText("Subtotal: " + Util.convertMoneyToString(Util.getSubTotal()));
        tax_lbl.setText("Tax: " + Util.convertMoneyToString(Util.getTax()));
        total_lbl.setText("Total: " + Util.convertMoneyToString(Util.getTotal()));
    }

    private void makeItemCatalogue(GridPane gridPane) throws FileNotFoundException
    {
		ArrayList<String> h = new ArrayList<String>();
		ArrayList<String> itemNameList = new ArrayList<String>();

        int j=0;

        App.getConfiguration().getItems().forEach((e)->{
			Map<String,Integer> t = e.getAdditions_varients();
			for (Map.Entry<String, Integer> entry : t.entrySet()) {
				System.out.println(entry.getKey() + ":" + entry.getValue().toString());
			}   
			
			System.out.println(e.getName());
			System.out.println(e.getIcon_path());
			h.add(e.getIcon_path());
			itemNameList.add(e.getName());
        });
		
        int s=0;
        for(String l:h)
        {
            ImageView img = new ImageView(new Image(new FileInputStream("src/main/resources/Images/"+l)));
            img.setPreserveRatio(true);
            img.setFitWidth(200);
            gridPane.add(img , j, s);
			j++;
        }
		
        int k=0;
        for(String l:itemNameList)
        {
            gridPane.add(new Label(l),k,1);
            k++;
        }
		
        int f=0;
        String kk="";
        
        System.out.println(kk);  
		ArrayList<Button> btns = new ArrayList<Button>();
		for(int i=0;i<4;i++)
		{
			kk = itemNameList.get(i);      
            Button item_btn = new Button(kk);
            final int item_idx = i; // NOTE: Needs to be final to be used in lambda.
            item_btn.setOnAction(actionEvent -> {itemCustomizationWindow(App.getConfiguration().getItems().get(item_idx));});
			btns.add(item_btn);
			gridPane.add(btns.get(i), f, 3);
			f++;
		}
    }
}
