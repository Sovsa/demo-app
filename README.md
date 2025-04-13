# Demo App

This is a simple demo application that runs alongside a MySQL database using Docker Compose.

## 🚀 Getting Started

Follow these steps to set up and run the application locally using Docker and docker compose.

---

### 1. Build the docker image
After cloning this repository, run `docker build .` within the root of this repository.

### 2. Check the mysql volume
Check within the docker-compose.yaml (root of this repository) file whether the mysql volume path is ok. You can set it up wherever you want.

### 3. Run the application
Run the application by going to the root of the repository and running `docker compose up`

### 4. Visit the page
Unless there is an conflict of ports on your localhost, you should be able to visit the page on http://localhost:8080/
