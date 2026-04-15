#!/bin/bash
echo "Downloading ClassicModels database..."
wget -qO classicmodels.zip https://www.mysqltutorial.org/wp-content/uploads/2018/03/mysqlsampledatabase.zip
unzip -oq classicmodels.zip

echo "Preparing init scripts..."
rm -rf mysql-init
mkdir -p mysql-init
mv mysqlsampledatabase.sql mysql-init/1-schema-data.sql
rm -f classicmodels.zip

echo "Starting Docker Compose for MySQL..."
docker compose up -d mysql-db

echo "Wait a few seconds for MySQL to be ready..."
sleep 15
docker ps
echo "Database setup complete."
