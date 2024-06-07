#!/usr/bin/env bash

# Please don't hate me.
alias blaze="bazel"

cdws() {
	cd /Users/yanicksenn/Documents/Projects/bazel
}

commit() {
	message=$1
	hg add .
	hg commit -m "$1"

	git add .
	git commit -m "$1"
	git push
}