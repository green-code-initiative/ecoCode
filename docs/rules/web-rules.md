## List of "Bad Green Practices" Web-oriented Rules

| Title  | Reference in "115" book | Category | Priority | Weighting | Level of complexity of realization | Example / Comment |
|--|--|--|--|--|--|--|
| Use official social media sharing buttons | 19 | Technical / Design | 2 | 2 | | |
| Include a CSS file containing directives not used on a page | 21 | Templating / CSS | 1 | 3 | | To reduce the number of HTTP requests |
| Non-grouped similar CSS declarations | 25 | Templating / CSS | 1 | 3 | | Do not write : `h1 { background-color: gray; color: navy; } h2 { background-color: gray; color: navy; } h3 { background-color: gray; color: navy; }` but rather : `h1, h2, h3 { background-color: gray; color: navy; }`|
| CSS shorthand notations not used | 26 | Templating / CSS | 1 | 3 | | Do not write : `margin-top:1em; margin-right:0; margin-bottom:2em; margin-left:0.5em;` but rather : `margin:1em 0 2em 0.5em;` |
| CSS print not included | 27 | Templating / CSS | 1 | 3 | | Reduction in the number of printed pages |
| Non-standard fonts used | 29 | Templating / Front | 1 | 3 | | |
| Non-outsourced CSS and Javascript | 32 | Templating / HTML | 1 | 3 | | CSS and JavaScript codes must not be embedded in the HTML code of the page |
| Image tags containing an empty SRC attribute | 33 | Templating / Images | 1 | 3 | | |
| Resize images outside the browser | 34 | Templating / Images | 1 | 3 | | |
| Use unoptimized vector images | 36 | Templating / Images | 1 | 3 | | Delete layer information, comments, etc. |
| Using too many CSS/javascript animations | 39 | Client code / CSS/Javascript| 1 | 3 | | **/!\ Need to define a threshold for the number of animations that is too high**|
| Modify the DOM when traversing it | 41 | Client code / DOM | 1 | 3 | | |
| Edit DOM elements to make it invisible | 42 | Client code / DOM | 2 | 2 | | |
| Modify a CSS property directly | 45 | Client code / Javascript | 1 | 3 | | Prioritize modification of CSS classes |
| Using try...catch...finally calls | 47 | Client code / Javascript | 3 | 1 | | Prioritize logical tests |
| The use of methods for basic operations | 48 | Client code / Javascript | 3 | 1 | | Prioritize primitive operations |
| Call a DOM element multiple times without caching | 49 | Client code / Javascript | 1 | 3 | | |
| Use global variables | 50 | Client code / Javascript | 3 | 1 | | |
| Using strings as arguments to SetTimeout() and setInterval() | 52 | Client code / Javascript | 1 | 3 | | |
| Using for...in loops | 53 | Client code / Javascript | 2 | 2 | | |
| Rewrite native getter/setters | 62 | Server code / Application server | 2 | 2 | | |
| Unnecessarily assigning values to variables | 63 | Server code / Application server | 3 | 1 | | |
| Use single quote (') instead of quotation mark (") | 66 | Server code / Application server | 3 | 2 | | |
| Use the $i++ variable during an iteration | 67 | Server code / Application server | 3 | 1 | | | Prioritize ++$i |
| Calling a function in the declaration of a for loop | 69 | Server code / Application server | 1 | 3 | | |
| Perform an SQL query inside a loop | 72 | Server code / Database | 1 | 3 | | |
| Write SELECT * FROM | 74 | Server code / Database | 1 | 3 | | |