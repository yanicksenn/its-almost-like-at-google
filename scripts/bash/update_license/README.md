# `update_licence`

Example script using [templating](/python/com/yanicksenn/templating/README.md) and `sh_binary` to update the year in the LICENCE file in the workspace root.

## Usage

```bash
bazel run //scripts/bash/update_license:update_license -- $(bazel info workspace)
```