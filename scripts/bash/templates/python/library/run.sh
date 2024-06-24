#!/usr/bin/env bash

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
  echo >&2 "ERROR: Cannot find templating binary path."
  exit 2
fi

workspace_root=$1
if [ -z "$workspace_root" ]; then
  echo >&2 "ERROR: Workspace root is not set."
  exit 3
fi

package_root=$2
package_name=$3

package_path=$workspace_root/$package_root/$package_name
data_root=$workspace_root/scripts/bash/templates/python/library/data
rules_path=$(rlocation $data_root/RULES)
package_root_python=${package_root////.}

if [[ ! "$package_root_python" =~ ^[a-zA-Z0-9_\.]+$ ]]; then
  echo >&2 "ERROR: Package root is not a valid python pathage path (Runtime error)."
  exit 5
fi

$templating -- \
    --template_path=$data_root \
    --rules_path=$rules_path \
    --target_root=$package_path \
    --__PACKAGE_NAME=$package_name \
    # --debug
