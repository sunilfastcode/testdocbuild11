FROM gitpod/workspace-postgres
USER gitpod

COPY dvdrental.sql /dvdrental.sql
RUN psql create database dvdrental
RUN psql -d dvdrental -a -U gitpod -f /dvdrental.sql

# Install Angular CLI
RUN npm install -g @angular/cli@8.3.29