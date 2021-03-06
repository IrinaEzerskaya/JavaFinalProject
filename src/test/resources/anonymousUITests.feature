Feature: Тестирование интерфейса https://demoqa.com/
  согласно задаче:
  1. Login в системы под не валидными данными
  2. Возможность просмотра списка книг без входа в систему

  Background: UI пользователь с некорректными параметрами входа
    Given UI пользователь не авторизован

  Scenario: Неавторизованный UI пользователь имеет возможность просмотра списка книг без входа в систему
    Given Список книг доступен к просмотру по UI
    Then Список получен по UI
