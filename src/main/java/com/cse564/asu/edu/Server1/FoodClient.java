/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cse564.asu.edu.Server1;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 *
 * @author nithesh
 */
public class FoodClient {
    private static WebResource webResource;
    
    private static Client client;
    private final static String BASE_URI = "http://localhost:8080/POXOverHTTPSrv/webapi/myresource";
    
    //FoodClient() {
      //      }
    
    public static void main(String[] args) {
        client = Client.create();
        webResource = client.resource(BASE_URI);

        ClientResponse clientResponse = webResource.get(ClientResponse.class);
        System.out.println(clientResponse);
        //return "Done";
        //return rs.readEntity(FoodItem.class);
    }
    
    public void close() {
        //client.close();
    }

}
