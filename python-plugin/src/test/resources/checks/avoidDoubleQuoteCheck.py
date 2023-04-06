# in variables
firstname = "Andrea" # Noncompliant {{Avoid using quotation mark ("), prefer using simple quote (')}}
lastname = 'Doe'
cars = ["Renault", "Fiat", "Citroen"] # Noncompliant {{Avoid using quotation mark ("), prefer using simple quote (')}} {{Avoid using quotation mark ("), prefer using simple quote (')}} {{Avoid using quotation mark ("), prefer using simple quote (')}}
courses = ['mathematics', 'french', 'IT sciences']
instruments = ['guitar', "bass", 'drums'] # Noncompliant {{Avoid using quotation mark ("), prefer using simple quote (')}}

# in functions
def my_function(name, age):
    print(name + 'is' + age + ' yo.')

my_function("Robert", 12) # Noncompliant {{Avoid using quotation mark ("), prefer using simple quote (')}}
my_function('Robert', 12)
