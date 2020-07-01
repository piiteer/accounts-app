# Konta walutowe
Prosta aplikacja wystawiająca API do tworzenia kont walutowych i wymiany walut pomiędzy subkontami.
Obsługuje waluty PLN i USD. Kurs wymiany pobierany jest z serwisu api.nbp.pl

## Quick start
Żeby zbudować aplikację wykonaj polecenie:

    ./gradlew build

Żeby uruchomić aplikację wykonaj polecenie:

    ./gradlew bootRun

## API
### `POST /api/accounts` - tworzenie konta
    
#### Przykładowe ciało zapytania:

    {
        name: "Luke Skywalker",
        pesel: "85050766777",
        initialAmount: 2000
    }

#### Opis parametrów
* `name` - Imię i nazwisko
* `pesel` - Numer PESEL
* `initialAmount` - początkowy stan konta PLN.


### `GET /api/accounts/{pesel}` - pobieranie informacji o koncie
    
#### Opis parametrów zapytania
* `pesel` - Numer PESEL

#### Przykładowa odpowiedź:

    {
      "name": "Luke Skywalker",
      "pesel": "85050766777",
      "subaccounts": {
        "PLN": 1000.00,
        "USD": 0.00
      }
    }

#### Opis parametrów odpowiedzi
* `name` - Imię i nazwisko
* `pesel` - Numer PESEL
* `subaccounts` - stan subkont w różnych walutach

### `PUT /api/accounts/{pesel}/exchange` - wymiana walut
    
#### Przykładowe ciało zapytania:

    {
      "from": "USD",
      "to": "PLN",
      "amount": 1000
    }

#### Opis parametrów zapytania
* `from` - Kod waluty, która ma zostać sprzedana
* `to` - Kod waluty, która ma zostać kupiona
* `amount` - kwota, która ma zostać sprzedana.