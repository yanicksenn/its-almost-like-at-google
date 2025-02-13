#!/bin/bash

bazel run //go/com/yanicksenn/taste:main -- \
	--taste=$(pwd)/go/com/yanicksenn/taste/sample.taste \
	--recipe=$(pwd)/go/com/yanicksenn/taste/sample.recipe
