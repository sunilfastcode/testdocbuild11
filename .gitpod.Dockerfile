FROM gitpod/workspace-full
USER gitpod

#RUN psql create database dvdrental
#RUN psql -d dvdrental -a -U gitpod -f /dvdrental.sql

# Install Angular CLI
RUN npm install -g @angular/cli@8.3.29

COPY ./dvdrental.sql /dvdrental.sql
#RUN pg_start
#RUN psql -h localhost -d postgres -f /dvdrental.sql
#RUN psql -h localhost -d postgres