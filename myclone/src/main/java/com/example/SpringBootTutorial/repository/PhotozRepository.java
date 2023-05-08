package com.example.SpringBootTutorial.repository;

import com.example.SpringBootTutorial.model.Photo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// This interface will be used for adding to the database.

// The CrudRepository class gives us different useful methods such as Find All. Delete,
// Save to Database and many more.

// The next step is adding annotations to our database table from the Photo class


// When setting the data types for CrudRepository we first define the object type then the ID type, Our ID is of type Integer so we do the following:
public interface PhotozRepository extends CrudRepository<Photo, Integer> {



}