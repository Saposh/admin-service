# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim

# Указываем автора
LABEL authors="Sapow"

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем JAR-файл в контейнер
COPY target/*.jar app.jar

# Открываем порт, на котором будет работать приложение
EXPOSE 8080

# Запускаем Spring Boot приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
