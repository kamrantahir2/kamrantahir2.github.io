package com.example.SpringBootTutorial.web;

// In this class we will handle methods relating to the web server

// Controllers are used to convert JSON but they don't talk to the database correctly.
// To solve this issue we will make a Service class (PhotozService)

import com.example.SpringBootTutorial.model.Photo;
import com.example.SpringBootTutorial.service.PhotozService;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

// We added @RestController. This will add an instance of a RestController for this class
@RestController
public class PhotozController {

    // To use the db that was moved tp PhotozService we make a private final instance of PhotozService
    private final PhotozService photozService;
    // Now instead of using db.values() we now call PhotozService.get().

    // Now that we have moved the db this class essentially doesn't know about the db anymore, instead it knows that the PhotozService instance exists. Then when we call PhotozService.get() this class essentially asks PhotozService to send any photos we have. This way it doesn't matter whether the photos come from a database, file system etc. they are all handled the same way by the Service

    // Since we added @Service to the PhotozService class, Spring knows to look for it and that we need to create an instance of it to be used as a service


    // After inserting the above code we were then prompted by Intellij to add a constructor
    public PhotozController(PhotozService photozService) {
        this.photozService = photozService;
    }

    // This private List is used to represent a database, THIS WILL BE REPLACED:

//    private List<Photo> db = List.of(new Photo(1, "hello.jpeg"));

//    The above List has been replaced with the below Map
    // We are now removing this mock db and moving it to PhotozService:

//    private Map<String, Photo> db = new HashMap<>(){{
//        put("1", new Photo("1", "hello.jpeg"));
//}};

    // To start off we will be making a simple class that prints out Hello World on our localhost:8080 server

    // In simple terms GetMapping activates the specified URL

    // To display something on the web server we need to map it using @GetMapping:
    // The @GetMapping annotation represents a GET request ("/" = homepage)
    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }


    // RETURN A LIST OF PHOTOS AND THEIR DATA
    // Since Spring knows we are returning a List<photo> it automatically converts the data to JSON for the web server
    @GetMapping("/photoz")
    public Iterable<Photo> get() {
        // We need to use db.values to return the full array:
//        return db.values();

        // Now that we have moved our db over to PhotozService and created an instance of PhotozService in this class, instead of doing return db.values() as shown above we now do:
        return photozService.get();
    }

    // GET A SPECIFIC PHOTO
    // We have added an {id} parameter to the URL to get a specific photo
    @GetMapping("/photoz/{id}")
    public Photo get(@PathVariable Integer id) {
        // We have added a parameter of String id which will at as the URL {id} with the marker @PathVariable which tells Spring that the URL {id} will act as the String id

        Photo photo = photozService.get(id);

        // We only want to return a photo if it is available. To handle this error Spring has a specific exception called by throw new ResponseStatusException(HttpStatus.NOT_FOUND) where we call ResponseStatusException() and specify the error.
        if (photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // We now are able to return the full array of photos in our db with /photoz and are able to get a specific photo by its ID. Since we have used @PathVariable in the method parameter it will input the URL id as the method parameter
        return photo;
    }

    // DeleteMapping()- Used to delete a record
    @DeleteMapping("/photoz/{id}")
    public void delete(@PathVariable Integer id) {
        photozService.remove(id);
    }

    // PostMapping() used to upload photos
    // [REF2] USED HERE
    // The @RequestBody annotation tells the web server to convert the entire JSON to a Photo object.
    // Right now a user can input an empty Photo object which will return null. To avoid this issue we added Spring Boot's Validation dependency and added the @Valid annotation which ensures the Photo is valid, We also went to the Photo class and added the @NotEmpty annotation to the filename.

    // Now that we have added the ability to upload a photo we can get rid of the Photo photo parameter:
    // public void create(@RequestBody @Valid Photo photo) {

    // Instead we want to pass through a MultipartFile which is a class available thorugh Spring Boot
    // We also need to add an annotation- @RequestPart("data"). The "data" part tells Spring Boot which part contains the file, this is because in the upload.html file "data"  is the key part of a key:value pair so the file is uploaded as the value
    @PostMapping("/photoz/")
    public Photo create(@RequestPart("data") MultipartFile file) throws IOException {

        // THE COMMENTED OUT CODE BELOW HAS BEEN MOVED TO PhotozService

//        Photo photo = new Photo();

        // For this method we want the front end to send some JSON and Spring should convert it to a photo object
        // WE NEED TO SET THE ID SINCE THE FRONT END WON'T DO THAT
        // The below code generates a random ID as a String:

//        photo.setId(UUID.randomUUID().toString());

        // As part of the MultipartFile class we have access to file.getOriginalFilename() when setting the filename for the photo:

//        photo.setFilename(file.getOriginalFilename());

        // To set the data (byte[] array in the Photo class) we can simply call the setter for the array and pass through file.getBytes() which is also part of the MultipartFile class:

//        photo.setData(file.getBytes());

        Photo photo = photozService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        return photo;
    }

    // Right now we have an issue when we try to upload a photo, we get an error message saying FileSizeLimitExceededException. To solve this we go to the application.properties file and change the max file size and max request size. We can now successfully upload a file.

    // We now have another problem, instead of displaying an image it displays the JSON including the bytes. We will fix this in the Photo class.

    // We created another Controller called DownloadController

}