from datetime import date

class Client():

    def __init__(self, age, weight):
        self.age = age
        self.weight = weight

    def set_age(self, age): # Noncompliant {{Avoid the use of getters and setters}}
        self.age = age

    def set_age(self, age2): # Noncompliant {{Avoid the use of getters and setters}}
        self.age = age2

    def get_age_in_five_years(self):
        a = Client()
        return a.age

    def get_age(self): # Noncompliant {{Avoid the use of getters and setters}}
        return self.age

    def is_major(self):
        return self.age >= 18

    def get_weight(self): # Noncompliant {{Avoid the use of getters and setters}}
        return self.weight