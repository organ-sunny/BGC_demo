#!/bin/sh

cd ui
rm -rf build
npm install
npm run build
cd ..

rm -rf src/main/resources/static/*
cp -r ui/build/* src/main/resources/static