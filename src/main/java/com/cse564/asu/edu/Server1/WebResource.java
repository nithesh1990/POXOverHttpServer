/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cse564.asu.edu.Server1;

import com.cse564.asu.edu.Server.Model1.AddFoodResponse;
import com.cse564.asu.edu.Server.Model1.ClientRequest;
import com.cse564.asu.edu.Server.Model1.FoodItem;
import com.cse564.asu.edu.Server.Model1.FoodItemAdded;
import com.cse564.asu.edu.Server.Model1.FoodItemExists;
import com.cse564.asu.edu.Server.Model1.InvalidFoodItem;
import com.cse564.asu.edu.Server.Model1.InvalidMessage;
import com.cse564.asu.edu.Server.Model1.NewFoodItems;
import com.cse564.asu.edu.Server.Model1.RetrievedFoodItems;
import com.cse564.asu.edu.Server.Model1.SelectedFoodItems;
import com.cse564.asu.edu.Server.Model1.ServerResponse;
import com.cse564.asu.edu.Server.Model1.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author nithesh
 */
@Path("inventory")
public class WebResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WebResource
     */
    public WebResource() {
    }

    /**
     * Retrieves representation of an instance of com.cse564.asu.edu.Server.Model1.WebResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        return "Hello";
    }
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public ServerResponse postXml(ClientRequest clientRequest){
        int requestCode = clientRequest.getRequestCode();
        switch(requestCode){
            case Utils.RequestCodes.ADD_FOOD_ITEM:
                return addFoodItems(clientRequest.getNewFoodItems());
                
            case Utils.RequestCodes.GET_FOOD_ITEM:
                return getFoodItems(clientRequest.getSelectedFoodItems());
                
        }
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setResponseCode(Utils.ResponseCodes.INVALID_MESSAGE);
        serverResponse.setInvalidMessage(new InvalidMessage());
        return serverResponse;
        
    }

    public ServerResponse getFoodItems(SelectedFoodItems selectedFoods){
        List<Integer> foodIds = selectedFoods.getFoodItemId();
        ServerResponse serverResponse = new ServerResponse();
        RetrievedFoodItems retreivedFoodItems = new RetrievedFoodItems();
        List<FoodItem> availableFoods = new ArrayList<>();
        List<InvalidFoodItem> invalidFoods = new ArrayList<>();
        for(Integer id : foodIds){
            if(FoodData.getInstance().isFoodIdExists(id)){
                availableFoods.add(FoodData.getInstance().getFoodItem(id));
            } else {
                InvalidFoodItem invalidItem = new InvalidFoodItem();
                invalidItem.setFoodItemId(id);
                invalidFoods.add(invalidItem);
            }
        }
        retreivedFoodItems.setFoodItem(availableFoods);
        retreivedFoodItems.setInvalidFoodItem(invalidFoods);
        serverResponse.setResponseCode(Utils.ResponseCodes.GET_FOOD_ITEM);
        serverResponse.setRetreievedFoodItems(retreivedFoodItems);
        return serverResponse;
    }
            
    
    
    public ServerResponse addFoodItems(NewFoodItems newFoodItems){
        ServerResponse serverResponse = new ServerResponse();
        AddFoodResponse addFoodResponse = new AddFoodResponse();
        List<FoodItemAdded> addList = new ArrayList<>();
        List<FoodItemExists> existsList = new ArrayList<>(); 
        serverResponse.setResponseCode(Utils.ResponseCodes.ADD_FOOD_ITEM);
        List<FoodItem> foodItemList = newFoodItems.getFoodItem();
        for(FoodItem foodItem : foodItemList){
            //add to food list
            if(isFoodItemExists(foodItem.getCategory(), foodItem.getName())){
                FoodItem existingItem = FoodData.getInstance().getFoodItem(foodItem.getCategory(), foodItem.getName());
                FoodItemExists foodItemExists = new FoodItemExists();
                foodItemExists.setFoodItemId(existingItem.getId());
                existsList.add(foodItemExists);
            } else {
                int newFoodItemId = addtoList(foodItem);
                FoodItemAdded foodItemAdded = new FoodItemAdded();
                foodItemAdded.setFoodItemId(newFoodItemId);
                addList.add(foodItemAdded);
            }
        }
        if(addList.size()>0){
            addFoodResponse.setFoodItemAdded(addList);
        }
        
        if(existsList.size()>0){
            addFoodResponse.setFoodItemExists(existsList);
        }
        
        serverResponse.setAddFoodResponse(addFoodResponse);
        return serverResponse;
    }
    
    public boolean isFoodItemExists(String Category, String name){
        return FoodData.getInstance().isFoodItemExists(Category, name);
    }
    
    
    public int addtoList(FoodItem foodItem){
        
        return FoodData.getInstance().addFoodItem(foodItem.getCategory(), foodItem.getName(), foodItem);
    }
    /**
     * PUT method for updating or creating an instance of WebResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
