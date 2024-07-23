При создании библиотеки использовались ряд допущений.

1. Все сенсоры работают с одной view. В случае необходимости работать со множественными view архитетуру надо несколько
   дополнить.
2. Других обработчиков событий, которые могут перехватить view не имеет. Так же, можно было бы отслеживать глобальные
   клики
   по экрану и сопоставлять их с областью нахождения кнопки, что сделает это независимым от view и его обработчиков.
3. Данная библиотека работает в жизненном цикле активити и в реальности, скорее всего, она будет работать
   на уровне приложения и отслеживать активити из верха стека.
4. Результат выдается индивидуально по каждому сенсору. Возможно в реальной задаче нужна будет упаковка данных в единый
   набор, тогда имплементацию выдачи результата из SensorWorker нужно будет сделать по запросу синхронно, а не как
   сейчас автоматически асинхронно.
5. Т.к. в библиотеке неясен метод выдачи результата, я сделал вывод в logcat с тэгом w201
6. Не уверен будет ли это решение работать с compose, скорее всего будет, но, наверное, не оптимально. Надо дополнительно
   исследовать.
7. Не стоит рассматривать данное тестовое задание, как готовую архитектуру для production ready модуля. 

