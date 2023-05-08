package com.example.SpringBootTutorial;
// This is the base Application class that was included in the SpringBoot Initializr

// ABOUT
// -------

// - Take photos and upload them to the server
// - Take photos, read them, update then and delete them CRUD

// Photo Requirements:
	// - ID
	// - Filename
	// - Image Data


	// - We need to create a "repository" to use Spring Data, to do that we create a class called repository.PhotozRepository which will automatically create a package called repository if it does not already exist.

	// - The PhotozRepository will actually be an interface and will extend CrudRepository

// DEPLOYMENT:
	// - To deploy our app we click on the maven tab on the right, click on our project -> lifecycle -> double click package

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTutorialApplication.class, args);
	}

}
