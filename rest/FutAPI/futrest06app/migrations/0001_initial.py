# Generated by Django 4.1.2 on 2023-05-15 16:54

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('email', models.CharField(max_length=80, unique=True)),
                ('password', models.CharField(max_length=70)),
                ('nickname', models.CharField(max_length=30)),
            ],
        ),
    ]
