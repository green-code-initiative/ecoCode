class AvoidMultipleIfElseStatement:
    def method_with_multiple_if_elif():
        nb1 = 0
        nb2 = 10

        if nb1 == 1: # Noncompliant {{Use a match-case statement instead of multiple if-else if possible}}
            nb1 = 1
        elif nb1 == nb2:
           print(nb1)
        elif nb2 == nb1:
            print(nb2)
        else:
            print()
        nb1 = nb2

    def method_with_multiple_if_else():
        nb1 = 0
        nb2 = 10

        if nb1 == 1:
            nb1 = 1
        else:
            print(nb1)
        if nb1 == 1:# Noncompliant {{Use a match-case statement instead of multiple if-else if possible}}
            nb1 = 1
        else:
            print(nb2)

    def method_with_one_if_else_if():
        nb1 = 0
        nb2 = 10

        if nb1 == 1:
            nb1 = 1
        elif nb1 == nb2:
            print(nb2)
        else:
            print(nb1)
        nb1 = nb2


    def method_with_one_if_else():
        nb1 = 0
        nb2 = 10

        if nb1 == 1:
            nb1 = 1
        else:
            print(nb2)

