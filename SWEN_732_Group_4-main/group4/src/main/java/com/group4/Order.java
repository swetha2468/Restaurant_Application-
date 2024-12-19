package com.group4;

import java.util.ArrayList;
import java.util.List;

class Order
{
    private List<Item> items;

    public Order()
    {
        // Create new empty list to track order.
        items = new ArrayList<Item>();
        clearItems();
    }

    /**
     * @brief: Clear all items in the current list so that list is empty.
     * 
     * @return: None
     */
    public void clearItems()
    {
        this.items.clear();
    }

    /**
     * @brief: Add the provided item to the end of the list for the Order.
     *  
     * @param x (Item): Item to be added.
     * 
     * @return: None
     */
    public void addItem(Item x)
    {
        this.items.add(x);
    }

    /**
     * @brief: Remove an item from the order at the specified index. Return Item.
     * 
     * @param idx (int): Index of the item to be removed.
     * 
     * @return: Item, the Item that was removed.
     */
    public Item removeItem(int idx)
    {
        return this.items.remove(idx);
    }

    /**
     * @brief: Replace (overwrite) the item at the specified index with the provided Item.
     *  
     * @param idx (int): Index of item to be replaced in order.
     *
     * @param x (Item): Provided item to be used as replacement.
     */
    public void replaceItem(int idx, Item x)
    {
        this.items.set(idx, x);
    }

    /**
     * @brief: Calculate the total for all items in the order.
     * 
     * @return int, The total of all Items in the order.
     */
    public int calculateTotal()
    {
        int res = 0;
        
        for (Item x : this.items) {
            res += x.getTotalCost();
        }

        return res;
    }

    public List<Item> getItems()
    {
        return this.items;
    }
}