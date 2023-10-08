# Recipes
Multi-user web service with Spring Boot that allows storing, retrieving, updating, and deleting recipes.

Spring Boot implementation of [Hyperskill's]( https://hyperskill.org/projects/180) project that includes JSON, REST API, Spring Boot Security, H2 database, LocalDateTime, Project Lombok concepts.
The program is a multi-user web service that allows storing, retrieving, updating, and deleting recipes.

## Requirements

- JDK 11
- Gradle 7.3.3
- Spring Boot 2.7.2

## Usage

1. Clone the repository
    ```shell
    git clone https://github.com/MichalLeh/Recipes.git
    ```
2. Build and run the project
    ```shell
      cd Recipes
      gradlew build
    ```

The endpoints can be accessed using a browser or a tool that allows you to send HTTP requests
like [Postman](https://www.getpostman.com/).

### Processes

- [Registration](#registration)
- [Post a new recipe](#post-a-new-recipe)
- [Update a recipe](#update-a-recipe)
- [Get a recipe by id](#get-a-recipe-by-id)
- [Get recipes by category or name](#get-recipes-by-category-or-name)
- [Delete a recipe by id](#Delete-a-recipe-by-id)

## API Endpoints

| Endpoint                               | Anonymous | User |
|----------------------------------------|-----------|------|
| POST /api/register                     | +         | +    |
| POST /api/recipe/new                   | -         | +    |
| PUT /api/recipe/{id}                   | -         | +    |
| GET /api/recipe/{id}                   | -         | +    |
| GET /api/recipe/search/?category={category} | -         | +    |
| GET /api/recipe/search/?name={name}    | -         | +    |
| DELETE /api/recipe/{id}                | -         | +    |

_'+' means the registered user can access given endpoint. '-' means the unregistered user can't access given endpoint._

### Examples

#### Registration
- Endpoint receives a JSON object with two fields: `email` (string), and `password` (string). Both fields are required and must be valid: `email` should contain `@` and `.` symbols, `password` should contain at least 8 characters and shouldn't be blank;
- Only an author of a recipe can delete or update a recipe. If a user is not the author of a recipe, but they try to carry out the actions mentioned above, the service should respond with the `403 (Forbidden)` status code;
- If a user with a specified email does not exist, the program saves (registers) the user in a database and responds with `200 (Ok)`;
- If a user is already in the database, respond with the `400 (Bad Request)` status code;
- If the fields do not meet these restrictions or user is already in the database, respond with the `400 (Bad Request)` status code.

`POST /api/register` request

*Request body:*

```
{
   "email": "Michal@some.com",
   "password": "Recipe123"
}
```

*Response:*
Status code: `200 (Ok)`

#### Post a new recipe

`POST /api/recipe/new` request with basic authentication; email (login): Michal@some.com, and password: Recipe123
- Endpoint receives a recipe as a JSON object and returns a JSON object with one id field. This is a uniquely generated number by which we can identify and retrieve a recipe later. The status code should be `200 (Ok)`;
- The service should accept only valid recipes – all fields are obligatory, `name`, `description`, `category`, `ingredients` and `directions` shouldn't be blank, and JSON arrays should contain at least one item;
- If a recipe doesn't meet these requirements, the service should respond with the `400 (Bad Request)` status code.

*Request body:*

```
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

*Response:*

```
{
    "id": 1
}
```
Further a  `POST /api/recipe/new` request with basic authentication; email (login): Michal@some.com, and password: Recipe123

*Request body:*

```
{
   "name": "Chocolate Ice Cream",
   "category": "dessert",
   "description": "Tastes divine, easy to make at home ...",
   "ingredients": ["dark chocolate", "double cream", "vanilla extract", „milk chocolate chips“],
   "directions": ["Melt the chocolate", "Leave to cool it","Mix the cream, milk and vanilla", "Fold the melted chocolate and cocoa powder into the cream mix", "..."]
}
```

*Response:*

```
{
    "id": 2
}
```

`POST /api/recipe/new` request without basic authentication
  
*Response:*

Status code: `401 (Unauthorized)`

#### Update a recipe

`PUT /api/recipe/1` request with basic authentication; email (login): Michal@some.com, and password: Recipe123
- Endpoint receives a recipe as a JSON object and updates a recipe with a specified `id`. Also, update the `date` field too. The server should return the `204 (No Content)` status code;
- If a recipe with a specified `id` does not exist, the server should return `404 (Not found)`;
- The server should respond with `400 (Bad Request)` if a recipe doesn't follow the restrictions indicated above (all fields are required, string fields can't be blank, arrays should have at least one item).
  
*Request body:*

```
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```
*Response:*
Status code: `204 (No Content)`

#### Get a recipe by id

`GET /api/recipe/1` request with basic authentication; email (login): Michal@some.com, and password: Recipe123
- Returns a recipe with a specified id as a JSON object (where `{id}` is the id of a recipe). The server should respond with the `200 (Ok)` status code;
- If a recipe with a specified id does not exist, the server should respond with `404 (Not found)`.

*Response:*
```
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "date": "2020-01-02T12:11:25.034734",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```
#### Get recipes by category or name

`GET /api/recipe/search` request
- Endpoint takes one of the two mutually exclusive query parameters:
    - if `category` parameter is specified, it returns a JSON array of all recipes of the specified category. Search is case-insensitive, sorts the recipes by date (newer first);
    - if `name` parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter. Search is case-insensitive, sorts the recipes by date (newer first); 
- If no recipes are found, the program should return an empty JSON array;
- If 0 parameters were passed, or more than 1, the server should return `400 (Bad Request)`. The same response should follow if the specified parameters are not valid;
- If everything is correct, it should return `200 (Ok)`.
  
`GET /api/recipe/search/?category=dessert` request

*Response:*
```
[
   {
      "name": "Vegan Chocolate Ice Cream",
      "category": "dessert",
      "date": "2021-04-06T14:10:54.009345",
      ....
   },
   {
      "name": "vegan avocado ice cream",
      "category": "DESSERT",
      "date": "2020-01-06T13:10:53.011342",
      ....
   },
]
```
`GET /api/recipe/search/?name=tea` request

*Response:*
```
[
   {
      "name": "Fresh Mint Tea",
      "category": "beverage",
      "date": "2021-09-06T14:11:51.006787",
      ....
   },
   {
      "name": "warming ginger tea",
      "category": "beverage",
      "date": "2020-08-06T14:11:42.456321",
      ....
   },
   {
      "name": "Iced Tea Without Sugar",
      "category": "beverage",
      "date": "2019-07-06T17:12:32.546987",
      ....
   },
]
```

#### Delete a recipe by id

`DELETE /api/recipe/1` request with basic authentication; email (login): Michal@some.com, and password: Recipe123
- Endpoint deletes a recipe with a specified `{id}`. The server should respond with the `204 (No Content)` status code;
- If a recipe with a specified id does not exist, the server should return `404 (Not found)`;
