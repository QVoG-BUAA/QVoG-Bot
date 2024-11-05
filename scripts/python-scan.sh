#!/bin/bash

PROJ_PATH=${1:?"Project path is required."}
RESULT_PATH=${2:?"Result path is required."}

BUILDER_PATH="/bot/Python2Graph"
ENGINE_PATH="/bot/Engine"

# Build graph
OLD=$PWD
cd "$BUILDER_PATH" || exit
bash "$BUILDER_PATH/build.sh" default "$PROJ_PATH" > /dev/null 2> /bot/log/Python2Graph.log
if [ $? -ne 0 ]; then
    echo "Failed to build graph."
    exit 1
fi
cd "$OLD" || exit

# Run query
OLD=$PWD
cd "$ENGINE_PATH" || exit
java -jar QVoGine.jar --language python --style json-compact --format json-compact --output "$RESULT_PATH" 2>/bot/log/Scan.log
if [ $? -ne 0 ]; then
    echo "Failed to run query."
    exit 2
fi
cd "$OLD" || exit
