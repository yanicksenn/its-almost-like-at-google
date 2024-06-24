#!/usr/bin/env bash

workspace_root=$1
if [ -z "$workspace_root" ]; then
  echo >&2 "ERROR: Workspace root is not set."
  exit 1
fi

fork_root=$workspace_root/python/com/yanicksenn/llm/meta/llama3/fork
if [ ! -d "$fork_root" ]; then
  echo "INFO: Creating fork root directory $fork_root."
  mkdir -p "$fork_root"
fi

today=$(date +%Y-%m-%d_%0H-%0M-%0S)
fork_path=$fork_root/$today
if [ -d "$fork_path" ]; then
  echo "ERROR: Fork already exists - try again later."
  exit 2
fi

echo "INFO: Forking llama3 repo from Github into $fork_path."
git clone https://github.com/meta-llama/llama3.git $fork_root/$today

echo "INFO: Forking complete."