# AwakeLabs Programming Challenge

This is a simple distributed implementation of the AwakeLabs Programming Challenge API. It has five initial services.

- `read-service: Responsible for any reading operation.`
- `write-service: Responsible for any write operation.`
- `service-registry: Responsible for discovering the services.`
- `service-gateway: Responsible for Routing & Load Balancing.`
- `backend-admin: Responsible for monitoring the health of the services.`


## Encryption of data at rest

For ensuring the encryption of data at rest I used `PostgreSQL`'s `pgcrypto`extension. While storing data instead of encrypting each column of the `PHI` instances I `concatinated` all the `PHI` values together and stored in one column using `symmetric key encryption`. For example:

```
   {
        "patientId": 3752809,
        "currentBpm": 74.0,
        "anxietyLevel": 0.0,
        "baselineProgress": 100.0,
        "time": 1619967887027,
        "batteryLevel": 0.87
    }
```  
is stored as:

```
   {
        "patientId": 3752809,
        "phi_values": "74.0#0.0#100.0"
        "time": 1619967887027,
        "batteryLevel": 0.87
    }
```
Storing together reduced the number of encryption required. 

## Encryption of data in-transit

To ensure the security of the data in-transit the connection between the `read-service` and `write-service` is `SSL`. Although I used a self-signed `ssl-certificate`, the principles should be applicable to real world. The `APIs` are also exposed via `SSL` connection. Now, as the connection between `application-layer` and `database-layer` is secured the `encryption-decryption` is performed in the database reducing redundancy.

## Running 

The whole application is `containerized` using `docker-compose`. Run the following command to initiate the project. It may take a few seconds to fully `start`.

```
$ git clone https://github.com/iamtanbirahmed/awakelabs-backend.git
$ cd awakelabs-backend
$ docker-compose up
```

Run `$ docker images`. It will show some-thing like this:

```
REPOSITORY                              TAG             IMAGE ID       CREATED          SIZE
awakelabs-api-backend_gateway_service   latest          48a57eb4fdb4   46 minutes ago   386MB
awakelabs-api-backend_read_service      latest          158b695bfcfc   47 minutes ago   404MB
awakelabs-api-backend_discovery         latest          922b2746c25c   47 minutes ago   381MB
awakelabs-api-backend_write_service     latest          e681b7bbe0e6   3 hours ago      402MB
awakelabs-api-backend_admin_service     latest          53dca5769368   11 hours ago     387MB
dpage/pgadmin4                          latest          df872ce2bc9e   10 days ago      244MB
postgres                                latest          293e4ed402ba   13 days ago      315MB
redislabs/redistimeseries               latest          9efeb7d109a0   2 weeks ago      143MB
openjdk                                 13-jdk-alpine   c4b0433a01ac   21 months ago    336MB
```
Send `GET` request to this to check if every thing is running well.

```
https://localhost:8443/api/readservice/v0.1/read/
```
Should have a response similar to this

```
Greeting from read-service:7003
```
## API

- Create Single Data Point:
```
$ curl --location --request POST 'https://localhost:8443/api/writeservice/v0.1/write/' \
--header 'Content-Type: application/json' \
--data-raw ' {
    "data": {
      "currentBpm": 74,
      "anxietyLevel": 0,
      "baselineProgress": 100,
      "heartRates": [
        {
          "time": 1619967827274,
          "rrInterval": 0.793,
          "heartRate": 91,
          "anxietyLevel": 0,
          "motion": false
        },
      ],
      "time": 1619967887027,
      "state": {
        "batteryLevel": 0.87
      }
    },
    "id": 3752809
  }'
```

- Load entire `data.json` file. This api may take a few seconds (approx. `30s`)
```
$ curl --location --request POST 'https://localhost:8443/api/writeservice/v0.1/write/json'
```
response:

```
$ Entire Json file was loaded successfully in the database
```

- Get `VitalSigns` for all the patients

```
$ curl --location --request GET 'https://localhost:8443/api/readservice/v0.1/read/' 
```
Example response:
```
[
    {
        "id": "64963b2a-0b82-44db-a592-12523a804e7a",
        "patientId": 3752809,
        "currentBpm": 74.0,
        "anxietyLevel": 0.0,
        "baselineProgress": 100.0,
        "time": 1619967887027,
        "batteryLevel": 0.87
    }
]

```
- Get `HeartRates` for all patients

```
$ curl --location --request GET 'https://localhost:8443/api/readservice/v0.1/heart-rates/read/'
```
Example response:

```
[
    {
        "id": "8f0464bc-735c-4e75-9baa-51f9ac8bd5a8",
        "time": 1619967833034,
        "rrInterval": 1.248,
        "heartRate": 92.0,
        "anxietyLevel": 0.0,
        "motion": false,
        "patientId": 3752809,
        "vitalSign": "64963b2a-0b82-44db-a592-12523a804e7a"
    },
    {
        "id": "b20a61cc-9d19-4720-be30-04c9eef9ac66",
        "time": 1619967829285,
        "rrInterval": 0.605,
        "heartRate": 90.0,
        "anxietyLevel": 0.0,
        "motion": false,
        "patientId": 3752809,
        "vitalSign": "64963b2a-0b82-44db-a592-12523a804e7a"
    },
    {
        "id": "17f3677f-52eb-47fb-baa9-cc5d44a52b9d",
        "time": 1619967855278,
        "rrInterval": 0.711,
        "heartRate": 76.0,
        "anxietyLevel": 0.0,
        "motion": false,
        "patientId": 3752809,
        "vitalSign": "64963b2a-0b82-44db-a592-12523a804e7a"
    },
    {
        "id": "8831a735-37e5-451d-8ac2-939ed990c027",
        "time": 1619967876037,
        "rrInterval": 0.503,
        "heartRate": 80.0,
        "anxietyLevel": 0.0,
        "motion": false,
        "patientId": 3752809,
        "vitalSign": "64963b2a-0b82-44db-a592-12523a804e7a"
    }
]
```

### Why `Java` ?

- My plan was to implement a demo for a `scalable` architecture which could be quickly developed. `spring-boot` has some robust `libraries` which allows fast `API` development. 
- The community is very strong. Most of the problems have detail discussion on how to solve.
- Personally, I was not familliar with `python` libraries for `discovery` or `gateway.` 
