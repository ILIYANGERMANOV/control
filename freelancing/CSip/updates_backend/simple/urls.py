from django.conf.urls import patterns, include, url
from django.contrib import admin
from version_file.views import *
from django.contrib.staticfiles.urls import staticfiles_urlpatterns

urlpatterns = patterns('',
	url(r'^version', GetVersionView.as_view(), name='version'),
)
urlpatterns += staticfiles_urlpatterns()