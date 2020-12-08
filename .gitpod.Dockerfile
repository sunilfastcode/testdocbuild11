FROM gitpod/workspace-full
USER gitpod

# Install Angular CLI
RUN npm install -g @angular/cli@8.3.29