
# Сервер таблицы лидеров для игры Rogal

Данный сервер предоставляет API для работы с таблицей лидеров игры.

## Требования

- Java 11 или выше
- Maven 3.6 или выше

## Запуск сервера

### Запуск на стандартном порту (8080)

```bash
java -jar leaderboard-server-1.0.0.jar
```

### Запуск с указанием IP и порта

```bash
java -jar leaderboard-server-1.0.0.jar --server.address=192.168.1.10 --server.port=8090
```

Или можно изменить настройки в файле `application.properties`:

```properties
server.port=8090
server.address=192.168.1.10
```

## API Endpoints

### Отправка результата

POST `/api/leaderboard/submit`

Пример запроса:
```json
{
  "playerName": "Player1",
  "wave": 10,
  "kills": 150,
  "score": 1500
}
```

### Получение топ результатов

GET `/api/leaderboard/top?limit=10`

### Получение результатов игрока

GET `/api/leaderboard/player/{playerName}`

### Получение лучшего результата игрока

GET `/api/leaderboard/player/{playerName}/best`
