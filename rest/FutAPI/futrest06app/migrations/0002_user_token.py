# Generated by Django 4.1.2 on 2023-05-15 16:57

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('futrest06app', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='token',
            field=models.CharField(blank=True, max_length=200, null=True),
        ),
    ]
