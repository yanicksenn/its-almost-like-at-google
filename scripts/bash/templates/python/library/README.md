# Create python librarry

Template for a new python library.

## Usage

```bash
# Creates new python library my_library in python/com/yanicksenn.
bazel run //scripts/bash/templates/python/library:run -- \
    /path/to/bazel/workspace \
    python/com/yanicksenn \
    my_library
```