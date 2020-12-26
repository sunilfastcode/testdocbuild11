FROM gitpod/workspace-postgres
USER gitpod

# 
RUN psql -h localhost -d postgres -a -f /dvdrental.sql

# Install Angular CLI
RUN npm install -g @angular/cli@8.3.29
