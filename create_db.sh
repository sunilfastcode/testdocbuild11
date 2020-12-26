#!/bin/bash
set -e

POSTGRES="psql -U gitpod"

echo "Creating database: dvdrental"

$POSTGRES <<EOSQL
CREATE DATABASE dvdrental;
ALTER ROLE gitpod WITH password 'fastcode';
EOSQL

echo "Creating schema..."
psql -d dvdrental -f /dvdrental.sql


#RUN psql -U gitpod postgres -c 'create database dvdrental'

# '/bin/sh -c "psql -U gitpod postgres -c 'create database dvdrental;'"'

# /bin/bash -c "psql -U gitpod postgres -c 'create database dvdrental;'"

#RUN "psql -h localhost -U gitpod -d dvdrental -f /dvdrental.sql;"

#RUN psql -U gitpod postgres -c "ALTER ROLE gitpod WITH password 'fastcode'"

#SHELL ["/bin/sh", "-c"] 

#RUN psql -U gitpod postgres -c "create database dvdrental;"