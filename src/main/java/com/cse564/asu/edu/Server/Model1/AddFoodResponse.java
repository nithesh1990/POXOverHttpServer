/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cse564.asu.edu.Server.Model1;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nithesh
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AddFoodResponse {

    public List<FoodItemAdded> getFoodItemAdded() {
        return FoodItemAdded;
    }

    public void setFoodItemAdded(List<FoodItemAdded> foodItemAdded) {
        this.FoodItemAdded = foodItemAdded;
    }

    public List<FoodItemExists> getFoodItemExists() {
        return FoodItemExists;
    }

    public void setFoodItemExists(List<FoodItemExists> foodItemExists) {
        this.FoodItemExists = foodItemExists;
    }
    
    @XmlElement
    private List<FoodItemAdded> FoodItemAdded;
    
    @XmlElement
    private List<FoodItemExists> FoodItemExists;
    
    
}
