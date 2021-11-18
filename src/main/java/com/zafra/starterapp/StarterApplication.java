package com.zafra.starterapp;

import com.zafra.starterapp.handlers.catApi.CatApiConnector;
import com.zafra.starterapp.models.catApi.Breed;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.imageio.ImageIO;

//@SpringBootApplication
public class StarterApplication {

  public static void main(String[] args) throws Exception {
    //FinnHubApiConnector finnHubApiConnector = new FinnHubApiConnector();
    //Questrade questrade = new Questrade(finnHubApiConnector);
    //questrade.handle();

    CatApiConnector catApiConnector = new CatApiConnector();
    //System.out.println(System.getProperty("user.dir"));

    String breed =
        catApiConnector.parseCatBreedFromJson(String.format("%s/%s", System.getProperty("user.dir"), "src/main/resources/catBreed.json"));
    Optional<Breed> catBreedInformation = catApiConnector.getCatBreedInformation(breed);

    if (catBreedInformation.isPresent()) {
      Optional<BufferedImage> maybeCatImage =
          catApiConnector.getCatImageFromApi(catBreedInformation.get().getUrl());

      if (maybeCatImage.isPresent()) {
        System.out.println("Saving the image");
        saveAsFile(maybeCatImage.get());
      }
    }
  }

  public static void saveAsFile(BufferedImage image) throws IOException {
    File outputfile = new File("catImage.jpg");
    ImageIO.write(image, "jpg", outputfile);
  }
}
