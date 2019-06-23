package e.usf.nutritionapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "https://api.nal.usda.gov/";

    @GET("ndb/search/")
    Call<FoodItemContainerContainer>getFoodItems(
            @Query ("format") String format,
            @Query("q") String search,
            @Query("sort") String sort,
            @Query("max") int max,
            @Query("offset") int offset,
            @Query("api_key") String apiKey
    );
}
