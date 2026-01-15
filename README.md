# CareFlow - Système de Gestion Clinique

[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-green)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![JWT](https://img.shields.io/badge/JWT-Authentication-red)](https://jwt.io/)

Un système complet de gestion de clinique médicale développé avec Spring Boot, offrant une API REST sécurisée pour la gestion des patients, médecins, rendez-vous et prescriptions médicales.

## 🏥 Vue d'ensemble

CareFlow est une application backend développée en Java avec Spring Boot qui fournit une API REST complète pour la gestion d'une clinique médicale. Le système inclut la gestion des utilisateurs avec différents rôles (administrateur, médecin, personnel), la prise de rendez-vous, les consultations médicales, les diagnostics et les prescriptions.

### Fonctionnalités principales

- 🔐 **Authentification JWT** avec gestion des rôles
- 👥 **Gestion des utilisateurs** (Admin, Médecins, Personnel)
- 🏥 **Gestion des patients** avec informations médicales complètes
- 📅 **Système de rendez-vous** avec gestion des conflits
- 🩺 **Consultations médicales** avec mesures vitales
- 💊 **Prescriptions et médicaments**
- 📊 **Diagnostics et historique médical**
- 📈 **Tableau de bord** avec statistiques

## 🛠️ Technologies utilisées

- **Backend**: Java 21, Spring Boot 3.2.0
- **Base de données**: PostgreSQL 15
- **ORM**: Hibernate/JPA
- **Migration**: Flyway
- **Sécurité**: Spring Security + JWT
- **Documentation**: OpenAPI/Swagger
- **Containerisation**: Docker & Docker Compose
- **Tests**: JUnit 5

## 🚀 Démarrage rapide

### Prérequis

- Docker & Docker Compose
- 4GB RAM minimum
- Ports 9000 et 5433 disponibles

### Installation et lancement

1. **Cloner le projet**
   ```bash
   git clone https://github.com/Madior74/CareFlow.git
   cd CareFlow
   ```

2. **Lancer avec Docker**
   ```bash
   docker-compose up -d
   ```

3. **Accéder à l'application**
   - **API**: http://localhost:9000
   - **Swagger UI**: http://localhost:9000/swagger-ui.html
   - **Documentation API**: http://localhost:9000/v3/api-docs

### Comptes de test

| Rôle | Email | Mot de passe | Permissions |
|------|-------|--------------|-------------|
| Admin | `admin@clinic.local` | `admin123` | Accès complet |
| Médecin | `doctor@clinic.local` | `doctor123` | Gestion patients |
| Staff | `staff@clinic.local` | `staff123` | Gestion administrative |

## 📖 Documentation

### Pour les développeurs backend

Consultez la [documentation technique complète](./API_DOCUMENTATION_TECHNIQUE.md) pour :
- Architecture du projet
- Endpoints API détaillés
- Modèles de données
- Gestion des erreurs
- Bonnes pratiques



## 🏗️ Architecture

```
careflow/
├── src/main/java/org/school/careflow/
│   ├── config/           # Configuration Spring
│   ├── controller/       # Contrôleurs REST
│   ├── dto/             # Objets de transfert
│   ├── exception/       # Gestion d'erreurs
│   ├── model/           # Entités JPA
│   ├── repository/      # Couches de données
│   ├── security/        # Configuration sécurité
│   └── service/         # Logique métier
├── src/main/resources/
│   ├── application.yml  # Configuration principale
│   ├── db/migration/    # Scripts Flyway
│   └── static/          # Ressources statiques
├── docker-compose.yml   # Configuration Docker
├── Dockerfile          # Image application
└── .env               # Variables d'environnement
```

## 🔧 Configuration

### Variables d'environnement (.env)

```env
# Serveur
SERVER_PORT=9000

# Base de données
SPRING_DATASOURCE_URL=jdbc:postgresql://careflow-db:5432/careflow
SPRING_DATASOURCE_USERNAME=careflow_user
SPRING_DATASOURCE_PASSWORD=careflow_pass

# JWT
JWT_SECRET=votre_secret_jwt_ici
JWT_EXPIRATION=3600000

# Logging
SPRING_JPA_SHOW_SQL=true
```

### Base de données

Le schéma de base de données est géré par Flyway avec les migrations dans `src/main/resources/db/migration/`.

**Tables principales :**
- `users` - Utilisateurs et authentification
- `patient` - Informations patients
- `doctor` - Profils médecins
- `appointments` - Rendez-vous
- `consultations` - Consultations médicales
- `prescriptions` - Prescriptions médicales

## 🔐 Sécurité

- **Authentification JWT** avec expiration configurable
- **Autorisation basée sur les rôles** (ADMIN, DOCTOR, STAFF)
- **Protection CSRF** désactivée pour l'API REST
- **Validation des données** avec Bean Validation
- **Logs d'activité** pour traçabilité

## 🧪 Tests

```bash
# Tests unitaires
mvn test

# Tests d'intégration
mvn verify

# Tests avec base de données
mvn test -Dspring.profiles.active=test
```

## 📊 Monitoring

L'application expose des métriques via Spring Boot Actuator :

- **Health checks**: `/actuator/health`
- **Métriques**: `/actuator/metrics`
- **Info**: `/actuator/info`

## 🚀 Déploiement

### Production

```bash
# Build de l'image
docker build -t careflow:latest .

# Déploiement
docker-compose -f docker-compose.prod.yml up -d
```

### Variables de production

```env
SPRING_PROFILES_ACTIVE=prod
SPRING_JPA_HIBERNATE_DDL_AUTO=validate
JWT_SECRET=secret-production-fort
```

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

### Standards de code

- Java 21 avec records et sealed classes
- Tests unitaires obligatoires
- Documentation des endpoints avec OpenAPI
- Validation des DTOs
- Gestion d'erreurs centralisée

## 📝 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 📞 Support

- **Documentation technique**: [API_DOCUMENTATION_TECHNIQUE.md](./API_DOCUMENTATION_TECHNIQUE.md)





---

**Développé avec ❤️ pour la gestion moderne des cliniques médicales**

*CareFlow - Simplifiant la gestion médicale depuis 2025*
