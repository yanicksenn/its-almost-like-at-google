# `search_todos`

Finds all TODOs in the bazel workspace.

## Usage

```bash
# Finds all TODOs with any context in the workspace.
bazel run //scripts/bash/search/todos:todos -- $(bazel info workspace)

# Finds the TODOs with the given context in the workspace.
bazel run //scripts/bash/search/todos:todos -- $(bazel info workspace) --context=$USER

# Finds all TODOs with any context in the workspace and returns some debugging information.
bazel run //scripts/bash/search/todos:todos -- $(bazel info workspace) --debug
```

