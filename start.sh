#!/bin/bash
docker build . -t candidateinteractions
docker run -p 8080:8080 candidateinteractions
