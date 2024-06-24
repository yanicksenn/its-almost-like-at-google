# Bash scripts

## Usage

Source the `_scripts.sh` file to use the scripts.

```bash
source /Users/yanicksenn/Documents/Projects/bazel/scripts/bash/_scripts.sh
```

## Content

- [fork_llama3](/scripts/bash/meta/llama3/README.md)
- [search_todos](/python/com/yanicksenn/search/todos/README.md)
- [create_python_binary](/scripts/bash/templates/binary/python/README.md)
- [update_licence](/scripts/bash/update_license/README.md)

## Building new scripts

You can use the `sh_binary` rules for your shell scripts and somewhat solve the problem of finding dependency files with the `rlocation` path.

Follow this [guide](https://github.com/bazelbuild/bazel/blob/8fa6b3fe71f91aac73c222d8082e75c69d814fa7/tools/bash/runfiles/runfiles.bash) for more information about `runfiles` and `rlocation`.

However, even the `sh_binary` will be executed in the bazel-out directory. Thus, you'll lose access the bazel workspace. This means you cannot simply run other binaries with `bazel run //...` anymore and instead you'll have to use `rlocation` to resolve binary paths. The binary to call has to be declared as a data dependency in your `sh_binary`.

### Using `sh_binary`

#### `BUILD`
```bash
sh_binary(
    name = "my_binary",
    srcs = ["my_binary.sh"],
    data = [
        # Dependency. Binary that will be run in the script.
        "//python/com/yanicksenn/templating",
    ],
    deps = [
        # Requirement to use rlocation.
        "@bazel_tools//tools/bash/runfiles"
    ], 
)
```

#### `my_binary.sh`
```bash
#!/usr/bin/env bash

# --- begin runfiles.bash initialization v3 ---
# ...
# --- end runfiles.bash initialization v3 ---

templating="$(rlocation __main__/python/com/yanicksenn/templating/templating)"
if [[ ! -f "${templating:-}" ]]; then
  echo >&2 "ERROR: cannot find templating binary path"
  exit 1
fi

$templating \
    --template_path="/path/template" \
    --target_path="/path/target"
```

### Pro

- Using `sh_binary` checks whether dependencies (`deps` and `data`) exist before executing the binary.
- Shell scripts will also be represented as a bazel target.s

### Con

- Binaries that modify files in the bazel workspace require a workaround to refer to the workspace root. E.g.: `bazel run //binary -- $(bazel info workspace)`.

### Example implementation

- See [update_licence](/scripts/bash/update_license/update_license.sh).

