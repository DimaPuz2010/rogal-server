@echo off
REM Скрипт для запуска сервера таблицы лидеров

echo Запуск сервера таблицы лидеров для игры Rogal...

set IP=%1
set PORT=%2

if "%IP%"=="" (
  set IP=26.185.166.45
  echo IP-адрес не указан, используется адрес по умолчанию: %IP%
)

if "%PORT%"=="" (
  set PORT=443
  echo Порт не указан, используется порт по умолчанию: %PORT%
)

echo Запуск сервера на %IP%:%PORT%...
echo.

java -jar leaderboard-server-1.0.0.jar --server.address=%IP% --server.port=%PORT%

echo.
echo Сервер остановлен.
pause 