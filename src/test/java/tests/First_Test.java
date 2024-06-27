package tests;


import models.Member;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import services.TrelloService;
import utils.CheckResponseIsValid;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class First_Test {

    private Properties prop = new Properties();
    private String API_KEY;
    private String TOKEN;
    private TrelloService trelloService;

    @BeforeTest
    public void beforeAllTests() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("secrets.properties");
        prop.load(stream);
        API_KEY = prop.get("api_key").toString();
        TOKEN = prop.get("token").toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.trello.com/1/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        trelloService = retrofit.create(TrelloService.class);
    }

    @Test
    public void firstMethod() throws IOException {
        Response<Member> response = trelloService.getMemberInfo(API_KEY, TOKEN).execute();

        CheckResponseIsValid.checkResponseCode(response, 200);
        CheckResponseIsValid.checkResponseBodyContains(response, "username");

        Member member = response.body();
        if (member != null) {
            System.out.println(member.getUsername());
            System.out.println(member.getId());
            System.out.println(member.getFullName());
        } else {
            System.err.println("Member object is null.");
        }
    }
}
