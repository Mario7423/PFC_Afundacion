from django.db import models


class User(models.Model):
    email = models.CharField(max_length=80, unique=True)
    password = models.CharField(max_length=70)
    nickname = models.CharField(max_length=30)
    token = models.CharField(blank=True, null= True, max_length=200)


class Player(models.Model):
    name = models.CharField(max_length=80)
    age = models.IntegerField()
    number = models.IntegerField()
    image = models.CharField(blank=True,null=True, max_length=200)
    team = models.CharField(max_length=50, default='Sin equipo')
    nationality = models.CharField(max_length=30)
    nickname = models.CharField(max_length=30, default='Sin apodo')
    position = models.CharField(max_length=20, default='Centrocampista')


class Hints(models.Model):
    hint1 = models.CharField(max_length=80)
    hint2 = models.CharField(max_length=80)
    hint3 = models.CharField(max_length=80)
    hint4 = models.CharField(max_length=80)
    hint5 = models.CharField(max_length=80)
    solution = models.CharField(max_length=80)

    def to_json(self):
        return {
            "hint1": self.hint1,
            "hint2": self.hint2,
            "hint3": self.hint3,
            "hint4": self.hint4,
            "hint5": self.hint5,
            "solution": self.solution
        }

