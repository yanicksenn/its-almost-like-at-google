#!/usr/bin/env bash

# Run this script as:
# bazel run //scripts/bash/update_license:update_license -- $(bazel info workspace)

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

templating="$(rlocation __main__/python/com/yanicksenn/templating/templating)"
if [[ ! -f "${templating:-}" ]]; then
  echo >&2 "ERROR: cannot find templating binary path"
  exit 1
fi

workspace_root=$1
license_path=$workspace_root/LICENSE
license_out_path=$license_path.out

$templating -- \
    --template_path=$(rlocation __main__/scripts/bash/update_license/data/LICENSE.template) \
    --target_path=$license_out_path \
    --__YEAR=$(date +%Y)
echo "INFO: Successfully written $license_out_path."

if [ -f "$license_path" ]; then
    rm -r $license_path
    echo "INFO: Successfully removed $license_path."
fi

mv $license_out_path $license_path
echo "INFO: Successfully replaced $license_path."