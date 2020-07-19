# JAX-RS REST API - SOA Assignment 1 Summer Term 2020

-To start the Project run the below command within the main folder of assignment
    `gradlew run`
-To access the UI Follow the following link         
`http://localhost:9999/v1/swagger-ui/index.html?url=openapi.yaml`
-The servier is available at the followign address
`localhost:9999/v1`


## Sbmitted Files
- Source Code
- openapi.yaml
- insomnia.json


Some basic inputs for some of the REST Services

- Creating a Bottle
`
{
  "id": 0,
  "name": "Coca Cola",
  "volume": 25,
  "isAlcoholic": false,
  "volumePercent": 0,
  "price": 2,
  "supplier": "Coca Cola",
  "inStock": 12
}
`

- Creating a Crate
`
{
  "bottle": {
    "id": 1,
    "href": "string"
  },
  "noOfBottles": 13,
  "price": 1,
  "inStock": 2,
  "href": "string"
}
`

- Creates an Order
`
{
  "orderId": 0,
  "bottles": [
    {
      "id": 2,
      "quantity": 1
    },
{
"id": 3,
      "quantity": 2
}
  ],
  "crates": [
    {
      "id": 2,
      "quantity": 1
    }
  ]
}
`
