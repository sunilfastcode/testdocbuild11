FROM gitpod/workspace-postgres
USER gitpod

# 
RUN psql -h 127.0.0.1 -d postgres -a -f /dvdrental.sql

# Install Angular CLI
RUN npm install -g @angular/cli@8.3.29
