package com.example.SpringBootTutorial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

// We need to add annotations to this Photo class so we can map our class to our database table

// This annotation connects to the Photoz table in our database. Note that we HAVE TO use uppercase letters when writing the name of our tables
@Table("PHOTOZ")
public class Photo {

    // This tells Spring that this ID field connects to the ID field in the database
    // Now that we have changed the ID from a String to Integer we need to go back to
    // PhotozService to fix some errors.
   @Id
    private Integer id;

    // The @NotEmpty annotation makes sure that the filename is not empty
    @NotEmpty
    private String fileName;

    // Used to set headers in DownloadController, We will be initializing by adding it to the constructor and then we set it in PhotozService.save()
    private String contentType;

    // When we get the JSON on the web server it includes the metadata (bytes[] array) which is huge and clogs up the screen. To avoid this we simply add the annotation @JsonIgnore
    @JsonIgnore
    private byte[] data;

    // MAKE SURE TO ALWAYS HAVE AN EMPTY CONSTRUCTOR TO AVOID ISSUES WITH JSON
    public Photo() {

    }

//    public Photo(String id, String filename) {
//        this.id = id;
//        this.filename = filename;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return fileName;
    }

    public void setFilename(String filename) {
        this.fileName = filename;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}