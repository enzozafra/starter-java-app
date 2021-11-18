package com.zafra.starterapp.handlers.catApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zafra.starterapp.models.catApi.Breed;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import javax.imageio.ImageIO;

public class CatApiConnector {
  private static final String HOST = "api.thecatapi.com";
  private static final String IMAGES_ENDPOINT = "images";
  private static final String SEARCH_ENDPOINT = "search";

  private static final String API_KEY_HEADER = "x-api-key";
  private static final String API_KEY = "45ef8fcc-ab95-40ce-a2ca-44e08d4b9f51";

  private final OkHttpClient client;
  private final Gson gson;

  public CatApiConnector() {
    this.client = new OkHttpClient();
    this.gson = new Gson();
  }

  public String parseCatBreedFromJson(String filePath) throws IOException, JsonParserException {
    String jsonString = readFileAsString(filePath);
    JsonObject json = JsonParser.object().from(jsonString);

    String breedGroup = (String) json.get("breed_group");
    return breedGroup;
  }

  public Optional<Breed> getCatBreedInformation(String breed) {
    HttpUrl.Builder httpUrlBuilder = getHttpUrlBuilder();
    httpUrlBuilder.addPathSegment(IMAGES_ENDPOINT);
    httpUrlBuilder.addPathSegment(SEARCH_ENDPOINT);
    httpUrlBuilder.addQueryParameter("breed_ids", breed);

    Request request = new Request.Builder()
        .url(httpUrlBuilder.build())
        .addHeader(API_KEY_HEADER, API_KEY)
        .build();

    try {
      Response response = client.newCall(request).execute();
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }

      List<Breed> breeds = gson.fromJson(response.body().string(), new TypeToken<List<Breed>>() {
      }.getType());

      if (!breeds.isEmpty()) {
        return Optional.of(breeds.get(0));
      } else {
        return Optional.empty();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return Optional.empty();
    }
  }

  public Optional<BufferedImage> getCatImageFromApi(String url) {
    Request request = new Request.Builder()
        .url(url)
        .addHeader(API_KEY_HEADER, API_KEY)
        .build();

    try {
      Response response = client.newCall(request).execute();
      if (!response.isSuccessful()) {
        throw new IOException("Unexpected code " + response);
      }

      InputStream inptStream = response.body().byteStream();
      BufferedImage image = ImageIO.read(inptStream);

      return Optional.of(image);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return Optional.empty();
    }
  }

  private static String readFileAsString(String file) throws IOException {
    return new String(Files.readAllBytes(Paths.get(file)));
  }

  private HttpUrl.Builder getHttpUrlBuilder() {
    return new HttpUrl.Builder()
        .scheme("https")
        .host(HOST)
        .addPathSegment("v1");
  }
}
