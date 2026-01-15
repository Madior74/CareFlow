# CareFlow API Documentation - Équipe Frontend

## Vue d'ensemble du projet

CareFlow est une API REST pour la gestion d'une clinique médicale développée avec Spring Boot. Elle permet de gérer les patients, médecins, rendez-vous, consultations et prescriptions médicales.

### Technologies utilisées
- **Backend**: Spring Boot 3.x
- **Base de données**: PostgreSQL
- **Authentification**: JWT (JSON Web Tokens)
- **Documentation**: OpenAPI/Swagger
- **Sécurité**: Spring Security avec rôles

---

## Configuration et démarrage

### Variables d'environnement
```bash
# Base de données
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/careflow
SPRING_DATASOURCE_USERNAME=careflow_user
SPRING_DATASOURCE_PASSWORD=careflow_pass

# JWT
JWT_SECRET=votre_secret_jwt_ici
JWT_EXPIRATION=3600000

# Serveur
SERVER_PORT=9000
```

### Démarrage de l'application
```bash
mvn spring-boot:run
```

### Accès à la documentation Swagger
Une fois l'application démarrée :
- **Swagger UI**: http://localhost:9000/swagger-ui.html
- **OpenAPI JSON**: http://localhost:9000/v3/api-docs

---

## Authentification

### Rôles utilisateur
- **ADMIN**: Accès complet à toutes les fonctionnalités
- **DOCTOR**: Gestion des patients, consultations, prescriptions
- **STAFF**: Gestion administrative (secrétaires)
- **SECRETARY**: Alias pour STAFF

### Endpoints d'authentification

#### Connexion
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@clinic.local",
  "password": "admin123"
}
```

**Réponse réussie (200):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "userId": 1,
  "email": "admin@clinic.local",
  "message": "Connexion réussie"
}
```

#### Rafraîchissement du token
```http
POST /api/auth/refresh
Authorization: Bearer {votre_token_jwt}
```

#### Déconnexion
```http
POST /api/auth/logout
Authorization: Bearer {votre_token_jwt}
```

### Utilisation du token JWT
Tous les endpoints (sauf `/api/auth/login`) nécessitent un header Authorization :
```
Authorization: Bearer {votre_token_jwt}
```

---

## Endpoints principaux

### 1. Gestion des patients (`/api/patient`)

#### Créer un patient
```http
POST /api/patient/create
Authorization: Bearer {token}
Content-Type: application/json

{
  "firstname": "Bertin",
  "lastname": "françois",
  "phone": "0612345678",
  "email": "bertin.françois@email.com",
  "dateOfBirth": "1990-05-15",
  "gender": "M",
  "address": "123 Rue de la Santé, Paris",
  "bloodType": "A+",
  "allergies": "Pollen, Arachides",
  "emergencyContactName": "Marie françois",
  "emergencyContactPhone": "0698765432"
}
```

#### Récupérer tous les patients
```http
GET /api/patient/all
Authorization: Bearer {token}
```

#### Récupérer un patient par ID
```http
GET /api/patient/{id}
Authorization: Bearer {token}
```

#### Modifier un patient
```http
PUT /api/patient/update/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "firstname": "Bertin",
  "lastname": "françois",
  "phone": "0612345678",
  "email": "bertin.françois@email.com",
  "address": "Nouvelle adresse"
}
```

#### Supprimer un patient (ADMIN seulement)
```http
DELETE /api/patient/delete/{id}
Authorization: Bearer {token}
```

### 2. Gestion des médecins (`/api/doctor`)

#### Créer un médecin
```http
POST /api/doctor/create
Authorization: Bearer {token}
Content-Type: application/json

{
  "firstname": "Dr. Daba",
  "lastname": "Ndiaye",
  "phone": "0611111111",
  "address": "456 Avenue des Médecins",
  "specialty": "Cardiologie",
  "dateOfBirth": "1980-03-20",
  "hireDate": "2020-01-15",
  "gender": "F",
  "email": "daba.ndiaye@clinic.local",
  "password": "password123"
}
```

#### Autres endpoints similaires aux patients
- `GET /api/doctor/all` - Liste des médecins
- `GET /api/doctor/{id}` - Détails d'un médecin
- `PUT /api/doctor/update/{id}` - Modifier un médecin
- `DELETE /api/doctor/delete/{id}` - Supprimer un médecin

### 3. Gestion des rendez-vous (`/api/appointment`)

#### Créer un rendez-vous
```http
POST /api/appointment/create
Authorization: Bearer {token}
Content-Type: application/json

{
  "doctorId": 1,
  "patientId": 2,
  "appointmentTime": "2024-01-20T10:00:00",
  "durationMinutes": 30,
  "type": "consultation",
  "reason": "Consultation annuelle"
}
```

**Types de rendez-vous:**
- `premiere` - Première consultation
- `suivi` - Consultation de suivi
- `urgence` - Urgence
- `consultation` - Consultation standard

**Statuts:**
- `SCHEDULED` - Programmée
- `COMPLETED` - Terminée
- `CANCELLED` - Annulée
- `NO_SHOW` - Patient absent

### 4. Gestion des disponibilités (`/api/availability`)

#### Ajouter une disponibilité
```http
POST /api/availability/create
Authorization: Bearer {token}
Content-Type: application/json

{
  "doctorId": 1,
  "startTime": "2024-01-20T09:00:00",
  "endTime": "2024-01-20T17:00:00"
}
```

### 5. Gestion des consultations (`/api/consultation`)

#### Créer une consultation
```http
POST /api/consultation/create
Authorization: Bearer {token}
Content-Type: application/json

{
  "appointmentId": 1,
  "symptoms": "Douleurs thoraciques, essoufflement",
  "observations": "Patient présente des signes de stress",
  "height": 175.5,
  "weight": 70.2,
  "temperature": 36.8,
  "bloodPressure": "120/80"
}
```

### 6. Gestion des diagnostics et prescriptions (`/api/diagnostic`, `/api/prescription`)

#### Créer un diagnostic
```http
POST /api/diagnostic/create
Authorization: Bearer {token}
Content-Type: application/json

{
  "consultationId": 1,
  "diagnosisCode": "I25.10",
  "diagnosisText": "Maladie cardiovasculaire athéroscléreuse",
  "notes": "Prévention nécessaire"
}
```

#### Créer une prescription
```http
POST /api/prescription/create
Authorization: Bearer {token}
Content-Type: application/json

{
  "diagnosticId": 1,
  "medicationId": 1,
  "dosage": "1 comprimé par jour",
  "durationDays": 30,
  "instructions": "Prendre le matin avec un verre d'eau"
}
```

---

## Modèles de données

### Patient
```json
{
  "id": 1,
  "firstname": "Bertin",
  "lastname": "François",
  "phone": "0612345678",
  "email": "bertin.françois@email.com",
  "dateOfBirth": "1990-05-15",
  "gender": "M",
  "address": "123 Rue de la Santé",
  "bloodType": "A+",
  "allergies": "Pollen",
  "emergencyContactName": "Marie françois",
  "emergencyContactPhone": "0698765432"
}
```

### Médecin
```json
{
  "id": 1,
  "firstname": "Dr. Daba",
  "lastname": "Ndiaye",
  "phone": "0611111111",
  "address": "456 Avenue des Médecins",
  "specialty": "Cardiologie",
  "dateOfBirth": "1980-03-20",
  "hireDate": "2020-01-15",
  "gender": "F"
}
```

### Rendez-vous
```json
{
  "id": 1,
  "doctorId": 1,
  "patientId": 2,
  "appointmentTime": "2024-01-20T10:00:00",
  "durationMinutes": 30,
  "type": "consultation",
  "status": "SCHEDULED",
  "reason": "Consultation annuelle"
}
```

---

## Schéma de base de données (aperçu)

### Tables principales
- **users** - Utilisateurs du système
- **roles** - Rôles (ADMIN, DOCTOR, STAFF)
- **user_roles** - Association utilisateurs-rôles
- **patient** - Informations patients
- **doctor** - Informations médecins
- **staff** - Personnel administratif
- **appointments** - Rendez-vous
- **consultations** - Consultations médicales
- **diagnostics** - Diagnostics posés
- **prescriptions** - Prescriptions médicales
- **medications** - Médicaments disponibles
- **medical_history** - Historique médical des patients

### Contraintes importantes
- Un médecin ne peut pas avoir deux rendez-vous simultanés
- Les rendez-vous ne peuvent pas être programmés dans le passé
- Un diagnostic doit être lié à une consultation
- Une prescription doit être liée à un diagnostic

---

## Gestion des erreurs

### Codes d'erreur courants
- **400 Bad Request**: Données invalides
- **401 Unauthorized**: Token manquant ou invalide
- **403 Forbidden**: Permissions insuffisantes
- **404 Not Found**: Ressource introuvable
- **409 Conflict**: Conflit (ex: rendez-vous chevauchant)
- **500 Internal Server Error**: Erreur serveur

### Structure des erreurs
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Le champ 'email' est obligatoire",
  "path": "/api/patient/create"
}
```

---

## Bonnes pratiques pour l'intégration frontend

### 1. Gestion des tokens
- Stocker le token JWT de manière sécurisée (localStorage/sessionStorage)
- Rafraîchir automatiquement le token avant expiration
- Gérer la déconnexion et la suppression du token

### 2. Gestion des erreurs
- Vérifier le code de statut HTTP
- Afficher les messages d'erreur utilisateur de manière claire
- Gérer les erreurs réseau (retry, offline mode)

### 3. Validation des données
- Valider les formulaires côté frontend avant envoi
- Respecter les contraintes de validation backend
- Gérer les formats de date et heure corrects

### 4. Performance
- Utiliser la pagination pour les listes importantes
- Mettre en cache les données statiques quand possible
- Optimiser les appels API (éviter les requêtes inutiles)

### 5. Sécurité
- Ne jamais exposer le token JWT dans les logs
- Utiliser HTTPS en production
- Valider toutes les données reçues du backend

---

## Support et contact

Pour toute question concernant l'API ou l'intégration :
- Consulter la documentation Swagger interactive
- Vérifier les logs de l'application pour les erreurs détaillées
- Contacter l'équipe backend pour clarifications


