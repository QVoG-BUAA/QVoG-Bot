#!/bin/bash

#
# Copyright (C) QVoG@BUAA 2024
# Programmed by Tony S.
#

PYTHON2GRAPH_URL="https://github.com/QVoG-BUAA/Python2Graph/archive/refs/tags/v1.0.zip"
C2GRAPH_URL="https://github.com/QVoG-BUAA/C2Graph/releases/download/v1.0/C2Graph.jar"
QVOG_ENGINE_URL="https://github.com/QVoG-BUAA/QVoG-Engine/releases/download/v1.0/QVoGine.jar"
QVOG_QUERY_URL="https://github.com/QVoG-BUAA/QVoG-Query/releases/download/v1.0/Query.jar"

# in case the bot directory does not exist
mkdir -p bot

echo "Initializing Python2Graph..."
rm -rf bot/Python2Graph
wget "$PYTHON2GRAPH_URL" -O /tmp/Python2Graph.zip
unzip /tmp/Python2Graph.zip -d bot && rm /tmp/Python2Graph.zip
mv bot/Python2Graph-1.0 bot/Python2Graph
cat <<EOF > bot/Python2Graph/config.yaml
BACKEND:
  DATABASE: "gremlin"
  GREMLIN:
    CONNECTION_STRING: "ws://localhost:8182/gremlin"
CACHE:
  DATABASE: "memory"
EOF

echo "Initializing C2Graph..."
rm -rf bot/C2Graph && mkdir -p bot/C2Graph
wget "$C2GRAPH_URL" -O bot/C2Graph/C2Graph.jar

echo "Initializing QVoG Engine & Query..."
rm -rf bot/Engine
mkdir -p bot/Engine bot/Engine/lib
wget "$QVOG_ENGINE_URL" -O bot/Engine/QVoGine.jar
wget "$QVOG_QUERY_URL" -O bot/Engine/lib/Query.jar
cat <<EOF > bot/Engine/config.yaml
{
    "gremlin": {
        "host": "localhost",
        "port": 8182
    },
    "cache": {
        "type": "none"
    }
}
EOF

echo "Initialization complete!"
