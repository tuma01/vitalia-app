FROM mysql:8.0.25

# Definir variables de entorno
ENV MYSQL_ROOT_PASSWORD=bolivia
ENV MYSQL_DATABASE=hospital_db

EXPOSE 3306