# Kotlin Demo
Let's explore the requirements of the Bottle Rocket Studios test.
Then, let's implement them at scale. =)

## State of the Implementation
Here's where we're at:

  * Almost everything has test coverage.
  * The data layer and the service layers are the most complex.
  * We have a very flexible schema that uses enterprise style key-value pairs.
  * `ContainerService::saveDTO` has a preliminary implementation.
  * `ShowServive::save` has a preliminary implementation.
  * REST/JSON controllers don't exist, but a standard implementation just wraps a service object.
  * I focused my time on modeling an enterprise scalable data structure and schema that is easy
  to marshal/unmarshal.
  * Each component has its own logical package: ad, image, show, etc.
  * Only using `H2` for testing, so this needs permanent storage.
  * Since each logical package would have its own controllers, we can easily use this repo to deploy
  multiple micro-services, if desired, from the same repo.  How?  Simple: each controller would
  have its own profile annotation.  Then, our CI/CD pipeline just deploys the repo to each deployment
  via profile tags, etc.
  * This needs a `Jenkinsfile`
  * Kotlin can be a bit strange... if the tests fail, just re-run them to pass.

## Requirements
The design:

  * You have information on videos for a show, a list of images associated with a show, and a list of video ads associated with a show all considered "assets".
  * Each asset has an ID, a name, a type indicator, a URL, and an expiration date.
  * Videos have a field that indicates if it is a movie, a full episode or a clip.
  * Image assets can be represented by a base asset.
  * Ad assets include a field for a product description.
  * Containers describe a collection of assets.
  * Containers can be considered a "show" with information that includes an ID, name, description, and assets.

To implement:

  * Create a program in Java, Kotlin or Scala that generates at least one container with many assets (at least one of each type) with all properties set. 
  * The program should visit each asset and print information about that asset specific to the type of asset it is.
  
## My implementation notes
  * Container is the abstract, top-level container.
  * All types can be wrapped in a container.
  * Base containers will have a header/meta data.
  * The body of the container will accept any DTO.
  * The first top level container is a Show.
  * Our service layer will contain our business logic.
  * We know from experience that "things can be easy if you let them".  Less is often more.
  * Relational data: we will use a local db for testing
  * Non-relational data: we will use a local db fo testing
  * Package via Docker
  * Deploy dependencies via `docker-compose`; including `Jenkins`.
  * Build file: `Jenkinsfile`
  * Web services built using `Spring Boot`.
  
### Data Structures
  * For collections, we will use the JVM `ConcurrentSkipList*` because we're processing 
  huge collections, and all operations average `O(log(n))`.
  * Our DTOs will be mutable.
  * Our entities will be mutable.
  * Our services will use a utility method to convert from mutable to immutable structures.
  Why?  We use a logarithm to divide collections into pieces, and put those into a `ConcurrentSkipList*`.
  
### Algorithms  
  * We'll use a Radix Sort to sort our collections.  Why?  On average, it performs at
  `O(log(n))`, and we can sort by `id`.
  
## Pros and Cons of Kotlin
### Pros
  * It's Kotlin =)
  * Easy data classes.
  * Easily nullable DTOs to make a wider range of DTOs easier.
  * Easy integration with the JVM and other JVM libraries.
  
### Cons
  * It's Kotlin =(
  * No `Lombok` (at least, we haven't made it work yet...)
  * Some legacy XML is hard to unmarshal (not a problem here =)  
 
## License
This repository is for the sole use of Bottle Rocket Studios.
