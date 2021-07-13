package Project;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GitHubProject {
	RequestSpecification requestSpec;
	String SSHkey;
	int id;
	
	
  @BeforeClass
  public void setup() {
	  requestSpec = new RequestSpecBuilder()
              .setContentType(ContentType.JSON)
              .addHeader("Authorization", "token ghp_HRDVWk3bSOFKrJgEfuTbRSnu2bZQbf2EI")
              .setBaseUri("https://api.github.com")
              .build();
	  
	  
  }
	
  @Test(priority=1)
  public void AddSSHkey() {
	  
	String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCoMqZtxLlf7E7tpBKT6A0ZGOhwQ6/I4kkNhOeeKHfTe0WHWGTGrn6SPAuIhnU57qDLcs1VQUy3lZc8fVmkIOEdN0XlYrdg4Y3TE7sPwdmwmaSF8GAhBCagCJy++6QQkLQVd2n1kD71te1UUvEv0pLBvs/qjkqbDEtyHi6p4i+XqBl87zDJiITmRW4+F5jRA4be6H9lD+jdwrEUWEjWQeV+Y4mwyMaUk0aayCRa/eKtoVTfX0GeVXkus+9iCDVwk4AhsvgyGHeb6yCHNyj/HD4D+fH2KRqii1LTvn/Fd1swX6jR1zZBGuheiWQ4hriwLmso6R629BASyDOTXdEdt\"}";
	Response response = given().spec(requestSpec).body(reqBody)
			.when().post("/user/keys");
	
	id = response.then().extract().path("id");	
			
	// Assertion
	response.then().statusCode(201);
  }
  
  @Test(priority=2)
  public void GetSSHkey() {
	  
	  Response response = given().spec(requestSpec)
			  .when().get("/user/keys");
	  
	  String res= response.body().asPrettyString();
	  System.out.println(res);
	// Assertion
	  response.then().statusCode(200);
	  
  }
  
  @Test(priority=3)
  public void DeleteSSHkey() {
	  
	  Response response = given().spec(requestSpec)
			  .pathParam("keyId", id)
				.when().delete("/user/keys"+ "/{keyId}");
	  
	 String resdel = response.getBody().asPrettyString();
	  System.out.println(resdel);
	// Assertion
	  response.then().statusCode(204);
	  
	  
  }
}
