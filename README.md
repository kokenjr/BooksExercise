# Books Exercise
Android Exercise demonstrating the ability to fetch a list of books from a REST Endpoint.


## External Libraries

* [Retrofit 2](http://square.github.io/retrofit/)
* [Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson)

## Test

You can run the integration test with the following command:

```./gradlew connectedAndroidTest```

## Notes
* The `two-hour-mark` branch includes all code written within two hours.
* The `master` branch includes the following additions:
	* Error handling
	* Integration Test
	* Swipe to refresh
* Without the 2 library limitation I would've used the following:
	* Glide/Picasso for image loading
	* RxAndroid for async tasks and network calls.
	* ButterKnife for view binding
* Given more time I would have done the following:
	* Added search functionality
	* Added sorting
	* Added filtering by author
	* Added better placeholder for book cover, while images are being loaded. 
	* Styled the book cover images a bit better (rounded corners, identical dimensions, etc.)
	* Added App Icon
* Generated and signed APKs for both branches are available in the root of the project.
