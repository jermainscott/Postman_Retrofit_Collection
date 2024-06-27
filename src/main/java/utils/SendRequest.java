package utils;

import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class SendRequest {

    public static <T> Response<T> sendRequest(Call<T> call) throws IOException {
        return call.execute();
    }
}
