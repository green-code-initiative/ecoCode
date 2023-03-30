# Rules support matrix by techno

Each rule need to be develop for each technology to be available in SonarQube plugin.

Here is the list of rules already available in ecoCode project code.

- ✅ Rule included in current version of ecoCode
- 🚧 Rule to implement
- 🚫 Non applicable rule

| Rule key | Title  | Java | Php | JS | Python | Rust |
|--|--|--|--|--|--|--|
|  | Use official social media sharing buttons | 🚫 | 🚫 | 🚧 | 🚫 | 🚫 |
|  | Include a CSS file containing directives not used on a page | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 |
|  | Non-grouped similar CSS declarations | 🚫 | 🚫 | ✅ | 🚫 | 🚫 |
|  | CSS shorthand notations not used | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 |
|  | CSS print not included | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 |
|  | Non-standard fonts used | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 |
|  | Non-outsourced CSS and Javascript | 🚫 | 🚫 | 🚧 | 🚫 | 🚫 |
|  | Image tags containing an empty SRC attribute | 🚫 | 🚫 | 🚫 | 🚫 | 🚫 |
|  | Resize images outside the browser | 🚫 | 🚫 | ✅ | 🚫 | 🚫 |
|  | Use unoptimized vector images | 🚧 | 🚧 | 🚧 | 🚧 | 🚧 |
|  | Using too many CSS/javascript animations | 🚫 | 🚫 | ✅ | 🚫 | 🚫 |
|  | Modify the DOM when traversing it | 🚫 | 🚫 | ✅ | 🚫 | 🚫 |
|  | Edit DOM elements to make it invisible | 🚫 | 🚫 | 🚧 | 🚫 | 🚫 |
|  | Modify a CSS property directly | 🚫 | 🚫 | 🚧 | 🚫 | 🚫 |
| EC34 | Using try...catch...finally calls | 🚧 | ✅ | 🚧 | ✅ | 🚧 |
| EC22 | The use of methods for basic operations | 🚧 | ✅ | 🚧 | 🚧 | 🚧 |
| ??? | Call a DOM element multiple times without caching | 🚫 | 🚫 | ✅ | 🚫 | 🚫 |
| EC4 | Use global variables | ✅ | ✅ | 🚧 | ✅ | 🚧 |
|  | Using strings as arguments to SetTimeout() and setInterval() | 🚫 | 🚫 | ✅ | 🚫 | 🚫 |
| EC53 | Using arrays in foreach loops | ✅ | 🚧 | 🚧 | 🚧 | 🚧 |
| EC7 | Rewrite native getter/setters | 🚧 | 🚧 | 🚧 | ✅ | 🚧 |
| EC63 | Unnecessarily assigning values to variables | ✅ | 🚧 | 🚧 | 🚧 | 🚧 |
| EC66 | Use single quote (') instead of quotation mark (") | 🚧 | ✅ | 🚧 | 🚧 | 🚧 |
| EC67 | Use the $i++ variable during an iteration | ✅ | ✅ | 🚧 | 🚧 | 🚧 |
| EC69 | Calling a function in the declaration of a for loop | ✅ | ✅ | 🚧 | ✅ | 🚧 |
| EC72 | Perform an SQL query inside a loop | ✅ | ✅ | 🚧 | ✅ | 🚧 |
| EC74 | Write SELECT * FROM | ✅ | ✅ | 🚧 | ✅ | 🚧 |
| EC1 | Calling a Spring repository inside a loop | ✅ | 🚫 | 🚫 | 🚫 | 🚫 |
| EC3 | Getting the size of the collection in the loop | ✅ | 🚧 | 🚧 | 🚧 | 🚧 |
| EC2 | Multiple if-else statement | ✅ | 🚧 | 🚧 | 🚧 | 🚧 |
| EC76 | Usage of static collections | ✅ | 🚫 | 🚫 | 🚫 | 🚫 |
| EC77 | Usage Pattern.compile() in a non-static context | ✅ | 🚫 | 🚫 | 🚫 | 🚫 |
| EC75 | Concatenate Strings in loop | ✅ | 🚫 | 🚫 | 🚫 | 🚫 |
| EC78 | Const parameter in batch update | ✅ | 🚫 | 🚫 | 🚫 | 🚫 |
| EC79 | Free resources | ✅ | 🚫 | 🚫 | 🚫 | 🚫 |
| EC32 | Initialize builder/buffer with the appropriate size | ✅ | 🚫 | 🚫 | 🚫 | 🚫 |
| EC28 | Optimize read file exceptions | ✅ | 🚫 | 🚫 | 🚫 | 🚫 |
| EC5 | Usage of preparedStatement instead of Statement | ✅ | 🚫 | 🚫 | 🚫 | 🚫 |
| EC27 | Usage of system.arraycopy to copy arrays | ✅ | 🚫 | 🚫 | 🚫 | 🚫 |
