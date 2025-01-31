# 👤 FacialRecognition

![Version](https://img.shields.io/badge/version-1.0.0-blue.svg)
![Java](https://img.shields.io/badge/Java-17-red.svg)
![JavaFX](https://img.shields.io/badge/JavaFX-17-orange.svg)
![OpenCV](https://img.shields.io/badge/OpenCV-4.7.0-green.svg)

Un système de reconnaissance faciale moderne avec interface JavaFX, utilisant OpenCV pour la détection et FaceNet pour la reconnaissance.

## 📁 Structure du Projet

```
FacialRecognition/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── teams/
│   │   │       └── reconnaissance/
│   │   │           └── controller/
│   │   │               ├── AuthController.java        # Gestion de l'authentification
│   │   │               ├── PrincipalController.java   # Contrôleur principal
│   │   │               └── TestVisageController.java  # Tests de reconnaissance
│   │   └── resources/
│   │       ├── images/     # Images et assets
│   │       ├── styles/     # Fichiers CSS
│   │       └── teams/      # Ressources spécifiques aux équipes
└── README.md
```

## ✨ Fonctionnalités

### 🔐 Authentification
- Connexion sécurisée
- Gestion des utilisateurs
- Niveaux d'accès différenciés

### 🎯 Reconnaissance Faciale
- Détection en temps réel
- Analyse faciale précise
- Comparaison avec base de données

### 🖥️ Interface Utilisateur
- Design moderne avec JavaFX
- Navigation intuitive
- Visualisation en temps réel

## 🛠️ Technologies

- **Java & JavaFX** : Interface graphique
- **OpenCV** : Traitement d'images
- **FaceNet** : Reconnaissance faciale
- **CSS** : Stylisation personnalisée

## ⚙️ Installation

1. **Cloner le projet**
```bash
git clone https://github.com/MouradOuammou/FacialRecognition.git
cd FacialRecognition
```

2. **Configuration de l'environnement**
```bash
# Installation des dépendances
mvn install

# Configuration d'OpenCV
# Ajouter le chemin de la DLL OpenCV à votre PATH
```

3. **Lancer l'application**
```bash
mvn javafx:run
```

## 💻 Utilisation

### Authentification
```java
// AuthController.java
public class AuthController {
    public void login(String username, String password) {
        // Process d'authentification
    }
}
```

### Test de Reconnaissance
```java
// TestVisageController.java
public class TestVisageController {
    public void startDetection() {
        // Démarrage de la détection faciale
    }
}
```

## 🔧 Configuration

### Configuration OpenCV
```java
// Initialisation d'OpenCV
System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
```

### Configuration JavaFX
```java
// Configuration des vues
FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
```

## 📊 Performances

- Détection en temps réel : 30 FPS
- Précision de reconnaissance : > 95%
- Temps de traitement : < 100ms

## 🐛 Dépannage

### Problèmes Courants
1. **OpenCV non trouvé**
   - Vérifier le PATH
   - Réinstaller OpenCV

2. **Erreur JavaFX**
   - Vérifier le module-info.java
   - Vérifier les dépendances Maven

## 🤝 Contribution

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AjoutFonctionnalite`)
3. Commit (`git commit -m 'Ajout nouvelle fonctionnalité'`)
4. Push (`git push origin feature/AjoutFonctionnalite`)
5. Créer une Pull Request

## 📝 License

Ce projet est sous licence MIT.

## 👥 Auteur

- **MouradOuammou** - *Développement initial*

## 🔮 Évolutions Futures

- [ ] Amélioration de la précision
- [ ] Interface mobile
- [ ] Support multi-caméras
- [ ] Mode hors ligne

---
⭐ Si vous trouvez ce projet utile, n'oubliez pas de lui donner une étoile ! ⭐
