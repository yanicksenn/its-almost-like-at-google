#!/usr/bin/env bash

# Run this script as:
# bazel run //scripts/bash/search:search-todos -- $(bazel info workspace)
# bazel run //scripts/bash/search:search-todos -- $(bazel info workspace) --context=$USER
# bazel run //scripts/bash/search:search-todos -- $(bazel info workspace) --debug

# --- begin runfiles.bash initialization v3 ---
# Copy-pasted from the Bazel Bash runfiles library v3.
set -uo pipefail; set +e; f=bazel_tools/tools/bash/runfiles/runfiles.bash
# shellcheck disable=SC1090
source "${RUNFILES_DIR:-/dev/null}/$f" 2>/dev/null || \
  source "$(grep -sm1 "^$f " "${RUNFILES_MANIFEST_FILE:-/dev/null}" | cut -f2- -d' ')" 2>/dev/null || \
  source "$0.runfiles/$f" 2>/dev/null || \
  source "$(grep -sm1 "^$f " "$0.runfiles_manifest" | cut -f2- -d' ')" 2>/dev/null || \
  source "$(grep -sm1 "^$f " "$0.exe.runfiles_manifest" | cut -f2- -d' ')" 2>/dev/null || \
  { echo>&2 "ERROR: cannot find $f"; exit 1; }; f=; set -e
# --- end runfiles.bash initialization v3 ---

search_todos="$(rlocation __main__/python/com/yanicksenn/search/search-todoos)"
if [ ! -f "$search_todos" ]; then
  echo >&2 "ERROR: Cannot find search-todos binary path."
  exit 1
fi

$search_todos -- $@

