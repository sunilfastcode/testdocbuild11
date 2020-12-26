#!/bin/bash
set -e

POSTGRES="psql --username gitpod"

echo "Creating database: dvdrental"

$POSTGRES <<EOSQL
CREATE DATABASE dvdrental OWNER gitpod;
EOSQL

echo "Creating schema..."
psql -d dvdrental -a -U gitpod -f /dvdrental.sql