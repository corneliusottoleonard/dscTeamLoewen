#!/bin/bash

lang=$1

if [ "$lang" = "py" ];
then
    python -m grpc_tools.protoc \
        -I. \
        --python_out=./src/python/ \
        --pyi_out=./src/python/ \
        --grpc_python_out=./src/python/ \
        PotField.proto;
else
    echo "Unknown option for language ($lang). Allowed are 'py'."
fi
