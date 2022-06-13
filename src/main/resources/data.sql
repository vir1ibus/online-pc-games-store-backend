INSERT INTO role (name) VALUES ('admin'), ('moderator'), ('user');

INSERT INTO genre (title) VALUES ('Экшен'), ('Ролевые'), ('Приключения'), ('Стратегии'), ('Симуляторы'), ('Онлайн'),
                                 ('Инди'), ('Спорт'), ('Казуальные'), ('Гонки');

INSERT INTO item_type (title) VALUES ('Игра'), ('DLC'), ('Набор');

INSERT INTO service_activation (title) VALUES ('Bethesda.net'), ('ElderScrollsOnline.com'), ('Nintendo eShop'), ('Origin'),
                                    ('Steam'), ('Ubisoft Connect');

INSERT INTO system_requirement (title) VALUES ('ОС'), ('Процессор'), ('Оперативная память'), ('Видеокарта'), ('DirectX'),
                                              ('Место на диске');

INSERT INTO region_activation (title) VALUES ('Весь мир'), ('Россия'), ('Россия и СНГ'),
                                             ('Россия и СНГ (кроме Азербайджана и Таджикистана)'),
                                             ('Россия и СНГ (кроме Белоруссии, Грузии и Туркменистана)'),
                                             ('Россия и СНГ (кроме Украины и Туркмении)'),
                                             ('Россия и Украина'), ('Россия, Украина и Казахстан'),
                                             ('Россия, Украина, Белоруссия, Казахстан, Узбекистан, Армения, Молдавия, Азербайджан');

INSERT INTO publisher (id, title, img) VALUES ('bandai-namco', 'Bandai Namco', 'VZJelQjDMicwbMr5DLXhnrGj9g9zWpnE.webp'),
                                              ('offworld-industries', 'Offworld Industries', null),
                                              ('io-interactive', 'Io-Interactive A/S', 'JOGEhmGEPd3q7TJGBs9ek0bXVOWWlf-I.webp'),
                                              ('quantic-dream', 'Quantic Dream', 'szLDcO2lvOSaUAUSO3FBcPuXaMGA34Lq.webp'),
                                              ('ubisoft', 'Ubisoft', 'gfWwlfb6NJyprA6NWQ5LFjjgDsReu6hl.webp');

INSERT INTO developer (id, title, img) VALUES ('fromsoftware', 'FromSoftware', 'ig08JkRjsDU_gR2AK6Zbx51B48U8EcA5.webp'),
                                              ('offworld-industries', 'Offworld Industries', null),
                                              ('io-interactive', 'Io-Interactive A/S', null),
                                              ('quantic-dream', 'Quantic Dream', null),
                                              ('ubisoft-montreal', 'Ubisoft Montreal', 'Y5WE404-2bk9n1YqgAZ4xv8GQLQEr5gg.webp');

INSERT INTO item (id, title, img, price, discount, date_realise, language_support, platform, developer_id, publisher_id, item_type_id, region_activation_id, service_activation_id) VALUES
('dark-souls-3', 'Dark Souls 3', 'DA0Sw1Ctg36KcQO8OAo0IsDwZmDgzQ6H.jpg', 1799, 28, '2016-04-12', 'Русский (интерфейс)', 'PC', 'fromsoftware', 'bandai-namco', 1, 3, 5),
('squad', 'Squad', 'h5fW18djjHwlLPzz0QXULYlrE_3_amiA.jpg', 1099, 9, '2015-12-15', 'Русский (интерфейс)', 'PC', 'offworld-industries', 'offworld-industries', 1, 2, 5),
('hitman-absolution', 'Hitman: Absolution', 'SAcsUR9E5fyXEgmYYD7GZBddp_RKrxZD.jpg', 399, 75, '2012-11-19', 'Полностью на руссом', 'PC и Mac', 'io-interactive', 'io-interactive', 1, 3, 5),
('detroit-become-human', 'Detroit: Become Human', '0Q6C2ZECyPVivaFmWNOryAks0jZ-ZSNj.jpg', 1628, 45, '2020-06-18', 'Полностью на русском', 'PC', 'quantic-dream', 'quantic-dream', 1, 4, 5),
('assassins-creed-odyssey', 'Assassin''s Creed Odyssey', 'Cnsl_LXTZO8eBh1Nl7pnqsN83yDjiAZo.jpg', 2499, 36, '2018-10-05', 'Полностью на русском', 'PC', 'ubisoft-montreal', 'ubisoft', 1, 3, 6);

INSERT INTO description (title, text, item_id) VALUES ('Не забывай сохраняться у костра', 'Dark Souls 3, заключительная часть трилогии от студии FromSoftware Inc., погрузит вас в мрачный и безжалостный мир. Призер «Лучшая RPG» на GamesCom 2015 и одна из самых ожидаемых игр 2016 года наконец-то вышла на PC, PlayStation 4 и Xbox One! Dark Souls 3 дает игрокам огромную свободу для изучения мира, графику нового поколения, напряженные схватки, традиционную атмосферу безнадежности и страданий, невиданных ранее безжалостных боссов, новые опасности и приключения. Если вы не боитесь трудностей, то эта игра именно для вас!

В Dark Souls 3 разработчики переосмыслили боевую систему, позаимствовав лучшие элементы из Bloodborne, расширили арсенал оружия, скиллы и улучшили систему изучения магии. Заключительная часть серии понравится как старожилам серии, так и неопытным новичкам — мы вам это гарантируем.

Игроку предстоит предостеречь королевство Лотрик от надвигающегося апокалипсиса, вызванного конфликтом сил Огня и Темноты. Победите обезумевших лордов Циндера и погасите костер Первого Пламени, чтобы спасти мир от зловещей угрозы. Но помните, что все ваши действия приведут к одной из четырех возможных концовок, поэтому будьте осторожны.', 'dark-souls-3'),
('Команда, команда и еще раз команда', 'Феноменальная реалистичность, насыщенный геймплей и первостепенное значение командной работы выделяют Squad из ряда других многопользовательских шутеров. Он представляет собой достоверную имитацию масштабных сражений.

Численность каждого отряда составляет до 50 единиц техники, а одновременно может играть до 100 человек. От разработки совместной тактики и слаженного взаимодействия зависит исход каждого сражения. Чтобы все игроки могли контролировать происходящее на поле боле, введена инновационная система обозрения и навигации.

Большой состав позволит разделять обязанности между участниками. Squad открывает доступ к технике разных типов: танки и вертолеты способны возглавить наступление, а БТРы обеспечивают транспортную и логистическую поддержку. Каждый из отрядов назначает командиров, которые руководят строительством оборонительных линий, занимаются планированием полетов и действий вспомогательных сил.

Шутер построен на базе мощного движка Unreal Engine 4 и получил высококачественную графику. Чувствительность оружия поддерживает реалистичность происходящего. Огромное количество локаций позволит вести сражения практически в любой части мира.', 'squad'),
('Тяжелый выбор', 'Последняя часть в серии Hitman стала эталонной. В Blood Money механика игры была доведена до абсолюта. Казалось, что выше прыгать некуда. Оказалось, что есть куда. Hitman: Absolution выводит серию на новый уровень.

Впервые за всю историю серии в игре появился внятный сюжет и драма. Главному герою, Агенту 47, необходимо сделать тяжелый моральный выбор, в ходе которого характер персонажа обретает ранее невиданную глубину. Игрок увидит перед собой не просто холодного профессионального убийцу, но человека.

Разработчики значительно доработали геймплейную механику. В игре появилась свобода выбора прохождения миссии. Вы можете преследовать свою жертву в скрытом режиме, а можете прорываться боем, как мясник. Во всех случаях искусственный интеллект реагирует адекватно происходящим событиям. Агенту 47 доступны абсолютны все образы, достаточно только раздобыть одежду. Но будьте осторожны – вас легко распознать!

Благодаря новому движку игра стала более кинематографичной. Графика радует глаз детализированной картинкой, анимация персонажей выглядит достойно. В Hitman: Absolution появился режим контрактов, где игрок может создать собственное задание с назначенной наградой и используемым оружием.

Hitman: Absolution отпускает грехи предыдущих частей и выводит серию на новый уровень. Если вы фанат похождений Агента 47, то вам точно здесь понравится. Новичку же знакомство подарит незабываемые впечатления от сверкающей лысины и станет прекрасным входным билетом в мир Hitman.', 'hitman-absolution'),
('Искусство быть человеком', 'Мечтают ли андроиды об электроовцах? А люди об андроидах? Или может быть андроиды мечтают только о том, чтобы быть свободными? В Детройте будущего все уже давно решено. Технологии скакнули настолько далеко вперед, что человечество полностью переложило рутинные операции на человекоподобных андроидов. Для некоторых они стали настоящими друзьями, которых у тех никогда не было. Для других же способом срывать свою злобу. Но однажды произошел сбой.

Detroit: Become Human - это приключенческая игра в жанре интерактивного кино от создателей Heavy Rain и Beyond: Two Souls, события которой разворачиваются в Детройте будущего. Вам предстоит примерить на себя роль трех абсолютно разных андроидов, каждый из которых со своим бэкграундом и стартовой позицией. Сыграйте за андроида-копа, андроида-сиделку и андроида-домработницу, которые постепенно становятся аутсайдерами.

В Detroit: Become Human вас ждет разветвленная система диалогов и множество видов концовки того или иного эпизода. В зависимости от ваших решений, действий и найденной информации, вы каждый раз будете видеть совершенно иной исход событий. ПК-версия Detroit: Become Human похвастается улучшенной графикой, разрешением до 4K и 60 кадрами в секунду, которые сделают ваш опыт еще более кинематографичным. Почувствуйте себя андроидом будущего в Detroit: Become Human!', 'detroit-become-human'),
('Assassin’s Creed уже не тот…', 'Взрослеете вы, геймеры, заставшие приключения Альтаира во времена Крестовых походов, взрослеет и сама серия. Теперь культовая франшиза взяла курс на один из сложнейших жанров в индустрии — RPG с открытым миром. Вас ждёт совершенно новый опыт и совершенно незнакомый Assassin’s Creed. Одних это оттолкнёт, но других убедит в том, что серия не стоит на месте и хочет меняться вместе с вашими предпочтениями.

В новой части вам предоставят на выбор одного из двух персонажей – наёмника Алексиоса или наёмницу Кассандру, являющимися потомками того самого царя Леонида, прославленного в истории о трёх сотнях спартанцев. Сюжет развернётся приблизительно за 400 лет до Assassin’s Creed Origins и предложит игроку выбрать сторону в жёстком противостоянии древнегреческих полисов. От ваших решений будет зависеть — одержат победу просвещённые афиняне или же беспощадные спартанцы.

По всем традициям современных ролевых игр в Odyssey вас ждёт огромный бесшовный мир, нелинейные сайд-квесты, несколько концовок и, разумеется, возможность завести любовные отношения с персонажами любого пола без какого-либо ограничения сексуальной ориентации. И, разумеется, вас ждёт больше подробностей про загадочную Первую Цивилизацию, вокруг артефактов которой построен сюжет абсолютно всех частей серии.', 'assassins-creed-odyssey');

INSERT INTO genre_has_item (genre_id, item_id) VALUES (1, 'dark-souls-3'), (2, 'dark-souls-3'), (1, 'squad'), (7, 'squad'), (1, 'hitman-absolution'), (3, 'hitman-absolution'), (1, 'detroit-become-human'), (3, 'detroit-become-human'), (1 , 'assassins-creed-odyssey'), (2 , 'assassins-creed-odyssey'), (3 , 'assassins-creed-odyssey');

INSERT INTO item_has_system_requirement (item_id, system_requirement_id, value) VALUES
                                               ('dark-souls-3', 1, 'Windows 7 SP1 / Windows 8.1 / Windows 10 (64-разрядная)'),
                                               ('dark-souls-3', 2, 'Intel Core i3-2100 / AMD FX-6300'),
                                               ('dark-souls-3', 3, '4 ГБ ОЗУ'),
                                               ('dark-souls-3', 4, 'NVIDIA GeForce GTX 750 Ti / ATI Radeon HD 7950'),
                                               ('dark-souls-3', 5 ,'Версии 11'),
                                               ('dark-souls-3', 6, '25 ГБ'),
                                               ('squad', 1, 'Windows 10 (64-разрядная)'),
                                               ('squad', 2, 'Четырехъядерный Intel Core i или AMD Ryzen'),
                                               ('squad', 4, 'Geforce GTX 770 или AMD Radeon HD 7970 с 4 ГБ видеопамяти'),
                                               ('squad', 3, '8 ГБ ОЗУ'),
                                               ('squad', 5, 'Версии 11'),
                                               ('squad', 6, '55 ГБ'),
                                               ('hitman-absolution', 1, 'Windows Vista, Windows 7'),
                                               ('hitman-absolution', 2, '2.4 ГГц, двухъядерный'),
                                               ('hitman-absolution', 3, '2 ГБ'),
                                               ('hitman-absolution', 4, 'с 512 МБ видеопамяти на базе NV8600, или аналогичная от AMD'),
                                               ('hitman-absolution', 6, '24 ГБ'),
                                               ('detroit-become-human', 1, 'Windows 10, 64-bit'),
                                               ('detroit-become-human', 2, 'Intel Core i5-2300 2.8 GHz or AMD Ryzen 3 1200 3.1GHz or AMD FX-8350 4.2GHz'),
                                               ('detroit-become-human', 3, '8 GB'),
                                               ('detroit-become-human', 4, 'Nvidia GeForce GTX 780 or AMD HD 7950 with 3GB VRAM minimum (Support of Vulkan 1.1 required)'),
                                               ('detroit-become-human', 6, '55 GB'),
                                               ('assassins-creed-odyssey', 1, 'Windows 7 SP1, Windows 8.1, Windows 10 (64-разрядная)'),
                                               ('assassins-creed-odyssey', 2, 'AMD FX 6300 @ 3.8 ГГц, Ryzen 3 - 1200, Intel Core i5 2400 @ 3.1 ГГц'),
                                               ('assassins-creed-odyssey', 3, '8 ГБ ОЗУ'),
                                               ('assassins-creed-odyssey', 4, 'AMD Radeon R9 285, NVIDIA GeForce GTX 660 с 2 ГБ видеопамяти и поддержкой Shader Model 5.0'),
                                               ('assassins-creed-odyssey', 5, 'Версии 11'),
                                               ('assassins-creed-odyssey', 6, 'не менее 46 ГБ');


INSERT INTO screenshot (path, item_id) VALUES ('6rX3DR0wLv90-n5oXjVrk7Yv2LnWalQ9.jpg', 'dark-souls-3'),
                                              ('CZy00_DR6VCTn9NAQK0gMLapsairq2hf.jpg', 'dark-souls-3'),
                                              ('d0aGQZWNFZQJpGQ6xrGEtGOt-R5fIY08.jpg', 'dark-souls-3'),
                                              ('j_CBL25SGF6xe2D8TJw4f57m4umoC298.jpg', 'dark-souls-3'),
                                              ('l7Arlo8DzRkrO1cluy3irEUnNNqaN1SZ.jpg', 'dark-souls-3'),
                                              ('UKt_1jE4y8Lv8BnOqGh7rD-DZHGD1Yga.jpg', 'dark-souls-3'),
                                              ('ZUeP-XMU-gcjKBVBNKfQqXGOk9xMRqlL.jpg', 'squad'),
                                              ('ToUcTGWSc7mDSidVD2yBfFcTrWQhW8PO.jpg', 'squad'),
                                              ('CTP9LUzpdj8pmwR9Tv8DFEIdGu-M2Elu.jpg', 'squad'),
                                              ('A7GNrFFwGsiLa3WNw5hm57_AuEPa1IRR.jpg', 'squad'),
                                              ('qyyt-zOPfOHWkUvLv_KnbWnKIDKOmFrv.jpg', 'squad'),
                                              ('jtVNY01u4dNm8SllBMNgCY-7KLXzjI-h.jpg', 'squad'),
                                              ('1MjCtL_ONFnBq0H9hz1dXgVY3myS4-J3.jpg', 'squad'),
                                              ('bfujCoqOUHSgCHH98s9RqTbuBzQnEBLp.jpg', 'hitman-absolution'),
                                              ('MbfnAWQfeseeDHJ6ziqfvJ_IRUQUnk_Q.jpg', 'hitman-absolution'),
                                              ('jAl42-jc1n2TkhznXgKKWqPDcv90mUnv.jpg', 'hitman-absolution'),
                                              ('3-poyg8AgX6UgdQILdf8H4nIaxdvcx1t.jpg', 'hitman-absolution'),
                                              ('6zncF9NHXM93WZ7ZjV-Ibb7LRo7KGHFU.jpg', 'hitman-absolution'),
                                              ('GvofNa5T240P8OkslPQBTgcnYe7uamg3.jpg', 'hitman-absolution'),
                                              ('YEqdqU87Filfx_HVR_zc5Hsv_Ax26fG_.jpg', 'hitman-absolution'),
                                              ('Im-NnuPSRggSzLflcAkEuvWrEae5j7gR.jpg', 'hitman-absolution'),
                                              ('HzwQNmsvF3TNABgQw1X6uOUQ_s-hz2m1.jpeg', 'detroit-become-human'),
                                              ('8buF1XPuMppkqJ2cwPq92vxH6MxzGjMv.jpeg', 'detroit-become-human'),
                                              ('njHDHmdPfuGT1KhLAOOAS5UgTD0uvEdU.jpeg', 'detroit-become-human'),
                                              ('j5RQw_Zn1fSpUB26smOFfwlHw1ZN0H72.jpeg', 'detroit-become-human'),
                                              ('himgQpeMmoQzcsGjCP7Icbx3nu61d1MT.jpeg', 'detroit-become-human'),
                                              ('14pq_Vqc7P4x-yhVE_R8KmONc0DJFrn2.jpeg', 'detroit-become-human'),
                                              ('oKUGpqSFU5IahDg_Rl97Bir0EM7b0J54.jpg', 'assassins-creed-odyssey'),
                                              ('3pJCw3JVmtBxbNHRXvqfT2fbjXBh6S4z.jpg', 'assassins-creed-odyssey'),
                                              ('_UVAw-CPC5gWRKU74b-0QFA72UOq-Cen.jpg', 'assassins-creed-odyssey'),
                                              ('hVVfoehyv1dNbVAJahtqp7PcS3pAjRuT.jpg', 'assassins-creed-odyssey'),
                                              ('uZV8b35t3E6aRWPSGBqNNoHYcGbTk9lq.jpg', 'assassins-creed-odyssey');

INSERT INTO trailer (path, item_id) VALUES ('https://youtu.be/0J4u1FD87FM', 'dark-souls-3'),
                                           ('https://youtu.be/_zDZYrIUgKE', 'dark-souls-3'),
                                           ('https://youtu.be/IctFOaEsRE8', 'squad'),
                                           ('https://youtu.be/EcJhijTI1Ps', 'hitman-absolution'),
                                           ('https://youtu.be/Yb_Gpqoc1wQ', 'hitman-absolution'),
                                           ('https://youtu.be/aJ10wF7zvqA', 'detroit-become-human'),
                                           ('https://youtu.be/zwX5V6atTCE', 'assassins-creed-odyssey'),
                                           ('https://youtu.be/B8hOC9_bhPU', 'assassins-creed-odyssey');

INSERT INTO activate_key (value, item_id) VALUES ('yL4kejHw5Dn0QU0gdduo9IyDJk4UwWmRnhy', 'dark-souls-3'),
                                                 ('EFpcHuDTjJ8-5hRLW9aHYVDlgHz8YIXCHXN', 'squad'),
                                                 ('kI7YT4Akx-UPa7i6soDmZKpA2zTM49gtofc', 'hitman-absolution'),
                                                 ('aQRwYYgSuxbBToxzOJ9JUXoSvPpFKOr0ZtQ', 'detroit-become-human'),
                                                 ('dBm2KQGY7nPdGuufOpPlM6wlQy-17mDMSAb', 'assassins-creed-odyssey'),
                                                 ('uH2E4yBNl5qloimrotkyGDs9sUB2zIwhgku', 'dark-souls-3'),
                                                 ('2z5AyEa1-IP2q53XyRrfVHQ5qfoPCAeUsCV', 'squad'),
                                                 ('wadVbSS-djF51HbHRkZ0YdbzwJT5Bk3GIQM', 'hitman-absolution'),
                                                 ('bH0-5FrA4SmDLs5fnUSViVNHcvGm3QnerPQ', 'detroit-become-human'),
                                                 ('FINLYnJuxTUcXGrgR92iZGfB5XjUdR5QMla', 'assassins-creed-odyssey'),
                                                 ('Xazwk6ZS5K1gL9QFzticuVeUJUsVkR6G012', 'dark-souls-3'),
                                                 ('cLdh3b9rP5ioAeibgU9wqOLgVlFDfNwYXwt', 'squad'),
                                                 ('9uy8Ea1udDPrYoQFbqfymdHKStJqqQDcEot', 'hitman-absolution'),
                                                 ('UXpR-p2aPTntXnoI44K0mQzENrTSwVbue4D', 'detroit-become-human'),
                                                 ('T5oi0yvqsqsktDTWmYfWhsivdAnAHDUPmib', 'assassins-creed-odyssey'),
                                                 ('prS5H5fUStKthPgkGaobusbHkcJVWbTANaC', 'dark-souls-3'),
                                                 ('UwwNSq6ySUEInF7HmwjQgC4IVEw8ulcgP9g', 'squad'),
                                                 ('e3Ji5PwjxIWEDGO2lNhNZHF0Y0MqT9VtgWu', 'hitman-absolution'),
                                                 ('-6tCoADCkGD1PBVvejLV1rrFzenmgg47ngI', 'detroit-become-human'),
                                                 ('B2K4LOOtLfcY2X7F5G3zVOm73k1SnQtcvIp', 'assassins-creed-odyssey'),
                                                 ('mINCv8GFErBiQYqP9eTiA9ADeTrDMtXhym-', 'dark-souls-3'),
                                                 ('Qxmp8GWMEG4rW5bGMMzLuDUwZ9VlVhHGrgm', 'squad'),
                                                 ('xL9WBqdlCda1uD142WO39CzcE0xHpdZpAXE', 'hitman-absolution'),
                                                 ('CWTzKQUfvIp122DSUuCdpTFh3OpXz3HDA4q', 'detroit-become-human'),
                                                 ('KR10gGdOw59OmJud5gEGXNR3IiVLIH3qPJO', 'assassins-creed-odyssey'),
                                                 ('nCqNIJWTrBSd2h12Xg3kOh1F7Uur-1CBVs-', 'dark-souls-3'),
                                                 ('AWeAok9cZ02BbGth9lpVcF04JaAJxYWV6Ry', 'squad'),
                                                 ('eqD6jCrbrbIgk9-URA89kqubswvKPmCXV6r', 'hitman-absolution'),
                                                 ('UsRzfPNs45WFErIm2-1CSrF-c95Puwuu33B', 'detroit-become-human'),
                                                 ('J5gdToSBauQ25TOCBQRdeIbrK99pDgycZAn', 'assassins-creed-odyssey');