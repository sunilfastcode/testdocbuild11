FROM gitpod/workspace-postgres
USER gitpod

#COPY dvdrental.sql /dvdrental.sql
#RUN psql -h localhost -d postgres -f /dvdrental.sql
#RUN psql create database dvdrental
#RUN psql -d dvdrental -a -U gitpod -f /dvdrental.sql


#
COPY ./create_db.sh /docker-entrypoint-initdb.d/20-create_db.sh
COPY dvdrental.sql /dvdrental.sql

RUN chmod +x /docker-entrypoint-initdb.d/20-create_db.sh


# Adjust PostgreSQL configuration to accept remote connections 
RUN echo "host all all 0.0.0.0/0 md5" >> /var/lib/postgresql/data/pg_hba.conf

RUN echo "listen_addresses='*'" >> /var/lib/postgresql/data/postgresql.conf

# expose port
EXPOSE 5432

# Install Angular CLI
RUN npm install -g @angular/cli@8.3.29