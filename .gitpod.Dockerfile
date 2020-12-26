FROM gitpod/workspace-postgres
USER gitpod

#RUN psql create database dvdrental
#RUN psql -d dvdrental -a -U gitpod -f /dvdrental.sql

# Install Angular CLI
RUN npm install -g @angular/cli@8.3.29

COPY ./dvdrental.sql /dvdrental.sql
#RUN pg_start
#RUN psql -h localhost -d postgres -f /dvdrental.sql
#RUN psql -h localhost -d postgres

SHELL ["/bin/bash", "-c"] 

RUN "psql -U gitpod postgres -c 'create database dvdrental;'"

RUN "psql -h localhost -U gitpod -d dvdrental -f /dvdrental.sql;"

RUN psql -U gitpod postgres -c "ALTER ROLE gitpod WITH password 'fastcode'"

SHELL ["/bin/sh", "-c"] 

