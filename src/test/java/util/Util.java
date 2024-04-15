package util;


import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

public class Util {

    public void getUser(int userId){
        System.out.println("user id"+userId+"\nurl"+baseURI);
        Response response = given()
                .header("Authorization","Bearer a485702074717bc1aa1b96a242a30a456ac25c2691d7758fe6403e0349f4939e")
                .when().get("/"+String.valueOf(userId))
                .then()
                .log().all()
                .extract().response();
        Assert.assertEquals(response.statusCode() , 200);
        System.out.println("user successfully created");

    }

    public static JSONObject createUser(String name, String email, String gender, String status){
        JSONObject bodyRequest = new JSONObject();
        bodyRequest.put("email", email);
        bodyRequest.put("name",name);
        bodyRequest.put("gender",gender);
        bodyRequest.put("status",status);

        return bodyRequest;
    }

}
