def displayMessage(argument1, argument2, argument3):
    print(argument1+" "+argument2+" "+argument3) # Noncompliant {{Utiliser la simple côte (') au lieu du guillemet (")}}

# function call
displayMessage("Geeks", "4", "Geeks") # Noncompliant {{Utiliser la simple côte (') au lieu du guillemet (")}}





print("foo") # Noncompliant {{Utiliser la simple côte (') au lieu du guillemet (")}}



print('faa')




print(e)


print(6)


print('"Rien de grand ne s\'est accompli dans le monde sans passion." - Georg Wilhelm Friedrich Hegel')

myvalue = "foo" # Noncompliant {{Utiliser la simple côte (') au lieu du guillemet (")}}

myvalue = 'foo'