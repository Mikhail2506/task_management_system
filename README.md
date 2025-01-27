Task Management System

## Стек:
Java 17, Spring Boot 3.4.0, PostgreSQL, Swagger

## Установка и настройка:
Это Spring Boot приложение для управления задачами.
Оно использует PostgreSQL в качестве базы данных и Docker для контейнеризации.

Требования: установленный Docker и Docker Compose.

Запуск приложения.
1. Клонирование репозитория.
   Склонируйте репозиторий на ваш компьютер.

2. Запуск с использованием Docker Compose.
   Для запуска приложения и базы данных PostgreSQL в рабочей директории выполните следующие команды:

 - соберите Docker-образ для приложения: docker-compose build;
 - запустите контейнеры: docker-compose up -d.

После выполнения этой команды PostgreSQL будет доступен на порту 5433.

Spring Boot приложение будет доступно на порту 8080(http://localhost:8080/swagger-ui/index.html#/).

По умолчанию (при первом запуске приложения) создается администратор - userName = Author, role = ADMIN, password  = 100.
Для регистрации пользователей аутентификация не требуется. 
Jwt токен зарегистрированных пользователей(по email и  password) доступен на EP: http://localhost:8080/api/v1/auth.
Для работы с EP приложения зарегистрированного пользователя нужно аутентифицировать в Swagger(ввести токен.
Задачи создает ADMIN только для зарегистриррованных пользователей.
Пользователи регистрируются на EP http://localhost:8080/swagger-ui/index.html#/Authentication%20controller/registerNewUser.
3. Проверка работы PostgreSQL.

Подключитесь к базе данных через порт 5433 на вашем ПК:

psql -h localhost -p 5433 -U postgres -d task_management

Spring Boot приложение:

Откройте браузер и перейдите по адресу: http://localhost:8080.

Проверьте логи приложения  - docker logs task_management_app
4. Остановка контейнеров
   Чтобы остановить контейнеры, выполните команду - docker-compose down