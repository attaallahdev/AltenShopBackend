
Documentation de l'API Backend
Ce document décrit les endpoints de l'API RESTful pour la gestion des produits, l'authentification, le panier et la liste d'envies.

1. Authentification et Autorisation
1.1. Enregistrer un nouvel utilisateur
Endpoint: POST /account
Description: Permet de créer un nouveau compte utilisateur.
Requête:
====================================================
 ##           INFORMATIONS POUR LE DÉVELOPPEMENT                 =
 ===================================================================

 ---------------------------------
 Documentation de l'API (Swagger/OpenAPI)
L'interface Swagger UI est disponible à l'URL suivante une fois l'application démarrée :
 http://localhost:8080/swagger-ui/index.html

 La spécification OpenAPI (JSON) est disponible ici :
 http://localhost:8080/v3/api-docs



 Authentification et Génération de Token

Pour obtenir un token JWT, envoyez une requête POST à /token avec les identifiants suivants.
L'utilisateur "admin" est créé au démarrage si nécessaire (voir DataLoader).

 URL pour obtenir le token : POST http://localhost:8080/token

 Corps de la requête (Request Body) :
 {
  "email": "admin@admin.com",
  "password": "alten1234@@@@"
 }

 Une fois le token obtenu, ajoutez-le à vos requêtes pour les endpoints sécurisés
 dans le header "Authorization" comme suit :
 Authorization: Bearer <votre_token_jwt>
            CONFIGURATION DE LA SÉCURITÉ (JWT)                 =


jwt.secret=ThisIsAVeryLongAndSecureSecretKeyForYourJWTAuthenticationTokenGeneration
jwt.expiration=36000000
             CONFIGURATION DE LA BASE DE DONNÉES              
spring.datasource.url=jdbc:postgresql://localhost:5432/productdb
spring.datasource.username=postgres
spring.datasource.password=morel123


