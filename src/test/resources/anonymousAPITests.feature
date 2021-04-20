Feature: Тестирование API https://demoqa.com/
  согласно задаче:
  1. Login в системы под не валидными данными
  2. Возможность просмотра списка книг без входа в систему

  Background: Пользователь с некорректными параметрами входа
    Given Пользователь не авторизован

  Scenario: Неавторизованный пользователь имеет возможность просмотра списка книг без входа в систему
    Given Список книг доступен к просмотру
    When Просматриваем список
    Then Список получен