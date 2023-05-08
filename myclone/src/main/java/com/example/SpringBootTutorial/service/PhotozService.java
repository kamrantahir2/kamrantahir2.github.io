package com.example.SpringBootTutorial.service;

// The Controller classes handle JSON well but have issues interacting with databases, instead of using a controller we made this PhotozService class to handle talking with databases

import com.example.SpringBootTutorial.model.Photo;
import com.example.SpringBootTutorial.repository.PhotozRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


// We can use both @Component or @Service here, We chose @Service since it has more features
// @Component
@Service
public class PhotozService {

    // To start connecting to our database we first need to create a field for PhotozRepository and add the constructor which Intellij prompts us about:
    private final PhotozRepository photozRepository;
    // Now that we have created the field for PhotozRepository we have to change the
    // methods to return from the photozRepository



    // This mock db was moved over from PhotozController
//    private Map<String, Photo> db = new HashMap<>(){{
//        put("1", new Photo("1", "hello.jpeg"));
//    }};

    public PhotozService(PhotozRepository photozRepository) {
        this.photozRepository = photozRepository;
    }

    // This method initially returned Collection<Photo> but it threw an error when switching to PhotozRepository so Intellij suggested we change the return type to Iterable
    public Iterable <Photo> get() {
        return photozRepository.findAll();
    }

    public Photo get(Integer id) {
        // We did .orElse(null) so if we can't find the photo we return null
        return photozRepository.findById(id).orElse(null);
    }

    public void remove(Integer id) {
        photozRepository.deleteById(id);
    }

    // These parameters are inserted in PhotozController where we can make use of file.getOriginalFilename() and file.getBytes() as part of the MultipartFile class that we use in PhotozController
    public Photo save(String filename, String contentType, byte[] data) {
        Photo photo = new Photo();
        photo.setContentType(contentType);
        // We don't need to set the ID anymore since it comes with our database
//        photo.setId(UUID.randomUUID().toString());
        photo.setFilename(filename);
        photo.setData(data);
        photozRepository.save(photo);
        return photo;
    }


}