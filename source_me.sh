#!/usr/bin/env bash

# Source this file to your shell and call `relink`.

# Please don't hate me.
alias blaze="bazel"

__bazel_root_check() {
    if [ -z "$__BAZEL_ROOT" ]; then
        echo "ERROR: __BAZEL_ROOT must be set."
        return 1
    fi
	if [ ! -f "$__BAZEL_ROOT/WORKSPACE" ]; then
        echo "ERROR: __BAZEL_ROOT must point to the root of a bazel workspace."
        return 2
	fi
    return 0
}

cdws() {
	__bazel_root_check

	cd $__BAZEL_ROOT
}

relink_bash() {
	__bazel_root_check

	echo "INFO: Relinking bash scripts ..."
	source "$__BAZEL_ROOT/scripts/bash/_scripts.sh"
}

relink() {
	__bazel_root_check

	echo "INFO: Relinking scripts ..."
	relink_bash
}

commit() {
	message=$1
	hg add .
	hg commit -m "$1"

	git add .
	git commit -m "$1"
	git push
}