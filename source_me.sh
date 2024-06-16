#!/usr/bin/env bash

# Source this file to your shell and call `relink`.

# Please don't hate me.
alias blaze="bazel"

cdws() {
	cd /Users/yanicksenn/Documents/Projects/bazel
}

relink_bash() {
	echo "INFO: Relinking bash scripts ..."
	source /Users/yanicksenn/Documents/Projects/bazel/scripts/bash/_scripts.sh
}

relink() {
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