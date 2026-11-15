# Projet : Gestion de Patrimoine Touristique (Microservices)

Application orientée services (SOA) développée dans le cadre du module **Développement d'Applications Orientées Services** (L3 2I - UASZ).

## Architecture

| Service | Port | Base | Rôle |
| :--- | :--- | :--- | :--- |
| **ms-patrimoine** | 8080 | H2 | Gestion des sites touristiques |
| **ms-circuit** | 8081 | H2 | Gestion des parcours de visite |
| **ms-evenement** | 8082 | H2 | Gestion des événements et disponibilités |
| **ms-reservation** | 8083 | H2 | Gestion des réservations |
| **frontend-patrimoine** | 4200 | - | Interface Angular |

## Prérequis
- Java 21
- Maven
- Node.js 20+
- Docker & Docker Compose

## Lancement en local (sans Docker)
```bash
cd ms-patrimoine && ./mvnw spring-boot:run
cd ms-circuit && ./mvnw spring-boot:run
cd ms-evenement && ./mvnw spring-boot:run
cd ms-reservation && ./mvnw spring-boot:run
cd frontend-patrimoine && npm install && ng serve