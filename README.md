# vertx-javaone-presentations

The 'Reactive Distributed Systems with Vert.x' slides and demos. You can find the slides in their source form and in pdf (in the root directory).

## Build the slides

To build the slides, launch:

```
mvn clean package
```

Once built, launch them with:

```
cd slides
java -jar slides-distributed-app-1.0-SNAPSHOT-fat.jar -cluster
```

Slides are served on: http://localhost:9000.

## The demos

Import the demo in your IDE. The libraries are provided in the `libs` directory. So add this directory to your build path.

Launch them demo individually by running the `main` methods.

The node demo is started using `npm start` from the `node` directory.
