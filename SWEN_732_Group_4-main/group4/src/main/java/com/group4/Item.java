package com.group4;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @brief: Represents an configured instance of an Item Configuration (which is template of possible options) specifying specific options.
 */
class Item {
    // The item configuration that should be a template for this item.
    private ItemConfiguration conf;

    // The key for the base this item uses from the configuration.
    String base;
    
    // All varients that this item uses from the configuration. NOTE: Store as ArrayList to allow modifying.
    ArrayList<String> varients;

    /**
     * @throws InvalidKeyException if provide base or any provided varients are NOT valid for specified configuration.
     */
    public Item(ItemConfiguration conf, String base, String[] varients) 
    {
        this.conf = conf;
        
        // Base should not be used if ItemConfiguration doesn't have entry for it (Exception).
        if (conf.getBase_varients().containsKey(base))
        {
            this.base = base;
        }
        else
        {
            throw new InvalidKeyException(base);
        }
        
        // Varients should not be used if ItemConfiguration doesn't have an entry for EACH one provided (Exception).
        for (String x : varients) {
            if (!conf.getAdditions_varients().containsKey(x))
            {
                throw new InvalidKeyException(x);
            }
        }

        // NOTE: If exception not thrown all varients provide exist.
        this.setVarients(varients);
    }

    /**
     * @brief: Calculate the cost of the item with configurations provided.
     * 
     * @return int: Total cost of the item given template and provided configurations.
     */
    public int getTotalCost()
    {
        int res;

        // Only one base varient per item, use that to start price.
        res = conf.getBase_varients().get(this.base);

        // Add on price for all additional varients (can be 0 or more).
        for (String x : varients) {
            res += conf.getAdditions_varients().get(x);
        }

        return res;
    }

    public ItemConfiguration getConf() {
        return conf;
    }
    public void setConf(ItemConfiguration conf) {
        this.conf = conf;
    }


    public String getBase() {
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }


    public String[] getVarients() {
        return varients.toArray(new String[0]);
    }
    public void setVarients(String[] varients) {
        this.varients = new ArrayList<>(Arrays.asList(varients));
    }

    /**
     * @throws InvalidKeyException if provide base or any provided varients are NOT valid for specified configuration.
     */
    // Add the varient to the varient List (if not already present).
    public void addVarient(String varient) 
    {
        // Varients should not be used if ItemConfiguration doesn't have an entry for it (Exception).
        if (!conf.getAdditions_varients().containsKey(varient))
        {
            throw new InvalidKeyException(varient);
        }

        // TODO: Do we want to allow DOUBLE of any varient.
        // Look to see if item already contains varient (can not be added twice). Early Exit.
        if (this.hasVarient(varient))
        {
            return;
        }

        this.varients.add(varient);
    }

    // TODO: Documentation. Remove varient, return true if found, false otherwise.
    public boolean removeVarient(String varient)
    {
        return this.varients.remove(varient);
    }

    // TODO: Documentation. Check for existance of provided varient. Return true if found, false otherwise.
    public boolean hasVarient(String varient)
    {
        return this.varients.contains(varient);
    }

    /**
     * @throws InvalidKeyException if provide base or any provided varients are NOT valid for specified configuration.
     */
    // TODO: Description. "Toggle" varient setting, if it is present then remove, otherwise add.
    public void toggleVarient(String varient)
    {
        // Varient should be removed if present or added if not present. I.e. "toggled".
        if (this.hasVarient(varient))
        {
            this.removeVarient(varient);
        }
        else
        {
            this.addVarient(varient);
        }
    }

    String getItemAdditionsString(String indent, String seperator)
    {
        if (this.varients.size() == 0)
        {
            return "";
        }

        StringBuilder res = new StringBuilder();

        for (String x : this.varients)
        {
            res.append(indent);
            res.append(x);
            res.append(seperator);
        }

        // Remove the last <Seperator length> characters. Above implementation leaves one trailing seperator.
        res.delete(res.length() - seperator.length(), res.length());

        return res.toString();
    }

    public String toString()
    {
        StringBuilder res = new StringBuilder();

        res.append(String.format("Configuration Name: %s, Base: %s, Varients: ", this.conf.getName(), this.base));
        
        // Add all varients in list form.
        for (String x : this.varients) {
            res.append(x);
            res.append(", ");
        }

        // NOTE: Looping mechanism for varients will leave trailing comma and space if there are any varients. Remove.
        if (varients.size() > 0)
        {
            res.setLength(res.length() - 2);
        }
        res.append("");

        return res.toString();
    }
}

/**
 * @brief: Represents Exception for Key not existing in data type.
 */
class InvalidKeyException extends RuntimeException
{
    public String key;

    public InvalidKeyException(String key)
    {
        this.key = key;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid Key %s not supported for data type.",this.key);
    }
}
