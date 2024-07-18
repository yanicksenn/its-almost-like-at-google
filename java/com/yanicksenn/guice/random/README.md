# Random Guice Module

Guice module providing randomization capabilities.

## Usage

### Basic setup

BUILD
```build
"//java/com/yanicksenn/guice/random"
```

Module.java
```java
install(new Random.Module());
```

MyService.java
```java
@Inject
MyService(Random random) {
    this.random = random;
}
```

### Overriding seed
Module.java
```java
@Provides
@Random.Annotations.Seed
long provideSeed() {
    return 1234L;
}
```

### Methods
MyService.java
```java
random.from(ImmutableList.of(1, 2, 3)); // [1 .. 3]
random.between(-10, 10); // [-10 .. 10]

// Using ranges:
random.inRange(IntRange.until(10));
random.inRange(LongRange.until(10L));
```