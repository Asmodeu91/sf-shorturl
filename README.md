**API сервис коротких ссылок**

API:

**GET** /api/url/registration - возвращает uuid пользователя (аналог регистрации)

**POST** /api/url/shorten - генерация короткой ссылки 
   параметры :
*    originalUrl - оригинальная ссылка - обязательное поле
*    userId - uuid пользователя полученный в /api/url/registration - обязательное поле
*    expirationDate - дата окончания срока короткой ссылки в формате 2024-01-01T12:00:00
*    limitVisits - количесво просмотров 

возвращает короткую ссылку в формате http://localhost:8080/api/url/ZTU3ZTcxOWZlMzliZTViYi1kZTFhLTQxODEtOTY5OC1iYmRlMjRjZjVhM2Ex

http://localhost:8080 - base-url , задается в application.yaml 

**GET** /api/url/{shortUrl}

переход по ссылке из короткой в оригинальную

**GET** /api/url/list
параметры :
*    userId - uuid пользователя полученный в /api/url/registration - обязательное поле
возвращает все ссылки пользователя из базы c их параметрами

**PUT** /api/url/update/{id}

редактирование параметров ссылки в базе
{id} ссылки нужно взять из /api/url/list
параметры :
*    newOriginalUrl - оригинальная ссылка - обязательное поле
*    userId - uuid пользователя полученный в /api/url/registration - обязательное поле
*    newExpirationDate - дата окончания срока короткой ссылки в формате 2024-01-01T12:00:00 - обязательное поле
*    limitVisits - количесво просмотров - обязательное поле


**DELETE** /api/url/delete/{id}

удаление короткой ссылки
{id} ссылки нужно взять из /api/url/list
параметры :
*    userId - uuid пользователя полученный в /api/url/registration - обязательное поле

**сборка**

$ ./gradlew bootjar 

**запуск**

$ java -jar build/libs/shoturl-0.0.1-SNAPSHOT.jar

**docker**

docker-compose up -d 

на порту 8080 будет доступно приложение 

















