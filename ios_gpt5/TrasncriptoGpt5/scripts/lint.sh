#!/usr/bin/env bash
set -e
swiftformat . --lint || true
swiftlint || true
