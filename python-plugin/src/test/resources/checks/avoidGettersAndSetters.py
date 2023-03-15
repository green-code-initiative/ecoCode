from datetime import date

class Client():

    def __init__(self, age, weight):
        self.age = age
        self.weight = weight

    def set_age(self, age): # Noncompliant {{Avoid creating getter and setter methods in classes}}
        self.age = age

    def set_age(self, age2): # Noncompliant {{Avoid creating getter and setter methods in classes}}
        self.age = age2

    def get_age_in_five_years(self):
        a = Client()
        return a.age

    def get_age(self): # Noncompliant {{Avoid creating getter and setter methods in classes}}
        return self.age

    def is_major(self):
        return self.age >= 18

    def get_weight(self): # Noncompliant {{Avoid creating getter and setter methods in classes}}
        return self.weight