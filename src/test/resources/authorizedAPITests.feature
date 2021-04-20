Feature: Тестирование API https://demoqa.com/
согласно задаче:
1. Login в систему под валидными данными
2. Добавление и удаление книги в/из коллекции

  Background: Пользователь запрашивает токен для авторизации
    Given Пользователь авторизовался

  Scenario: Авторизованный пользователь имеет возможность добавлять и удалять книги в коллекции
    Given Список книг доступен к просмотру для авторизованного пользователя
    When Добавляем книгу
    Then Книга добавлена
    When Удаляем книгу
    Then Книга удалена