Инструкция по запуску:
  1. Запустить docker-compose.yml(запустится бд и kafka)
  2. Перейти в папку SecurityService и прописать команду java -jar jarFile/SecurityService-0.0.1-SNAPSHOT.jar     
  3. Перейти в папку book-storage-service и прописать команду java -jar jarFile/book-storage-service-3.3.5.jar     
  4. Перейти в папку book-tracker-service и прописать команду java -jar target/book-tracker-service-0.0.1-SNAPSHOT.jar 
Порты:
  1. SecurityService 8085
  2. book-storage-service 8080
  3. book-tracker-service 8081
