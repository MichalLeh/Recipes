# Recipes
Multi-user web service with Spring Boot that allows storing, retrieving, updating, and deleting recipes.

Spring Boot implementation of [Hyperskill's]( https://hyperskill.org/projects/180) project that includes JSON, REST API, Spring Boot Security, H2 database, LocalDateTime, Project Lombok concepts.
The program is a multi-user web service that allows storing, retrieving, updating, and deleting recipes.


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
- [Get recipes](#get-recipes)

- [Delete a recipe](#delete-a-recipe)

## API Endpoints

| Endpoint                           | Anonymous | User |
|------------------------------------|-----------|------|
| POST /api/register                 | +         | +    |
| POST /api/recipe/new               | -         | +    |
| GET /api/recipe/{id}               | -         | +    |
| PUT /api/recipe/{id}               | -         | +    |
| DELETE /api/recipe/{id}            | -         | +    |
| GET /api/recipe/search?name={name} | -         | +    |

_'+' means the user with the role above can access that endpoint. '-' means the user with the role above does not have
access to that endpoint._

### Examples

#### Registration

**Example 1:** a `POST /api/register` request

*Request body:*

```
{
   "email": "Michal@some.com",
   "password": "Recipe123"
}
```
Status code: `200 (Ok) `

#### Post a new recipe

**Example 2:** a `POST /api/recipe/new` request without authentication

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
    "id": 3
}
```

#### Update a recipe

**Example 3:**  `PUT /api/recipe/1` request with basic authentication; email (login): Michal@some.com, and password: Recipe123

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

Status code: `204 (No Content) `

#### Get a recipe by id

**Example 4:**  `GET /api/recipe/1` request with basic authentication; email (login): Michal@some.com, and password: Recipe123

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

**Example 5:** a `POST /api/register` request

*Request body:*

```
{
   "email": "Programmer@some.com",
   "password": "C00k1es."
}
```

Status code: `200 (Ok)`

Further  `GET /api/recipe/1` request with basic authentication; email (login): Programmer@some.com, password: C00k1es

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

Further  `PUT /api/recipe/1` request with basic authentication; email (login): Programmer@some.com, password: C00k1es

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
Status code: ` 403 (Forbidden)`

Further  `DELETE /api/recipe/1` request with basic authentication; email (login): Programmer@some.com, password: C00k1es

Status code: ` 403 (Forbidden)`

**Example 4:** a  `POST /api/recipe/new` request with basic authentication; email (login): Michal@some.com, and password: Recipe123

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

Further  `POST /api/recipe/new` request with basic authentication; email (login): Michal@some.com, and password: Recipe123

*Request body:*

```
{
   "name": "Warming Ginger Tea",
   "category": "beverage",
   "description": "Ginger tea is a warming drink for cool weather, ...",
   "ingredients": ["1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"],
   "directions": ["Place all ingredients in a mug and fill with warm water (not too hot so you keep the beneficial honey compounds in tact)", "Steep for 5-10 minutes", "Drink and enjoy"]
}
```

*Response:*

```
{
    "id": 3
}
```

Further  `POST /api/recipe/new` request with basic authentication; email (login): Michal@some.com, and password: Recipe123

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
    "id": 4
}
```

**Example 5:**  Response for the `GET /api/recipe/search/?category=dessert` request

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

