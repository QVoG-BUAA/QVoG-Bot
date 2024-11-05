#!/bin/bash

PROJ_PATH=${1:?"Project path is required."}
RESULT_PATH=${2:?"Result path is required."}

BUILDER_PATH="/bot/C2Graph"
ENGINE_PATH="/bot/Engine"

# Build graph
OLD=$PWD
cd "$BUILDER_PATH" || exit
# Prepare the project
cat <<EOF > config.json
{
    "host": "localhost",
    "port": 8182,
    "includePath": [],
    "project": "$PROJ_PATH",
    "dir": "",
    "highPrecision": false
}
EOF
java -jar C2Graph.jar > /bot/log/C2Graph.log 2>&1
if [ $? -ne 0 ]; then
    echo "Failed to build graph."
    exit 1
fi
cd "$OLD" || exit

# Run query
OLD=$PWD
cd "$ENGINE_PATH" || exit
java -jar QVoGine.jar --language cxx --style json-compact --format json-compact --output "$RESULT_PATH" 2>/bot/log/Scan.log
if [ $? -ne 0 ]; then
    echo "Failed to run query."
    exit 2
fi
cd "$OLD" || exit
