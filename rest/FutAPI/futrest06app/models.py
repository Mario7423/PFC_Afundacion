from django.db import models


class User(models.Model):
    email = models.CharField(max_length=80, unique=True)
    password = models.CharField(max_length=70)
    nickname = models.CharField(max_length=30)
    token = models.CharField(blank=True, null= True, max_length=200)
