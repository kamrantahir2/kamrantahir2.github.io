package com.example.SpringBootTutorial.web;

import com.example.SpringBootTutorial.model.Photo;
import com.example.SpringBootTutorial.service.PhotozService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DownloadController {

    // This class also needs an instance of PhotozService as shown in PhotozController (The notes can be found in PhotozController)
    // This PhotozService class will be used to retrieve photos
    private final PhotozService photozService;

    public DownloadController(PhotozService photozService) {
        this.photozService = photozService;
    }



    // Instead of just returning a byte[] array we are returning ResponseEntity<byte[]> which is part of Spring Boot because we have the byte[] array but we also want to send back some HTTP headers including the file name
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Integer id) {

        // We search for the photo using photozService.get(id) and check to see if it is null, if so (meaning the photo doesn't exist) then we throw an error
        Photo photo = photozService.get(id);
        if (photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // Now that we have searched for the photo and ensured that it exists we can call photo.getData()
        byte[] data = photo.getData();

        // HttpHeaders is part of Spring Boot
        HttpHeaders headers = new HttpHeaders();

        // We now have the data to send and we have our HttpStatus, the final step to handling actual images instead of raw data is setting headers

        // First we need to define our content type, to do this we first went to the Photo class and added a String field called contentType

        // After creating the field we can now do MediaType.valueOf(photo.getContentType())
        headers.setContentType(MediaType.valueOf(photo.getContentType()));

        // The browser also needs a file name and we need to decide whether the browser should display the image or download it:
        // attachment downloads it and inline displays it

        ContentDisposition build = ContentDisposition
                .builder("attachment")
                .filename(photo.getFilename())
                .build();

        // Now that we have built the header by setting the content type and building the ContentDisposition then setting it to the header.
        headers.setContentDisposition(build);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

}