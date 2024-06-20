#!/usr/bin/env bash

__error() {
    echo >&2 -e "\e[31mERROR:\e[0m $@"
}

__bazel_workspace_check() {
    if ! bazel info > /dev/null 2>&1; then
        __error "Command is only support from within a bazel workspaces (below a directory having a WORKSPACE file)."
        return 1
    fi
    return 0
}

__bazel_workspace() {
    if ! __bazel_workspace_check; then
        return 1
    fi
    echo $(bazel info workspace)
}

# See /python/com/yanicksenn/search/todos/README.md
search_todos() {
    if ! __bazel_workspace_check; then
        return 1
    fi
    bazel run //python/com/yanicksenn/search/todos:todos -- --root_dir=$(__bazel_workspace) $@
}

# See /scripts/bash/templates/binary/python/README.md
create_python_binary() {
    if ! __bazel_workspace_check; then
        return 1
    fi
    bazel run //scripts/bash/templates/binary/python:run -- $(__bazel_workspace) python/com/yanicksen $@
}

# See /scripts/bash/update_license/README.md
update_license() {
    if ! __bazel_workspace_check; then
        return 1
    fi
    bazel run //scripts/bash/update_license:update_license -- $(__bazel_workspace)
}

