# Date proto

A simple proto representing gregorian dates.

## Usage

```build
# In BUILD files:
//protos/date:dates-proto
//protos/date:dates-proto-java
```

```java
// In java files:
Date date = Date.newBuilder()
    .setYear(2024)
    .setMonth(7)
    .setDay(14)
    .build();
```

```protobuf
# In proto files.
import "protos/date/date.proto";
```