FROM gitpod/workspace-postgres
USER gitpod

COPY dvdrental.sql /dvdrental.sql
RUN psql create database dvdrentalRUN psql -d dvdrental -a -U postgres -f /dvdrental.sql

# Install Angular CLI
RUN npm install -g @angular/cli@8.3.29