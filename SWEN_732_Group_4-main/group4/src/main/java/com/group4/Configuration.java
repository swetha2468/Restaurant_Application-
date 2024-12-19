package com.group4;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


class Configuration
{
    private float sales_tax;
    private List<ItemConfiguration> items;

    Configuration(){
        this.items = new ArrayList<ItemConfiguration>();
    }

    public float getSales_tax() {
        return sales_tax;
    }
    public void setSales_tax(float sales_tax) {
        this.sales_tax = sales_tax;
    }

    public List<ItemConfiguration> getItems() {
        return items;
    }
    public void setItems(List<ItemConfiguration> items) {
        this.items = items;
    }

    /*
     * @brief Add an item to the list of Configuration items.
     * 
     * @param item (ItemConfiguration): Item to be added.
     */
    public void addItem(ItemConfiguration item) {
        this.items.add(item);
    }

    /*
     * @brief Remove item at the specified index from the list.
     * 
     * @param idx (int): Index to be removed.
     * 
     * @return ItemConfiguration: Item removed from list.
     */
    public ItemConfiguration removeItem(int idx){
        return this.items.remove(idx);
    }

    /*
     * @brief Convert a JSON string into a Configuration object.
     * 
     * @ param json (String): the JSON string to be parsed for a Configuration Object.
     * 
     * @ return Configuration, object parsed from JSON.
     */
    public static Configuration loads(String json)
    {
        // Create a GSON object with formatting.
        GsonBuilder builder = new GsonBuilder(); 
        builder.setPrettyPrinting(); 
        Gson gson = builder.create(); 
       
        // Decode JSON to Configuration object.
        Configuration res = gson.fromJson(json, Configuration.class);
        return res;
    }

    /*
     * @brief Convert a specified text file with JSON into a Configuration object.
     * 
     * @param filename (String): path to file with JSON to be parsed for a Configuration Object.
     * 
     * @return Configuration, object parsed from JSON.
     */
    public static Configuration load(String filename)
    {
        // Get File.
        File file = new File(filename);
        
        // Get Read Object for File.
        FileInputStream fis = null;
        try 
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) 
        {
            // TODO Handle Error.
            e.printStackTrace();
        }
        
        //Read all file contents to String.
        String json = "";
        try 
        {
            json = new String(fis.readAllBytes());
        }
        catch (IOException e) 
        {
            // TODO Handle Error.
            e.printStackTrace();
        }

        // Decode file to Configuration object.
        return Configuration.loads(json);
    }

    /*
     * @brief Save the current Configuration object to a JSON string.
     * 
     * @Return String, the JSON representation for the object.
     */
    public String saves()
    {
        // Create a GSON object with formatting.
        GsonBuilder builder = new GsonBuilder(); 
        builder.setPrettyPrinting(); 
        Gson gson = builder.create(); 

        // Use JSON parser to create JSON for this object.
        return gson.toJson(this);
    }

    /* 
     * @brief Save the current Configuration object to the specified file.
     * 
     * @param filename (String): path to file where JSON should be saved.
     */
    public void save(String filename)
    {
        // Get JSON for current object.
        String json = this.saves();

        // Get File.
        File file = new File(filename);
        
        // Get Write Object for File.
        FileOutputStream fos = null;
        try 
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e)
        {
            // TODO: Error.
            e.printStackTrace();
        }

        // Write all JSON data to File.
        try {
            fos.write(json.getBytes());
        }
        catch (IOException e)
        {
            // TODO: Error.
            e.printStackTrace();
        }
    }

    public String toString(){
        StringBuilder res = new StringBuilder();

        res.append("Sales Tax: " + sales_tax);
        
        res.append(",\nItem Configurations: \n");
        for (ItemConfiguration x : this.items)
        {
            res.append("\t ");
            res.append(x);
            res.append("\n");
        }

        return res.toString();
    }
}

class ItemConfiguration
{
    private String name;
    private String icon_path;
    private Map<String, Integer> base_varients;
    private Map<String, Integer> additions_varients;


    ItemConfiguration(){
        name = "";
        icon_path = "";
        base_varients = new HashMap<String, Integer>();
        additions_varients = new HashMap<String, Integer>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_path() {
        return icon_path;
    }
    public void setIcon_path(String icon_path) {
        this.icon_path = icon_path;
    }

    public Map<String, Integer> getBase_varients() {
        return base_varients;
    }
    public void setBase_varients(Map<String, Integer> base_varients) {
        this.base_varients = base_varients;
    }

    public Map<String, Integer> getAdditions_varients() {
        return additions_varients;
    }
    public void setAdditions_varients(Map<String, Integer> additions_varients) {
        this.additions_varients = additions_varients;
    }

    public String toString()
    {
        StringBuilder res = new StringBuilder();

        res.append("Name: " + name);
        res.append(", Icon Path: " + icon_path);
        res.append(", Base Varients: " + base_varients);
        res.append(", Additions: " + additions_varients);

        return res.toString();
    }
}