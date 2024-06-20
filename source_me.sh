#!/usr/bin/env bash

# Source this file to your shell and call `relink`.

# Please don't hate me.
alias blaze="bazel"

__error() {
    echo >&2 -e "\e[31mERROR:\e[0m $@"
}

__info() {
    echo >&2 -e "\e[32mINFO:\e[0m $@"
}

__bazel_root_check() {
    if [ -z "$__BAZEL_ROOT" ]; then
		__error "__BAZEL_ROOT must be set."
        return 1
    fi
	if [ ! -f "$__BAZEL_ROOT/WORKSPACE" ]; then
		__error "__BAZEL_ROOT must point to the root of a bazel workspace."
        return 2
	fi
    return 0
}

cdws() {
	__bazel_root_check

	cd $__BAZEL_ROOT
}

relink() {
	__bazel_root_check

	__info "Relinking bash scripts ..."
	source "$__BAZEL_ROOT/scripts/bash/_scripts.sh"
}
