#!/usr/bin/env bash

bazel run //python/com/yanicksenn/templating:templating -- \
    --template_path=/Users/yanicksenn/Documents/Projects/bazel/python/com/yanicksenn/templating/test/template.json \
    --target_path=/Users/yanicksenn/Documents/Projects/bazel/python/com/yanicksenn/templating/test
