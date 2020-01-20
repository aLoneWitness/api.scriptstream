package scriptstream.logic;

import com.google.gson.Gson;
import okhttp3.*;
import scriptstream.entities.User;

import java.io.IOException;

public class UserVerificationLogic {
    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();

//    public boolean verify(String token) throws IOException {
//        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), token);
//
//        Request request = new Request.Builder()
//                .url("http://localhost:2000/rest/auth/verify")
//                .post(requestBody)
//                .build();
//
//        Response response = httpClient.newCall(request).execute();
//        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//        return true;
//    }

    public User getUser(String token) throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:2000/rest/user/getprofile")
                .header("Authorization", "Bearer " + token)
                .build();

        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        User user = gson.fromJson(response.body().string(), User.class);

        return user;
    }
}
