FROM gitpod/workspace-postgres
USER gitpod

#RUN psql create database dvdrental
#RUN psql -d dvdrental -a -U gitpod -f /dvdrental.sql

# Install Angular CLI
RUN npm install -g @angular/cli@8.3.29

COPY ./dvdrental.sql /dvdrental.sql

COPY ./create_db.sh /create_db.sh
RUN sudo chmod +x /create_db.sh

SHELL ["/bin/bash", "-c"] 
RUN source /create_db.sh
SHELL ["/bin/sh", "-c"] 

#RUN pg_start
#RUN psql -h localhost -d postgres -f /dvdrental.sql
#RUN psql -h localhost -d postgres

#SHELL ["/bin/bash", "-c"] 

#RUN psql -U gitpod postgres -c 'create database dvdrental'

# '/bin/sh -c "psql -U gitpod postgres -c 'create database dvdrental;'"'

# /bin/bash -c "psql -U gitpod postgres -c 'create database dvdrental;'"

#RUN "psql -h localhost -U gitpod -d dvdrental -f /dvdrental.sql;"

#RUN psql -U gitpod postgres -c "ALTER ROLE gitpod WITH password 'fastcode'"

#SHELL ["/bin/sh", "-c"] 

#RUN psql -U gitpod postgres -c "create database dvdrental;"

