#!/usr/bin/env bash

bazel run //python/com/yanicksenn/templating:templating -- \
    --template_path=/Users/yanicksenn/Documents/Projects/bazel/scripts/bash/update_license/LICENSE.template \
    --target_path=/Users/yanicksenn/Documents/Projects/bazel/LICENSE.out \
    --__YEAR=$(date +%Y)

rm -r /Users/yanicksenn/Documents/Projects/bazel/LICENSE
mv /Users/yanicksenn/Documents/Projects/bazel/LICENSE.out /Users/yanicksenn/Documents/Projects/bazel/LICENSE