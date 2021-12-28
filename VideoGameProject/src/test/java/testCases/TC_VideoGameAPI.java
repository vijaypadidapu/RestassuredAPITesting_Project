package testCases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class TC_VideoGameAPI {

	@Test(priority = 1)
	public void test_getAllVideoGames() {

		given().when().get("http://localhost:8080/app/videogames").then().statusCode(200);
	}

	@Test(priority = 2)
	public void test_addNewVideoGame() {

		HashMap data = new HashMap();
		data.put("id", "107");
		data.put("name", "Spider-Man");
		data.put("releaseDate", "2020-09-20T08:55:58.510Z");
		data.put("reviewScore", "5");
		data.put("category", "Adventure");
		data.put("rating", "Universal");

		Response res = given().contentType("application/json").body(data)

				.when().post("http://localhost:8080/app/videogames").then().statusCode(200).log().body().extract()
				.response();

		String jsonString = res.asString();

		Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
	}

	@Test(priority = 3)
	public void test_getVideoGame() {

		given().when().get("http://localhost:8080/app/videogames/107").then().statusCode(200).log().body()
				.body("videoGame.id", equalTo("107")).body("videoGame.name", equalTo("Spider-Man"));
	}

	@Test(priority = 4)
	public void test_updateVideoGame() {

		HashMap data = new HashMap();
		data.put("id", "107");
		data.put("name", "Pac-Man");
		data.put("releaseDate", "2020-09-20T08:55:58.510Z");
		data.put("reviewScore", "3");
		data.put("category", "Adventure");
		data.put("rating", "Universal");

		given().contentType("application/json").body(data)

				.when().put("http://localhost:8080/app/videogames/107").then().statusCode(200).log().body()
				.body("videoGame.name", equalTo("Pac-Man")).body("videoGame.reviewScore", equalTo("3"));

	}

	@Test(priority = 5)
	public void test_deleteVideoGame() {

		given().when().delete("http://localhost:8080/app/videogames/107").then().statusCode(200).log().body();
	}

}
