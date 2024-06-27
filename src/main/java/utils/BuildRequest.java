package utils;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class BuildRequest {
    private static final String BASE_URL = "https://api.trello.com/1/";

    public static <S> S buildRetrofitRequest(Class<S> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }
}
