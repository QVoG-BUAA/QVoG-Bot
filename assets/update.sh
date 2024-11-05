#!/bin/bash

base_dir=${1:?"Missing source directory"}

cp "$base_dir"/*.jar bot/application.jar
cp -r "$base_dir"/scripts bot/
cp -r "$base_dir"/templates bot/
cp -r "$base_dir"/dist bot/
