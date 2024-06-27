package tests;

import models.Board;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import retrofit2.Response;
import services.TrelloService;
import utils.BuildRequest;
import utils.CheckResponseIsValid;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BoardTests {

    private Properties prop = new Properties();
    private String SECRET_KEY;
    private String boardId;
    private TrelloService trelloService;


    @BeforeTest
    public void beforeAllTests() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("secrets.properties");
        prop.load(stream);
        String API_KEY = prop.get("api_key").toString();
        String TOKEN = prop.get("token").toString();
        SECRET_KEY = "token=" + TOKEN + "&key=" + API_KEY;

        trelloService = BuildRequest.buildRetrofitRequest(TrelloService.class);
    }

    public String getSecretKey() {
        return SECRET_KEY;
    }




    @Test
    public void createBoardTest() throws IOException {
        String boardName = "testBoard_4";
        Board board = new Board(boardName);

        Response<Board> response = trelloService.createBoard(boardName, prop.getProperty("api_key"), prop.getProperty("token")).execute();

        if (response.isSuccessful()) {
            Board createdBoard = response.body();
            boardId = createdBoard.getId();
            System.out.println("Created board ID: " + boardId);
            CheckResponseIsValid.checkBoardFields(response, boardName);
        } else {
            System.err.println("Error: " + response.errorBody().string());
        }
        CheckResponseIsValid.checkResponseCode(response, 200);
    }




    @Test(dependsOnMethods = {"createBoardTest"})
    public void getBoardByIdTest() throws IOException {
        System.out.println("Getting board with ID: " + boardId);

        Response<Board> response = trelloService.getBoard(boardId, prop.getProperty("api_key"), prop.getProperty("token")).execute();

        if (response.isSuccessful()) {
            Board board = response.body();
            System.out.println("Board name: " + board.getName());
            CheckResponseIsValid.checkResponseBodyContains(response, boardId);
        } else {
            System.err.println("Error: " + response.errorBody().string());
        }
        CheckResponseIsValid.checkResponseCode(response, 200);
    }




    @Test(dependsOnMethods = {"getBoardByIdTest"})
    public void updateBoardTest() throws IOException {
        String updatedName = "Updated_TestBoard";

        Response<Board> response = trelloService.updateBoard(boardId, updatedName, prop.getProperty("api_key"), prop.getProperty("token")).execute();

        if (response.isSuccessful()) {
            Board updatedBoard = response.body();
            System.out.println("Updated board name: " + updatedBoard.getName());
            CheckResponseIsValid.checkBoardFields(response, updatedName);
        } else {
            System.err.println("Error: " + response.errorBody().string());
        }
        CheckResponseIsValid.checkResponseCode(response, 200);
    }




    @Test(dependsOnMethods = {"updateBoardTest"})
    public void deleteBoardTest() throws IOException {
        Response<Void> response = trelloService.deleteBoard(boardId, prop.getProperty("api_key"), prop.getProperty("token")).execute();

        if (response.isSuccessful()) {
            System.out.println("Board deleted successfully");
            boardId = null;
        } else {
            System.err.println("Error: " + response.errorBody().string());
        }
        CheckResponseIsValid.checkResponseCode(response, 200);
    }
}
