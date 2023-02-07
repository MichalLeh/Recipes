# Recipes
Multi-user web service with Spring Boot that allows storing, retrieving, updating, and deleting recipes.

Spring Boot implementation of [Hyperskill's]( https://hyperskill.org/projects/180) project that includes JSON, REST API, Spring Boot Security, H2 database, LocalDateTime, Project Lombok concepts.
The program is a multi-user web service that allows storing, retrieving, updating, and deleting recipes.

**Example 1:** a `POST /api/recipe/new` request without authentication.

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

**Example 2:** a `POST /api/register` request.

*Request body:*

```
{
   "email": "Cook_Programmer@somewhere.com",
   "password": "RecipeInBinary"
}
```
Status code: `200 (Ok) `

Further `POST /api/recipe/new` request with basic authentication; email (login): Cook_Programmer@somewhere.com, and password: RecipeInBinary

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

Further  `PUT /api/recipe/1` *request with basic authentication; email (login): Cook_Programmer@somewhere.com, and password: RecipeInBinary*

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

Further  `GET /api/recipe/1` *request with basic authentication; email (login): Cook_Programmer@somewhere.com, and password: RecipeInBinary*

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

**Example 3:** a `POST /api/register` *request*

*Request body:*

```
{
   "email": "CamelCaseRecipe@somewhere.com",
   "password": "C00k1es."
}
```

Status code: `200 (Ok)`

Further  `GET /api/recipe/1` * request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.*

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

Further  `PUT /api/recipe/1` * request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.*

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

Further  `DELETE /api/recipe/1` * request with basic authentication; email (login): CamelCaseRecipe@somewhere.com, password: C00k1es.*

Status code: ` 403 (Forbidden)`

**Example 4:** a  `POST /api/recipe/new` *request with basic authentication; email (login): Cook_Programmer@somewhere.com, and password: RecipeInBinary*

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

Further  `POST /api/recipe/new` *request with basic authentication; email (login): Cook_Programmer@somewhere.com, and password: RecipeInBinary*

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

Further  `POST /api/recipe/new` *request with basic authentication; email (login): Cook_Programmer@somewhere.com, and password: RecipeInBinary*

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

**Example 5:**  *Response for the * `GET /api/recipe/search/?category=dessert` *request*

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

