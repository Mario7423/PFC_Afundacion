# Generated by Django 4.1.2 on 2023-05-24 07:40

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('futrest06app', '0002_user_token'),
    ]

    operations = [
        migrations.CreateModel(
            name='Player',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=80)),
                ('age', models.IntegerField()),
                ('number', models.IntegerField()),
                ('image', models.CharField(blank=True, max_length=200, null=True)),
                ('team', models.CharField(max_length=50, null=True)),
                ('nationality', models.CharField(max_length=30)),
            ],
        ),
    ]
