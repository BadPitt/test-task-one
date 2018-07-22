### Решение задачи 1
<b>Задача 1. Генерация тестовых данных</b> \
Напишите программу, которая создает текстовый файл, содержащий дату, время, номер точки продаж, номер операции и сумму операции.
Время должно выбираться случайно в диапазоне за предыдущий год. Т. е. если программа запущенна 02.06.2018, то время операции может быть от 01.01.2017 00:00 до 01.01.2018 00:00.
Номер точки продаж должен случайным образом выбирается из заранее подготовленного списка.
Список хранится в текстовом файле на одной строке одна точка продаж.
Сумма операции — случайное значение в диапазоне от 10 000,00 до 100 000,00 рублей.
Программе в качестве параметров передается имя файла со списком точек продаж, количество операций и файл, куда записать сгенерированные данные. \
Пример запуска программы: \
```java -jar build/libs/test-task-one-1.0.0.jar src/test/resources/sellPoints.txt 1000 src/test/resources/result.txt```

Подпроект test содержит бенчмарки для измерения времени, необходимого для генерации данных.

Ниже приведены результаты запуска на машине с характеристиками: \
JVM - version: JDK 1.8.0_172, Java HotSpot(TM) 64-Bit Server VM, 25.172-b11 \
OS  - Ubuntu 17.10 \
CPU - Intel® Core™ i3-7100 CPU @ 3.90GHz × 4 \
RAM - 15,6 GiB \
HDD: 

```$sudo hdparm -Tt /dev/sda  ``` \
/dev/sda: \
 Timing cached reads:   12866 MB in  2.00 seconds = 6443.46 MB/sec \
 Timing buffered disk reads: 182 MB in  3.02 seconds =  60.24 MB/sec \
```$dd bs=1M count=256 if=/dev/zero of=test conv=fdatasync ``` \
256+0 records in \
256+0 records out \
268435456 bytes (268 MB, 256 MiB) copied, 3,56009 s, 75,4 MB/s

т.е. скорость записи минуя кэши: ~75,4 MB/s

| Benchmark                          | Mode | Cnt | Score  | Error | Units |
| ---------------------------------- |:----:|:---:|:------:|:-----:| -----:|
| MyBenchmark.testGenerate_1000      | avgt |  25 | 0.006 ±|  0.001|  s/op |
| MyBenchmark.testGenerate_10_000    | avgt |  25 | 0.057 ±|  0.005|  s/op |
| MyBenchmark.testGenerate_100_000   | avgt |  25 | 0.566 ±|  0.037|  s/op |
| MyBenchmark.testGenerate_1_000_000 | avgt |  25 | 5.908 ±|  0.452|  s/op |

Зависимость между времененем работы и количеством элементов практически линейная.