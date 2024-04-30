# Link Administration

## Popis
Popis a Cíl:

Zadání pro pohovor

Název projektu:

Administrace rozcestníku aplikačních linků

Popis a cíl:

Vytvořte systém pro administraci rozcestníku aplikačních linků (správu odkazů). Systém bude umožňovat zadávat odkaz, aktualizovat odkaz, mazat odkaz, zobrazovat jejich seznam a zobrazit náhled na seznam všech odkazů. Každý odkaz bude mít atributy jako je název, URL, obrázek, popis, dostupné v prohlížeči Firefox, dostupné v prohlížeči Chrome, příznak, zda je aktivní a zobrazit v novém okně. Každá aktualizace odkazu bude zaznamenána do historie. Seznam všech odkazů bude vystaven pomocí REST API.

Funkcionalita:

1) Seznam odkazů

a. Zobrazí se seznam odkazů. Seznam odkazů může být v tabulkové podobě.

b. Na stránce se seznamem odkazů bude tlačítko na přidání nového záznamu, aktualizaci stávajícího a smazání záznamu.

2) Formulář pro vytvoření nového odkazu nebo aktualizaci stávajícího

a. Formulář pro vytvoření nového odkazu nebo aktualizaci stávajícího bude vypadat shodně. Jediný rozdíl bude ten, že u aktualizace se zobrazí historie změn.

b. Formulář bude obsahovat tlačítko pro uložení záznamu, smazání záznamu a tlačítko zpět.

c. Odkaz bude obsahovat atributy název, URL, obrázek, popis, dostupné v prohlížeči Firefox, dostupné v prohlížeči Chrome, příznak, zda je aktivní a zobrazit v novém okně.

d. Pro zadání popisu bude ve formuláři WYSIWYG editor (vyberte libovolně).

3) Smazání záznamu

a. Na seznamu odkazů nebo v editaci bude možné smazat záznam.

b. Před smazáním záznamu zobrazovat dialogové okno s potvrzením Ano / Ne.

4) Náhled

a. Vytvořit náhled všech aplikačních linků, které nebudou v podobě tabulky, ale boxů obsahujících obrázek a k němu popis z WYSIWYG editoru.

b. Zobrazené linky bude limitovat zobrazení podle atributu dostupné v prohlížeči Firefox, dostupné v prohlížeči Chrome a zda je odkaz aktivní.

c. Po kliku na box se odkaz zobrazí buďto ve stejném okně nebo v novém podle atributu, zda se má zobrazovat novém okně nebo ne.

Technologické požadavky

- Použijte relační DB

- Použijte Spring framework jako základ backendu aplikace

- Zajistěte, aby API bylo dobře dokumentované (např. Swagger nebo OpenAPI)

- Jako build nástroj pro backend použijte Maven

- Pro realizaci frontendu využijte React

Doporučení

- Zdrojový kód by měl být dostupný na GitHubu nebo jiném veřejném repositáři.

- Měla by být sepsána dokumentace krok po kroku, jak si zprovoznit aplikaci na jiném PC

o Součástí postupu by měly být i inicializační skripty pro iniciální naplnění DB k otestování

- Ujistěte se, že aplikace lze snadno spustit a otestovat (například pomocí Maven build skriptů).

- Aplikace by měla být alespoň částečně pokryta unit testy

Očekávaný výsledek

- Funkční „administrační“ část systému na správu odkazů s možností otestování zobrazení pomocí náhledu na všechny odkazy podle podmínek.








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




