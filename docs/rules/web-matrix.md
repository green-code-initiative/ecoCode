## Rules support matrix by techno

Each rule need to be develop for each technology to be available in SonarQube plugin.

Here is the list of rules already available in ecoCode project code.

- ✅ Rule included in current version of ecoCode
- 🚫 Non applicable rule

| Title  | Java | Php | Javascript | Python | Rust | ... |
|--|--|--|--|--|--|--|
| Use official social media sharing buttons | 🚫 | 🚫 |  | 🚫 | 🚫 | |
| Include a CSS file containing directives not used on a page | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 | |
| Non-grouped similar CSS declarations | 🚫 | 🚫 | ✅ | 🚫 | 🚫 | |
| CSS shorthand notations not used | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 | |
| CSS print not included | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 | |
| Non-standard fonts used | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 | |
| Non-outsourced CSS and Javascript | 🚫 | 🚫 |  | 🚫 | 🚫 | |
| Image tags containing an empty SRC attribute | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 | |
| Resize images outside the browser | | ✅  | | ✅  | | |
| Use unoptimized vector images |  |  |  |  |  | |
| Using too many CSS/javascript animations | 🚫 | 🚫 | ✅ | 🚫 | 🚫 | |
| Modify the DOM when traversing it | 🚫 | 🚫 | ✅ | 🚫 | 🚫 | |
| Edit DOM elements to make it invisible | 🚫 | 🚫 |  | 🚫 | 🚫 | |
| Modify a CSS property directly | 🚫 | 🚫 |  | 🚫 | 🚫 | |
| Using try...catch...finally calls |  | ✅ |  | ✅ | | |
| The use of methods for basic operations |  |  |  |  | | |
| Call a DOM element multiple times without caching | 🚫 | 🚫 | ✅ | 🚫 | 🚫 | |
| Use global variables | ✅ |  |  |  |  |  | | |
| Using strings as arguments to SetTimeout() and setInterval() | 🚫 | 🚫 | ✅ | 🚫 | 🚫 | |
| Using for...in loops | ✅  | ✅ |  | ✅ | | |
| Rewrite native getter/setters |  |  |  | ✅ | | |
| Unnecessarily assigning values to variables | ✅  |  |  |  | | |
| Use single quote (') instead of quotation mark (") | | ✅  | | | | |
| Use the $i++ variable during an iteration | ✅  | ✅  |  |  | | |
| Calling a function in the declaration of a for loop | ✅  | ✅  |  | ✅  | | |
| Perform an SQL query inside a loop | ✅  | ✅  |  | ✅ | | |
| Write SELECT * FROM | ✅  | ✅  |  | ✅  | | |
| Calling a Spring repository inside a loop | ✅ | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 |
