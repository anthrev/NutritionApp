package e.usf.nutritionapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET("&q={search}&sort=n&max=5&offset=0&api_key=OLhPe9BAFfszhNrO5kTx8HPmOczYzIhoVj6VlsT3")
    Call<List<Post>> getPosts(@Path("search") String search);

    @GET
    Call<List<Post>> getPosts(int i, int i1);
}
