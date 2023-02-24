<h1>Администратор турниров по настольному теннису</h1>

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

![Gallery](https://github.com/Shuffle-code/tt_nsk/blob/SergeiAidinov-readme/Project_promo.gif)
<br>
  
<dev>
  <h2><a href="http://tt-nsk.site/player/all" align = middle>
  <img src="https://github.com/Shuffle-code/tt_nsk/blob/SergeiAidinov-readme/web-logo-empty-background.png" align = middle> </a> Сайт проекта: http://tt-nsk.site</h2>
</dev>
     

<h2>Назначение проекта</h2>

Проект предназначен для администрирования соревнований по настольному теннису и предоставляет следующий функционал:

<body>
   <dev>
     <ul>
     <li>ведение картотеки игроков;</li>
     <li>ведение картотеки турниров;</li>
     <li>предоставление игрокам возможности регистрироваться на турниры;</li>
     <li>трансляция текущего счета в виде демонстрации таблицы с результатами сетов;</li>
     <li>сохранение результатов турнира в базе данных, с пересчетом рейтинга каждого игрока;</li>
     <li>для рефери: ведение счета турнира с автоматическим определением победителя в каждом сете и в игре;</li>   
    </ul>
   <hr>
     </dev>
  <dev>
     <h2>Сборка приложения:</h2>

<ul>
     <li>Файл для запуска приложения: tt_nsk.jar.</li>
     <li>Изменить значения в конфигурационном файле:
<p>spring_datasource:</p>
<p>&enspurl: </p>
<p>&enspusername: </p>
<p>&ensppassword: </p>
<p>storage:
<p>&ensplocation: </p>
  </li>
   <li>Запуск: java -jar tt_nsk.jar &</li>
   <li>База данных при её отсутствии, создается с помощью liquibase</li>
</ul>
     </dev>
     <dev>
     <h2>Технические требования:</h2>

<ul>
     <li>JRE версии не ниже 17;</li>
     <li>MySQL версии не ниже 8.0;</li>
</ul>
     </dev>
     

</body>
</html>





