from rest_framework import permissions, viewsets
from rest_framework import status, views
from rest_framework.response import Response
from rest_framework import permissions
import json

class GetVersionView(views.APIView):
	def get(self, request, format=None):
		return Response(1000, status=status.HTTP_200_OK)
