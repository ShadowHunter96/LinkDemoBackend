# Link Administration

## Popis
Popis a Cíl:
Cílem je vytvořit robustní systém, který bude spravovat aplikační linky. Uživatelé budou moci přidávat, aktualizovat, mazat a prohlížet linky. Každý link bude obsahovat informace jako název, URL, obrázek, popis a dostupnost pro různé prohlížeče. Systém také umožní zobrazit linky v novém okně a sledovat historii změn.

## Instalace
Po stažení z GitHub bude zapotřebí upravit konfiguraci pro připojení k databázi PostgreSQL. Nastavení se provádí v souboru `src/main/resources/application.properties`.  


### Krok za krokem:
1. Klonujte repozitář:
2. git clone https://github.com/vasGithubUcet/LinkAdministration.git

3. Otevřete soubor `src/main/resources/application.properties` a upravte následující položky podle vašeho prostředí:
   (ja osobne jsem nechal nazev na postgres a username taky postgres... používám DBeaver nebo PgAdmin anebo se developer muže přihlásit přes databázi napravo v ideii)

spring.datasource.url=jdbc:postgresql://localhost:5432/nazevDatabaze  
spring.datasource.username=uzivatelskeJmeno  
spring.datasource.password=heslo

## Build
Pro sestavení aplikace a spuštění testů použijte příkaz:
./mvnw clean install


## Spuštění
Projekt spustíte pomocí následujícího příkazu:
./mvnw spring-boot:run


## Testování unit testy
Pro spuštění unit testů využijte Maven:
./mvnw test

## Testování dat v aplikaci
Pro otestování dat je za potřebí si spustit konzoli v dbeaveru nebo v PgAdminu a spustit insertData ze souboru data.sql který je pod složkou resources

## Spuštění react aplikace v spring boot 
V controller třídě bude zapotřebí povolit crossOrigin @CrossOrigin("http://localhost:3000") v uvozovkách pak změnit parametr localhostu na kterém bude běžet react aplikace

## Swagger UI
Pro zobrazení Swagger UI:
http://localhost:8081/swagger-ui/index.html#/




