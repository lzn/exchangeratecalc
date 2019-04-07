# exchangeratecalc
exchangeratecalc


# Budowanie aplikacji
gradlew.bat clean build

# Spring boot
Uruchomienie za pomocą komendy gradlew.bat bootRun

# Uruchomienie na serwerze tomcat 8.5
1) Kopiujemy plik build\libs\exchangerates-1.0.0.war do katalogu webapps tomcata
2) Uruchamiamy serwer za pomocą komendy .\startup.bat z katalogu bin tomcata


# Obliczenia
Wywołujemy url HTTP GET:
1) Dla tomcat: http://localhost:8080/exchangerates-1.0.0/calculate?value=30&src=PLN&dst=EUR
2) Dla spring boot: http://localhost:8080/calculate?value=30&src=PLN&dst=EUR

parametry:
* value - kwota
* src - źródłowa waluta
* dst - docelowa waluta

Zostanie zwrócony komunikat json z wynikiem lub komunikatem błędu:
{
"result":6.989585517578807576710701055427413,
"msg":"OK"
}
