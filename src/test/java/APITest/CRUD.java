package APITest;

import data.DataProvider;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.logging.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.Util;

import java.util.HashMap;
import java.util.logging.Logger;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CRUD {

    Util utilClass = new Util();
    @BeforeClass
    public void beforeClass(){
        baseURI = "https://gorest.co.in/public/v2/users";
    }

    @Test(dataProvider = "createValidUser", dataProviderClass = DataProvider.class)
    public void createValidUser(String name, String email, String gender, String status){

        JSONObject bodyRequest = utilClass.createUser(name,email,gender,status);
        JsonPath response = RestAssured.given()
                .header("Authorization","Bearer a485702074717bc1aa1b96a242a30a456ac25c2691d7758fe6403e0349f4939e")
                .header("Content-type","application/json")
                .header("Accept","application/json")
                .body(bodyRequest.toString())
                .log().all()
                .when().post()
                .then()
                .log().all()
                .assertThat().statusCode(201)
                .assertThat().body("id", Matchers.notNullValue())
                .assertThat().body("name",Matchers.equalTo(name))
                .extract().body().jsonPath();

        utilClass.getUser(response.get("id"));

    }

    @Test(dataProvider = "createInvalidUser", dataProviderClass = DataProvider.class)
    public void createInvalidUser(String name, String email, String gender, String status){

        JSONObject bodyRequest = utilClass.createUser(name,email,gender,status);
      RestAssured.given()
                .header("Authorization","Bearer a485702074717bc1aa1b96a242a30a456ac25c2691d7758fe6403e0349f4939e")
                .header("Content-type","application/json")
                .header("Accept","application/json")
                .body(bodyRequest.toString())
                .log().all()
                .when().post()
                .then()
                .log().all()
                .assertThat().statusCode(422);

    }

    @Test(dataProvider = "edgeCases", dataProviderClass = DataProvider.class)
    public void checkEdgeCases(String name, String email, String gender, String status){

        JSONObject bodyRequest = utilClass.createUser(name,email,gender,status);
        RestAssured.given()
                .header("Authorization","Bearer a485702074717bc1aa1b96a242a30a456ac25c2691d7758fe6403e0349f4939e")
                .header("Content-type","application/json")
                .header("Accept","application/json")
                .body(bodyRequest.toString())
                .log().all()
                .when().post()
                .then()
                .log().all()
                .assertThat().statusCode(422)
                .assertThat().body("message", Matchers.contains("is too long (maximum is 200 characters)"));
    }

}
