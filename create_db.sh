#!/bin/bash

#set -e

#POSTGRES="psql -U gitpod"

#echo "Creating database: dvdrental"

#$POSTGRES <<EOSQL
#CREATE DATABASE dvdrental OWNER gitpod;
#ALTER ROLE gitpod WITH password 'fastcode';
#EOSQL

#echo "Populatng public schema...";
psql -d postgres -f ./dvdrental.sql;
#exit 0;


#RUN psql -U gitpod postgres -c 'create database dvdrental'

# '/bin/sh -c "psql -U gitpod postgres -c 'create database dvdrental;'"'

# /bin/bash -c "psql -U gitpod postgres -c 'create database dvdrental;'"

#RUN "psql -h localhost -U gitpod -d dvdrental -f /dvdrental.sql;"

#RUN psql -U gitpod postgres -c "ALTER ROLE gitpod WITH password 'fastcode'"

#SHELL ["/bin/sh", "-c"] 

#RUN psql -U gitpod postgres -c "create database dvdrental;"