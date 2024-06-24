# Create python binary

Template for a new python binary.

## Usage

```bash
# Creates new python binary my_binary in python/com/yanicksenn.
bazel run //scripts/bash/templates/python/binary:run -- \
    /path/to/bazel/workspace \
    python/com/yanicksenn \
    my_binary
```