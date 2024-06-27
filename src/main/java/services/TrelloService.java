package services;

import models.Board;
import models.Member;
import retrofit2.Call;
import retrofit2.http.*;




public interface TrelloService {

        /***********************Check if API is working****************/
        @GET("members/me")
        Call<Member> getMemberInfo(@Query("key") String apiKey, @Query("token") String token);


      //Creating a board
        @POST("boards/")
        Call<Board> createBoard(@Query("name") String name, @Query("key") String apiKey, @Query("token") String token);

        @GET("boards/{id}")
        Call<Board> getBoard(@Path("id") String id, @Query("key") String apiKey, @Query("token") String token);

        @PUT("boards/{id}")
        Call<Board> updateBoard(@Path("id") String id, @Query("name") String name, @Query("key") String apiKey, @Query("token") String token);

        @DELETE("boards/{id}")
        Call<Void> deleteBoard(@Path("id") String id, @Query("key") String apiKey, @Query("token") String token);
    }

