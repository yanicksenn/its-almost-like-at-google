# Ranges

Small utility library handling numeric ranges. Ranges may be a little bit more expressive and efficient instead of sequences of numbers.  

## Usage

Add this library to your BUILD deps:
```
//java/com/yanicksenn/libraries/ranges
```

Create a range instance:

```java
IntRange.until(3); // [0 .. 2]
IntRange.to(3); // [0 .. 3]
IntRange.until(4, 6); // [4 .. 5]
IntRange.between(4, 6); // [4 .. 6]

IntRange.between(3, 8)
    .stream()
    .forEach(System.out::println);

IntRange.between(3, 7).contains(6); // true
IntRange.between(6, 8).contains(9); // false
```