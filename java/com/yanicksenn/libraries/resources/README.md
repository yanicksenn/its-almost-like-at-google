# Resources

Utility library to parse resource files bundled with a java library or binary.

## Usage

Add this library to your BUILD deps:
```
//java/com/yanicksenn/libraries/resources
```

Parse resource files:
```java
Resource resource = Resource.builder()
    .setModule(getClass())
    .setResourcePath("resources/common-lastnames.txt")
    .build();

// Parse content of a resource line by line.
ImmutableList<String> commonLastnames = resource.parseList();

// Parse raw byte content of resource.
byte[] rawContent = resource.parseRaw();
```