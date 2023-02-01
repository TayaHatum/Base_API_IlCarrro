package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDto;
import dto.AuthResponseDto;
import dto.ErrorDto;
import okhttp3.*;
import oracle.jrockit.jfr.ActiveSettingEvent;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkHttp {

    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void loginSuccess() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().username("noa@gmail.com").password("Nnoa12345$").build();

        RequestBody requestBody= RequestBody.create(gson.toJson(auth),JSON);
        Request request= new  Request.Builder()
                .url("https://ilcarro-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        AuthResponseDto authResponseDto =
                gson.fromJson(response.body().string(),AuthResponseDto.class);
        System.out.println(authResponseDto.getAccessToken());

    }
    //eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibm9hQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNjc1ODczMzUzLCJpYXQiOjE2NzUyNzMzNTN9.L-vtuPE8oupgboSXjUqXQv6nbmidsBLaf4obdrVXyaM

    @Test
    public void loginWrongEmailFormat() throws IOException {
        AuthRequestDto auth = AuthRequestDto.builder().username("lps@gmail.com").password("Nnoa12345$").build();

        RequestBody requestBody= RequestBody.create(gson.toJson(auth),JSON);
        Request request= new  Request.Builder()
                .url("https://ilcarro-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);

        ErrorDto errorDto =
                gson.fromJson(response.body().string(),ErrorDto.class);
        Assert.assertEquals(errorDto.getError(),"Unauthorized");
        Assert.assertEquals(errorDto.getMessage(),"Login or Password incorrect");



    }



}
