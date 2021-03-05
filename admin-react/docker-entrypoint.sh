#!/bin/bash

set -e

if [[ "$ADMIN_RUN" == "yes" ]]; then
    if [[ "$PROJECT_MODE" == "development" ]]; then
        npm start
    else
        npm build
    fi
fi
