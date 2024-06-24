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

# See /scripts/bash/meta/llama3/README.md
fork_llama3() {
    if ! __bazel_workspace_check; then
        return 1
    fi
    bazel run //scripts/bash/meta/llama3:fork -- $(__bazel_workspace) $@
}

# See /python/com/yanicksenn/search/todos/README.md
search_todos() {
    if ! __bazel_workspace_check; then
        return 1
    fi
    bazel run //python/com/yanicksenn/tools/todos:todos -- --root_dir=$(__bazel_workspace) $@
}

# See /scripts/bash/templates/python/binary/README.md
create_python_binary() {
    if ! __bazel_workspace_check; then
        return 1
    fi
    bazel run //scripts/bash/templates/python/binary:run -- $(__bazel_workspace) python/com/yanicksenn $@
}

# See /scripts/bash/templates/python/library/README.md
create_python_library() {
    if ! __bazel_workspace_check; then
        return 1
    fi
    bazel run //scripts/bash/templates/python/library:run -- $(__bazel_workspace) python/com/yanicksenn/libraries $@
}

