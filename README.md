# Recipes
Multi-user web service with Spring Boot that allows storing, retrieving, updating, and deleting recipes.

Spring Boot implementation of [Hyperskill's]( https://hyperskill.org/projects/180) project that includes JSON, REST API, Spring Boot Security, H2 database, LocalDateTime, Project Lombok concepts.
The program is a multi-user web service that allows storing, retrieving, updating, and deleting recipes.

## Requirements

- JDK 11
- Gradle 7.6
- Spring Boot 2.7.2

## Usage

1. Clone the repository
    ```shell
    git clone https://github.com/MichalLeh/Recipes.git
    ```

2. Setup the environment
    ```shell
    cd Recipes
    cp .env.example .env
    ```
   Edit the `.env` file to your needs. Since the project uses H2 as a database, you just need to
   the `H2_DATABASE_URL`, `H2_DATABASE_USERNAME` and `H2_DATABASE_PASSWORD` variables. The default values are:
    ```shell
    H2_DATABASE_URL=h2:file:../quizdb
    H2_DATABASE_USERNAME=sa
    H2_DATABASE_PASSWORD=
    ```

3. Build and run the project
    ```shell
    ./gradlew build
    ./gradlew bootRun
    ```

The endpoints can be accessed using a browser or a tool that allows you to send HTTP requests
like [Postman](https://www.getpostman.com/).

### Processes

- [Registration](#registration)
- [Post a new recipe](#post-a-new-recipe)
- [Update a recipe](#update-a-recipe)
- [Get a recipe by id](#get-a-recipe-by-id)
- [Get recipes by category](#get-recipes-by-category)
- [Delete a recipe by id](#Delete-a-recipe-by-id)

## API Endpoints

| Endpoint                               | Anonymous | User |
|----------------------------------------|-----------|------|
| POST /api/register                     | +         | +    |
| POST /api/recipe/new                   | -         | +    |
| PUT /api/recipe/{id}                   | -         | +    |
| GET /api/recipe/{id}                   | -         | +    |
| GET /api/recipe/search?category={name} | -         | +    |
| DELETE /api/recipe/{id}                | -         | +    |

_'+' means the registered user can access given endpoint. '-' means the unregistered user can't access given endpoint._

### Examples

#### Registration

**Example:** a `POST /api/register` request

*Request body:*

```
{
   "email": "Michal@some.com",
   "password": "Recipe123"
}
```

*Response:*
Status code: `200 (Ok) `

#### Post a new recipe

**Example:** a `POST /api/recipe/new` request without authentication

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
Status code: `401 (Unauthorized)`

Further `POST /api/recipe/new` request with basic authentication; email (login): Michal@some.com, and password: Recipe123

*Request body:*

```
{
   "name": "Mint Tea",
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

Further a  `POST /api/recipe/new` request with basic authentication; email (login): Michal@some.com, and password: Recipe123

*Request body:*

```
{
   "name": "Basic Cookies",
   "category": "dessert",
   "description": "Perfect partner to a cup of tea, ...",
   "ingredients": ["225g butter", "110g sugar", "275g plain flour", "1tsp cinnamon sugar", "75g chocolate chips",],
   "directions": ["Mix the butter,sugar, flour","Put it into heated oven", "Eat and enjoy"]
}
```

*Response:*

```
{
    "id": 3
}
```

#### Update a recipe

**Example:**  `PUT /api/recipe/1` request with basic authentication; email (login): Michal@some.com, and password: Recipe123

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
Status code: `204 (No Content) `

#### Get a recipe by id

**Example:**  `GET /api/recipe/1` request with basic authentication; email (login): Michal@some.com, and password: Recipe123

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
#### Get recipes by category

**Example:**  Response for the `GET /api/recipe/search/?category=dessert` request

*Response:*
```
{
   "name": "Chocolate Ice Cream",
   "category": "dessert",
   "description": "Tastes divine, easy to make at home ...",
   "ingredients": ["dark chocolate", "double cream", "vanilla extract", „milk chocolate chips“],
   "directions": ["Melt the chocolate", "Leave to cool it","Mix the cream, milk and vanilla", "Fold the melted chocolate and cocoa powder into the cream mix", "..."]
}
,
{
   "name": "Basic Cookies",
   "category": "dessert",
   "description": "Perfect partner to a cup of tea, ...",
   "ingredients": ["225g butter", "110g sugar", "275g plain flour", "1tsp cinnamon sugar", "75g chocolate chips",],
   "directions": ["Mix the butter,sugar, flour","Put it into heated oven", "Eat and enjoy"]
}
```
#### Delete a recipe by id

**Example:** `DELETE /api/recipe/1` request with basic authentication; email (login): Michal@some.com, and password: Recipe123

*Response:*
Status code: `204 (No Content) `
