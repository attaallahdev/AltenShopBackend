
# Documentation de l'API Backend

Ce document décrit les endpoints de l'API RESTful pour la gestion des produits, l'authentification, le panier et la liste d'envies.

## 1. Authentification et Autorisation

### 1.1. Enregistrer un nouvel utilisateur

- **Endpoint**: `POST /account`
- **Description**: Permet de créer un nouveau compte utilisateur.
- **Requête**: 
```json
{
  "username": "john.doe",
  "firstname": "John",
  "email": "john.doe@example.com",
  "password": "password123"
}
```
- **Réponse**: Message de succès.

### 1.2. Se connecter et obtenir un token JWT

- **Endpoint**: `POST /token`
- **Description**: Permet de se connecter à l'application et d'obtenir un token JWT pour les requêtes authentifiées.
- **Requête**: 
```json
{
  "email": "john.doe@example.com",
  "password": "password123"
}
```
- **Réponse**: Token JWT en chaîne de caractères.

## 2. Gestion des Produits

Les endpoints de gestion des produits nécessitent une authentification. Les opérations de création, modification et suppression sont réservées à l'utilisateur `admin@admin.com`.

### 2.1. Récupérer tous les produits

- **Endpoint**: `GET /products`
- **Description**: Récupère la liste de tous les produits.
- **Authentification**: Requiert un token JWT valide.
- **Réponse**: Tableau d'objets `Product`.

### 2.2. Récupérer un produit par ID

- **Endpoint**: `GET /products/{id}`
- **Description**: Récupère les détails d'un produit spécifique par son ID.
- **Authentification**: Requiert un token JWT valide.
- **Réponse**: Objet `Product` ou 404 si non trouvé.

### 2.3. Créer un nouveau produit

- **Endpoint**: `POST /products`
- **Description**: Crée un nouveau produit.
- **Authentification**: Requiert un token JWT valide de l'utilisateur `admin@admin.com`.
- **Requête**: 
```json
{
  "code": "P001",
  "name": "Produit Test",
  "description": "Description du produit test",
  "image": "url_image",
  "category": "Catégorie",
  "price": 99.99,
  "quantity": 100,
  "internalReference": "REF001",
  "shellId": 1,
  "inventoryStatus": "INSTOCK",
  "rating": 5
}
```
- **Réponse**: L'objet `Product` créé.

### 2.4. Mettre à jour un produit

- **Endpoint**: `PATCH /products/{id}`
- **Description**: Met à jour les détails d'un produit existant.
- **Authentification**: Requiert un token JWT valide de l'utilisateur `admin@admin.com`.
- **Requête**: (Les champs à mettre à jour)
```json
{
  "name": "Nom du produit mis à jour",
  "price": 120.00
}
```
- **Réponse**: L'objet `Product` mis à jour ou 404 si non trouvé.

### 2.5. Supprimer un produit

- **Endpoint**: `DELETE /products/{id}`
- **Description**: Supprime un produit par son ID.
- **Authentification**: Requiert un token JWT valide de l'utilisateur `admin@admin.com`.
- **Réponse**: 204 No Content si succès, ou 404 si non trouvé.

## 3. Gestion du Panier

Les endpoints de gestion du panier nécessitent une authentification.

### 3.1. Récupérer les articles du panier de l'utilisateur

- **Endpoint**: `GET /cart`
- **Description**: Récupère tous les articles dans le panier de l'utilisateur authentifié.
- **Authentification**: Requiert un token JWT valide.
- **Réponse**: Tableau d'objets `CartItem`.

### 3.2. Ajouter un produit au panier

- **Endpoint**: `POST /cart`
- **Description**: Ajoute un produit au panier de l'utilisateur authentifié.
- **Authentification**: Requiert un token JWT valide.
- **Requête**: Paramètres de requête `productId` et `quantity`.
- **Réponse**: L'objet `CartItem` créé ou mis à jour.

### 3.3. Supprimer un article du panier

- **Endpoint**: `DELETE /cart/{cartItemId}`
- **Description**: Supprime un article spécifique du panier de l'utilisateur.
- **Authentification**: Requiert un token JWT valide.
- **Réponse**: 204 No Content si succès, ou 404 si non trouvé.

### 3.4. Mettre à jour la quantité d'un article dans le panier

- **Endpoint**: `PATCH /cart/{cartItemId}`
- **Description**: Met à jour la quantité d'un article spécifique dans le panier.
- **Authentification**: Requiert un token JWT valide.
- **Requête**: Paramètre de requête `quantity`.
- **Réponse**: L'objet `CartItem` mis à jour ou 404 si non trouvé.

## 4. Gestion de la Liste d'Envies

Les endpoints de gestion de la liste d'envies nécessitent une authentification.

### 4.1. Récupérer les articles de la liste d'envies de l'utilisateur

- **Endpoint**: `GET /wishlist`
- **Description**: Récupère tous les articles dans la liste d'envies de l'utilisateur authentifié.
- **Authentification**: Requiert un token JWT valide.
- **Réponse**: Tableau d'objets `WishlistItem`.

### 4.2. Ajouter un produit à la liste d'envies

- **Endpoint**: `POST /wishlist`
- **Description**: Ajoute un produit à la liste d'envies de l'utilisateur authentifié.
- **Authentification**: Requiert un token JWT valide.
- **Requête**: Paramètre de requête `productId`.
- **Réponse**: L'objet `WishlistItem` créé.

### 4.3. Supprimer un article de la liste d'envies

- **Endpoint**: `DELETE /wishlist/{wishlistItemId}`
- **Description**: Supprime un article spécifique de la liste d'envies de l'utilisateur.
- **Authentification**: Requiert un token JWT valide.
- **Réponse**: 204 No Content si succès, ou 404 si non trouvé.




## 5. Connexion avec le Frontend Angular

Pour connecter le backend Spring Boot au frontend Angular, plusieurs étapes sont nécessaires, principalement axées sur la configuration de CORS (Cross-Origin Resource Sharing) côté backend et la mise à jour des services Angular pour interagir avec les nouvelles API.

### 5.1. Configuration CORS dans Spring Boot

Étant donné que le frontend Angular et le backend Spring Boot s'exécuteront probablement sur des ports ou des domaines différents (par exemple, Angular sur `localhost:4200` et Spring Boot sur `localhost:8080`), il est impératif de configurer CORS dans le backend Spring Boot pour autoriser les requêtes provenant du frontend. Sans cette configuration, le navigateur bloquera les requêtes pour des raisons de sécurité.

Pour configurer CORS, vous pouvez ajouter une configuration globale dans votre application Spring Boot. Modifiez le fichier `SecurityConfig.java` ou créez une nouvelle classe de configuration CORS. Voici un exemple d'ajout de la configuration CORS dans `SecurityConfig.java`:

```java
package com.alten.productbackend.config;

import com.alten.productbackend.security.CustomUserDetailsService;
import com.alten.productbackend.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Add this line for CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/account", "/token").permitAll()
                        .requestMatchers("/products/**", "/cart/**", "/wishlist/**").authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Allow your Angular app origin
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
```

Dans cet exemple, nous avons ajouté un `CorsConfigurationSource` qui autorise les requêtes provenant de `http://localhost:4200` (le port par défaut d'Angular) et permet les méthodes HTTP courantes ainsi que les en-têtes `Authorization` et `Content-Type`.

### 5.2. Mise à jour des Services Angular

Le frontend Angular devra être mis à jour pour interagir avec les nouveaux endpoints du backend. Cela implique généralement les étapes suivantes :

1.  **Mettre à jour l'URL de base de l'API** : Dans l'environnement Angular (par exemple, `src/environments/environment.ts`), mettez à jour l'URL de base de l'API pour qu'elle pointe vers votre backend Spring Boot (par exemple, `http://localhost:8080`).

    ```typescript
    // src/environments/environment.ts
    export const environment = {
      production: false,
      apiUrl: 'http://localhost:8080'
    };

    // src/environments/environment.prod.ts
    export const environment = {
      production: true,
      apiUrl: 'http://your-production-backend-url.com'
    };
    ```

2.  **Créer ou mettre à jour les services Angular** : Les services Angular existants (par exemple, `ProductService`) devront être modifiés pour utiliser `HttpClient` afin d'envoyer des requêtes HTTP aux endpoints du backend. Vous devrez également créer de nouveaux services pour l'authentification, le panier et la liste d'envies.

    **Exemple de `ProductService` mis à jour (simplifié)**:

    ```typescript
    // src/app/products/services/product.service.ts
    import { Injectable } from '@angular/core';
    import { HttpClient, HttpHeaders } from '@angular/common/http';
    import { Observable } from 'rxjs';
    import { environment } from 'src/environments/environment';

    export interface Product {
      id: number;
      code: string;
      name: string;
      description: string;
      image: string;
      category: string;
      price: number;
      quantity: number;
      internalReference: string;
      shellId: number;
      inventoryStatus: "INSTOCK" | "LOWSTOCK" | "OUTOFSTOCK";
      rating: number;
      createdAt: number;
      updatedAt: number;
    }

    @Injectable({ providedIn: 'root' })
    export class ProductService {
      private apiUrl = environment.apiUrl + '/products';

      constructor(private http: HttpClient) { }

      getProducts(): Observable<Product[]> {
        // Ajoutez un token JWT si l'utilisateur est connecté
        const headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('jwt_token'));
        return this.http.get<Product[]>(this.apiUrl, { headers });
      }

      getProductById(id: number): Observable<Product> {
        const headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('jwt_token'));
        return this.http.get<Product>(`${this.apiUrl}/${id}`, { headers });
      }

      createProduct(product: Product): Observable<Product> {
        const headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('jwt_token'));
        return this.http.post<Product>(this.apiUrl, product, { headers });
      }

      updateProduct(id: number, product: Product): Observable<Product> {
        const headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('jwt_token'));
        return this.http.patch<Product>(`${this.apiUrl}/${id}`, product, { headers });
      }

      deleteProduct(id: number): Observable<void> {
        const headers = new HttpHeaders().set('Authorization', 'Bearer ' + localStorage.getItem('jwt_token'));
        return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
      }
    }
    ```

    **Exemple de `AuthService` (nouveau service)**:

    ```typescript
    // src/app/auth/services/auth.service.ts
    import { Injectable } from '@angular/core';
    import { HttpClient } from '@angular/common/http';
    import { Observable } from 'rxjs';
    import { environment } from 'src/environments/environment';

    @Injectable({ providedIn: 'root' })
    export class AuthService {
      private apiUrl = environment.apiUrl;

      constructor(private http: HttpClient) { }

      register(user: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/account`, user);
      }

      login(credentials: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/token`, credentials);
      }

      // Méthode pour stocker le token JWT (par exemple, dans le localStorage)
      setToken(token: string): void {
        localStorage.setItem('jwt_token', token);
      }

      getToken(): string | null {
        return localStorage.getItem('jwt_token');
      }

      logout(): void {
        localStorage.removeItem('jwt_token');
      }

      isLoggedIn(): boolean {
        return !!this.getToken();
      }
    }
    ```

3.  **Mettre à jour les composants Angular** : Les composants qui affichent ou manipulent les données devront utiliser ces services mis à jour. Par exemple, le `ProductListComponent` devra appeler `productService.getProducts()` pour récupérer les produits.

4.  **Implémenter l'intercepteur HTTP pour JWT** : Pour éviter de répéter l'ajout de l'en-tête `Authorization` à chaque requête, il est recommandé d'implémenter un intercepteur HTTP dans Angular. Cet intercepteur interceptera toutes les requêtes sortantes et ajoutera automatiquement le token JWT si disponible.

    ```typescript
    // src/app/core/interceptors/jwt.interceptor.ts
    import { Injectable } from '@angular/core';
    import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
    import { Observable } from 'rxjs';

    @Injectable()
    export class JwtInterceptor implements HttpInterceptor {
      intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = localStorage.getItem('jwt_token');

        if (token) {
          request = request.clone({
            setHeaders: {
              Authorization: `Bearer ${token}`
            }
          });
        }

        return next.handle(request);
      }
    }
    ```

    N'oubliez pas d'enregistrer cet intercepteur dans votre `app.module.ts` (ou `main.ts` si vous utilisez des modules autonomes) :

    ```typescript
    // src/app/app.module.ts (ou configuration équivalente pour les modules autonomes)
    import { HTTP_INTERCEPTORS } from '@angular/common/http';
    import { JwtInterceptor } from './core/interceptors/jwt.interceptor';

    // ...

    providers: [
      {
        provide: HTTP_INTERCEPTORS,
        useClass: JwtInterceptor,
        multi: true
      }
    ],
    // ...
    ```

En suivant ces étapes, le frontend Angular pourra communiquer efficacement avec le backend Spring Boot, en gérant l'authentification via JWT et en accédant aux différentes ressources (produits, panier, liste d'envies).

