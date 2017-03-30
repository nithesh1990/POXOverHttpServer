/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cse564.asu.edu.Server1;

import com.cse564.asu.edu.Server.Model1.FoodItem;
import com.cse564.asu.edu.Server.Model1.FoodItemList;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author nithesh
 */
public class FoodData {
    
    
    private static String FILE_NAME = "FoodItemData.xml";
    private static int foodId;
    
    private static Map<String, FoodItem> foodNameData;
    
    private static Map<Integer, FoodItem> foodIdData;
    
    private static FoodData sInstance;
    
    public static FoodData getInstance(){
        if(sInstance == null){
            sInstance = new FoodData();
            foodNameData = new HashMap<>();
            foodIdData = new HashMap<>();
            foodId = 150;
            loadFoodItems();
         }
        return sInstance;
    }
    
    
    public  int addFoodItem(String category, String name, FoodItem foodItem){
        String key = category+name;
        int currentfoodId = foodId;
        foodItem.setId(foodId);
        foodId++;
        foodNameData.put(key, foodItem);
        foodIdData.put(currentfoodId, foodItem);
        return currentfoodId;
    }
    
    public boolean isFoodItemExists(String category, String name){
        String key = category+name;
        return foodNameData.containsKey(key);
    }
    
    public boolean isFoodIdExists(int id){
        return foodIdData.containsKey(id);
    }
    
    public FoodItem getFoodItem(int id){
        return foodIdData.get(id);
    }
            
    public FoodItem getFoodItem(String category, String name){
        String key = category+name;
        return foodNameData.get(key);
    }

    public List<FoodItem> getFoodItems(){
        List<FoodItem> foodsList = new ArrayList<>();
        for(Map.Entry entry : foodNameData.entrySet()){
            foodsList.add((FoodItem)entry.getValue());
        }
        
        return foodsList;
    }
    
    
    public static void loadFoodItems() {
        InputStream input = WebResource.class.getClassLoader()
                .getResourceAsStream(FILE_NAME);
        try {
                Unmarshaller jaxbUnmarshaller;
                JAXBContext jaxbContext = JAXBContext.newInstance(FoodItemList.class);
                jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                FoodItemList itemList = (FoodItemList) jaxbUnmarshaller.unmarshal(input);
                List<FoodItem> foodsList = itemList.getFoodItemData();
                if(foodsList != null && foodsList.size()>0){
                    for(FoodItem foodItem : foodsList){
                        String key = foodItem.getCategory()+foodItem.getName();
                        foodNameData.put(key, foodItem);
                        foodIdData.put(foodItem.getId(), foodItem);
                    }
                }
            } catch (JAXBException ex) {
                ex.printStackTrace();
            }

    }
     
}
