package utils;

import models.Board;
import org.assertj.core.api.Assertions;
import retrofit2.Response;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CheckResponseIsValid {

//    public static void checkResponseCode(Response<?> response, int expectedCode) {
//        Assertions.assertThat(response.code()).as("Check response status code").isEqualTo(expectedCode);
//    }

    public static void checkResponseCode(Response<?> response, int expectedCode) {
        if (response.code() != expectedCode) {
            throw new AssertionError("Expected response code: " + expectedCode + " but was: " + response.code());
        }
    }

//
//
//    public static void checkResponseBodyContains(Response<?> response, String expectedContent) {
//        try {
//            Assertions.assertThat(response.body().toString()).as("Check response body contains").contains(expectedContent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void checkResponseBodyContains(Response<?> response, String keyword) {
        String bodyString = "";
        try {
            bodyString = response.errorBody() != null ? response.errorBody().string() : response.body().toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!bodyString.contains(keyword)) {
            throw new AssertionError("Expected response body to contain: " + keyword + " but was: " + bodyString);
        }
    }


    public static void checkBoardFields(Response<Board> response, String expectedName) {
        Board board = response.body();
        Assertions.assertThat(board.getName()).as("Check board name").isEqualTo(expectedName);
        Assertions.assertThat(board.getId()).as("Check board ID").isNotNull();
    }
}
