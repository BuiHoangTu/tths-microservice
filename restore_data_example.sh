#!/bin/sh
# this script may need higher authority to perform
SCRIPT=$(readlink -f "$0")
SCRIPT_PATH=$(dirname "$SCRIPT")

rm -r "$SCRIPT_PATH"/data-example

cp -r "$SCRIPT_PATH"/data-example.bak data-example
