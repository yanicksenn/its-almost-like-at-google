#!/usr/bin/env bash

# Source this file to your shell and call `relink`.

# Please don't hate me.
alias blaze="bazel"

__bazel_root_check() {
    if [ -z "$__BAZEL_ROOT" ]; then
		echo >&2 -e "\e[31mERROR: __BAZEL_ROOT must be set.\e[0m"
        return 1
    fi
	if [ ! -f "$__BAZEL_ROOT/WORKSPACE" ]; then
		echo >&2 -e "\e[31mERROR: __BAZEL_ROOT must point to the root of a bazel workspace.\e[0m"
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

	echo -e "\e[32mINFO: Relinking bash scripts ...\e[0m"
	source "$__BAZEL_ROOT/scripts/bash/_scripts.sh"
}

relink() {
	__bazel_root_check

	echo -e "\e[32mINFO: Relinking scripts ...\e[0m"
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