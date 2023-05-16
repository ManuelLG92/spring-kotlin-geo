# Backend Share now challenge

## How run the application
* Locally with the help of IntellJ IDEA is way better to debug and 
try changes. In the following path: 
```com/api/shared/Application.kt```
clicking in the green arrow at the left of the ```main``` function.


* Docker container
````
docker-compose up --build 
````

* The application will run in the port `8080`
---
## Architecture
* I tried to use a kind of clean architecture, using application, 
infrastructure, and domain layer. I couldn't fulfill as I wanted due 
time reasons.
* I used as much as I could the Dependency Injection, that's a fantastic
tool for accomplish the Dependency Inversion of Solid principles and doing 
easy the testing mocking dependencies.
* I used gradle instead of maven just because I had a scaffold
that I could use to separate the application in contexts
following a microservice approach.
* I build a basic gateway to allow the communication between
contexts.
* The context that is more built is the `location`, 
the `vehicles` has some lacks and some dto's that can be refactored
applying a better design.
* I would love to build the API with a completely hexagonal and
clean architectures, but these kind od methodologies 
implies more time than the usual.

---
## Acceptance Criteria API
* The API is implemented in Java or Kotlin using preferably Spring Boot as the framework,
  but feel free to use any other one that you'd be familiar with.
   * Framework: Spring Boot
   * Programming language: Kotlin (I loved it)
---
* The API defines an endpoint that can be used to fetch vehicles. 
A vehicle contains the ID of the polygon in which it is located at the time.
  * There are 2 endpoints:
    * `GET /vehicles` -> Vehicles list with the polygon id where they are located
    * `GET /vehicles/{vin}` -> Single vehicle by the VIN field with the polygon where it’s located
--- 
* The API defines an endpoint that can be used to fetch polygons. 
A polygon contains the  VINs of vehicles that are currently inside
the given polygon.
  * There are 3 endpoints:
    * `GET /polygons` returns all polygons without vehicles 
    * `GET /polygons/vehicles` returns all polygons with the vehicles 
that are inside each polygon
    * `GET /polygons/{id}//vehicles` returns single polygon by the id field
with the vehicles that are inside it.
---
* The API keeps the list of vehicles for the location “Stuttgart” up to date 
by fetching them regularly in the background.
  * Done each 5 minutes using `@Scheduled(fixedDelay = 300000)` in a function
beforehand enabling the schedule in the file `Application.kt`
with the annotation `@EnableScheduling`.
---
* A test suite containing useful Unit and Integration Tests is provided.
  * Unit test: 
    * `GetPolygonsWithVehiclesHandlerTest.kt`
      * In this test we can take a look a unit testing mocking the 
     repository dependency and verifying the function inputs 
     capturing slots.
    * `PolygonsArrayTest.kt`
      * In this test, we're testing the function that loads the
      polygons from a json file and return an array of `Polygons`.
      * fI used a small json file with only one polygon making the
      tests faster because will be the same for N item.
      * There's strict assertions from the loaded source with an
      expected stub.
    * `HelperTest.kt`
      * This last test covers a helper function that receive a map
      and return a list the class `PolygonWithVehicles`. 
      * There are assertions with a stub to ensure the map works properly
  * Integration Tests (`/test-integration`):
    * `HealthcheckAcceptanceTest.kt`: This is a standard practice to have an
    endpoint to check your application status.
    * `GetPolygonIntegrationTest.kt`: This test check the response type of
    the endpoint `GET /polygons` just validating the structure, since the value
    assertions its more suitable to do using the unit testing.
    For this case, we can assert strict the data size because we've
    a static file with the polygons, otherwise is not the best suitable approach.
    * `GetVehiclesIntegrationTest.kt`: This test only check the response types
    and the exceptions is the vehicle does not exist.
    We cannot assert strictly the sata size because these items came
    from a dynamic source (API in our case, but from a database will eb the same)
---
* A Dockerfile is provided to start the application as a container.
  * There’s a Dockerfile and a docker-compose file.
  However, we can run the application with Docker 
  I highly recommend to run with IntelliJ IDEA 
  which is so great and helpful if you want to debug.
---
* A Swagger/Open API spec is provided that documents the API.
  * I used the spring open-api pluggin to autogenerate the
  api documentation. This is the [url](http://localhost:8080/swagger-ui/index.html)
---


### Finally
I would like to give you the thanks for giving me this
opportunity because I've enjoyed doing this and learned
a lot as well. 

I'd like if you give me the opportunity to explain my
thoughts more widely and of course solve your doubts,
get some feedback from your experience on how to do better,
and having a nice conversation as we had.

Thanks,

Sincerely, Manuel Leon.

Kind regards.



    



