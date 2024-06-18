#!/usr/bin/env bash

__bazel_workspace_check() {
    if ! bazel info > /dev/null 2>&1; then
        echo "ERROR: Command is only support from within a bazel workspaces (below a directory having a WORKSPACE file)."
        return 1
    fi
    return 0
}

# See /scripts/bash/search/README.md
search_todos() {
    if ! __bazel_workspace_check; then
        return 1
    fi
    bazel run //scripts/bash/search:search-todos -- $(bazel info workspace) $@
}

# See /scripts/bash/update_license/README.md
update_license() {
    if ! __bazel_workspace_check; then
        return 1
    fi
    bazel run //scripts/bash/update_license:update_license -- $(bazel info workspace)
}

