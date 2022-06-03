from datetime import date

class Client():

    def __init__(self, age, weight):
        self.age = age
        self.weight = weight

    def get_age_in_five_years(self):
        a = Client()
        return a.age

    def get_age(self): # Noncompliant {{Avoid the use of getters and setters}}
        return self.age

    def set_age(self, age):
        self.age = age

    def is_major(self):
        return self.age >= 18

    def get_weight(self): # Noncompliant {{Avoid the use of getters and setters}}
        return self.weight