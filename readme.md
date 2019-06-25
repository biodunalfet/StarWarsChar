# StarWarsChars
A simple app that shows an implementation of [Uncle Bob's](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) Clean Architecture on Android. It interacts with the _tricky_ [Star Wars API](https://swapi.co/)

Technologies and Libraries
-----------------
- Kotlin
- MVP
- Clean Architecture
- RxJava2
- Retrofit
- Gson
- Architecture Components (Room, LiveData)
- Mockito, Espresso, JUnit4 tests

# App Flow
1. On app start, the user lands on the character search screen.
2. The user can search for characters from the Star Wars universe. The result of the search should display a character list. The info that should be displayed on each of the result is defined below
3. When tapping on a character, details are displayed as defined below

```
[Character search]
name
birth_year
```

```
[Character details]
name
birth_year
height (cm/feet/inches)
name (species)
language (species)
homeworld (species)
population (planets)
films (the movies the character appears in: title, release date, opening crawl)
```

## License

    Copyright 2019, Hamza Fetuga

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.