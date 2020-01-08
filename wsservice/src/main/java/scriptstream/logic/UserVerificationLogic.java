package scriptstream.logic;

import com.google.gson.Gson;
import okhttp3.*;
import scriptstream.entities.User;

import java.io.IOException;

public class UserVerificationLogic {
    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();

    public User verify(User user) throws IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(user));

        Request request = new Request.Builder()
                .url("http://localhost:2000/rest/auth/login")
                .post(requestBody)
                .build();

        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        user = gson.fromJson(response.body().string(), User.class);
        user.setName(user.getName());

        return user;
    }
}
