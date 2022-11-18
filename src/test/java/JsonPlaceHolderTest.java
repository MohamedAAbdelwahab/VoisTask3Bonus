import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class JsonPlaceHolderTest {

    JsonPath jsonPath;
    int userid;

    @Test
    public void GetARandomPostIdPrintOutItsCommentsToConsole() {
        Response response = RestAssured.given().when().get("https://jsonplaceholder.typicode.com/posts");
        jsonPath = response.jsonPath();
        ArrayList<Integer> ids = jsonPath.get("id");
        Random generator = new Random();
        int randomIndex = generator.nextInt(ids.size());
        System.out.println("user ID = "+ids.get(randomIndex));


        Response response2 = RestAssured.given().when().get("https://jsonplaceholder.typicode.com/posts/" + ids.get(randomIndex) + "/comments");

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///// I didn't know if you want the "body" of the comments or the whole reponse to be printed but i have done both ////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        jsonPath= response2.jsonPath();
//        ArrayList<String> errors2=jsonPath.get("body");
//        for(String i:errors2)
//        {
//            System.out.println("//////////////////////////////////////////////////////////////////////////////////////");
//            System.out.println(i);
//            System.out.println("//////////////////////////////////////////////////////////////////////////////////////");
//
//        }

        response2.print();
    }

    @Test
    void getAlbumsWithUserID() {
        Response response = RestAssured.given().when().get("https://jsonplaceholder.typicode.com/users");
        jsonPath = response.jsonPath();
        ArrayList<Integer> ids = jsonPath.get("id");
        Random generator = new Random();
        userid = generator.nextInt(ids.size());;
        System.out.println("user ID = "+userid);

        Response response2 = RestAssured.given().when().get("https://jsonplaceholder.typicode.com/users/" + ids.get(userid) + "/albums");
        jsonPath = response2.jsonPath();
        ArrayList<String> errors2 = jsonPath.get("title");
        for (String i : errors2) {
            Assert.assertTrue(i.length() <= 300);
        }

    }

    @Test
    void makeAPost() throws JsonProcessingException {
        Post post = new Post(userid, "Test", "Test");
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = mapper.writeValueAsString(post);

        Response response = RestAssured.given().contentType("application/json; charset=UTF-8").body(jsonString).when().post("https://jsonplaceholder.typicode.com/users");
        jsonPath = response.jsonPath();
        String title = jsonPath.get("title");
        String body = jsonPath.get("body");
        int useridResponse = jsonPath.get("userid");
        Assert.assertEquals(title,"Test");
        Assert.assertEquals(body,"Test");
        Assert.assertEquals(userid,useridResponse);
    }

    @Test
    void printAllTitlesForSpecificUserID() throws JsonProcessingException {
        Response response = RestAssured.given().when().get("https://jsonplaceholder.typicode.com/users");
        jsonPath = response.jsonPath();
        ArrayList<Integer> ids = jsonPath.get("id");
        Random generator = new Random();
        userid = generator.nextInt(ids.size());
        System.out.println("user ID = "+userid);
        Response response2 = RestAssured.given().queryParam("completed",false).when().get("https://jsonplaceholder.typicode.com/users/"+userid+"/todos");
        response2.print();
    }

}