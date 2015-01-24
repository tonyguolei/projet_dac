#! /usr/bin/env bash

find ./target -name "*_.java" -exec rm -f {} \;
find ./target -name "*_.class" -exec rm -f {} \;
