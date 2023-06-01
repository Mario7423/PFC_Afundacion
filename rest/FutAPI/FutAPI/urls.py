"""FutAPI URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.1/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from futrest06app import endpoints

urlpatterns = [
    path('admin/', admin.site.urls),
    path('v1/health', endpoints.server_status),
    path('v1/register', endpoints.registeUser),
    path('v1/login', endpoints.loginUser),
    path('v1/add', endpoints.addPlayer),
    path('v1/addHint', endpoints.addHint),
    path('v1/getHints', endpoints.getHints),
    path('v1/getPlayers', endpoints.getPlayers),
    path('v1/addNew', endpoints.addNew),
    path('v1/getNews', endpoints.getNews),
    path('v1/addGame', endpoints.addGame),
    path('v1/getGames', endpoints.getGames),
]
