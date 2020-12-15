# edjll-shop
<p>Курсовая работа по дисциплине "Технологии разработки систем электронной коммерции".</p>
<p>На тему "Разработка программного обеспечения интернет-магазина".</p>

<h4>Для запуска приложения необходимо:</h4>
<ul>
  <li>Скачать репозиторий.</li>
  <li>Подгрузить все зависимости.</li>
  <li>В файле application.properties:
    <ul>
      <li>Задать url подключения к СУБД MySQL в переменной spring.datasource.url.</li>
      <li>Задать имя входа для подключения к СУБД в spring.datasource.username.</li>
      <li>Задать пароль для входа в spring.datasource.password</li>
      <li>Прописать пути к папка с изображениями
        <ul>
          <li>manufacturer.upload.path</li>
          <li>promotion.upload.path</li>
          <li>productData.upload.path</li>
          <li>review.upload.path</li>
        </ul>
      </li>
    </ul>
  </li>
  <li>В файле "shop.sql" содержится дамп базы данных.</li>
  <li>Также для запуска приложения необходимо jdk версии 1.8.</li>
  <li>Приложение готово к запуску.</li>
</ul>

<h4>Данные учётных записей</h4>
<ul>
  <li>Админ:
    <ul>
      <li>Логин: adm.test.edjll@ya.ru</li>
      <li>Пароль: 123</li>
    </ul>
  </li>
  <li>Поддержка:
    <ul>
      <li>Логин: supp.test.edjll@ya.ru</li>
      <li>Пароль: 123</li>
    </ul>
  </li>
  <li>Пользователь:
    <ul>
      <li>Логин: aleksey.test.edjll@ya.ru</li>
      <li>Пароль: 123</li>
    </ul>
  </li>
</ul>
