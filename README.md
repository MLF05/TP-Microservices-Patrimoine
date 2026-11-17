# Projet : Gestion de Patrimoine Touristique (Microservices & Spring Cloud)

Application orientée services (SOA) développée dans le cadre du module **Développement d'Applications Orientées Services** (L3 2I - UASZ).

## Architecture

L'application repose sur une architecture microservices complète orchestrée par Docker, avec un écosystème Spring Cloud (Config Server, Eureka, API Gateway) et un frontend Angular.

| Service | Port (externe) | Base | Rôle |
| :--- | :--- | :--- | :--- |
| **config-server** | 8888 | - | Serveur de configuration centralisé |
| **eureka-server** | 8761 | - | Annuaire de découverte des services |
| **api-gateway** | 9090 | - | Point d'entrée unique / Routage |
| **ms-patrimoine** | 8080 | H2 | Gestion des sites touristiques |
| **ms-circuit** | 8081 | H2 | Gestion des parcours de visite |
| **ms-evenement** | 8082 | H2 | Gestion des événements et disponibilités |
| **ms-reservation** | 8083 | H2 | Gestion des réservations (appelle ms-evenement) |
| **frontend** | 4200 | - | Interface Angular (servie par Nginx) |

### Flux de communication
1. Le **Frontend (Angular)** est servi par **Nginx**.
2. Le navigateur appelle **Nginx** sur le port `4200` (ce qui évite les problèmes de CORS).
3. Nginx redirige les appels `/api/*` vers l'**API Gateway** (port `9090`).
4. L'**API Gateway** interroge **Eureka** (port `8761`) pour trouver le microservice cible.
5. La Gateway redirige la requête vers le microservice approprié (`ms-patrimoine`, `ms-circuit`, `ms-evenement` ou `ms-reservation`).
6. Les configurations de chaque microservice sont centralisées dans le **Config Server** (port `8888`).

## Prérequis
- Java 21
- Maven
- Node.js 20+
- Docker & Docker Compose

## Lancement de l'application (avec Docker)

C'est la méthode recommandée pour lancer l'ensemble de l'architecture :

```bash
docker-compose up --build -d