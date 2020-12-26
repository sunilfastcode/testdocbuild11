FROM gitpod/workspace-postgres

# Adjust PostgreSQL configuration to accept remote connections 
RUN echo "host all all 0.0.0.0/0 md5" >> /var/lib/postgresql/data/pg_hba.conf

RUN echo "listen_addresses='*'" >> /var/lib/postgresql/data/postgresql.conf

# expose port
EXPOSE 5432

USER gitpod

# 
RUN psql -h localhost -d postgres -a -f /dvdrental.sql

# Install Angular CLI
RUN npm install -g @angular/cli@8.3.29
