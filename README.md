✅ <strong> Modules et Fonctionnalités.</strong>

👤 1. Gestion des Clients
Création de clients (nom, prénom, adresse)

Affichage d'une liste paginée et filtrable de tous les clients

Modification et suppression d’un client

Recherche par nom, prénom, ou adresse

Consultation de l’historique des commandes d’un client

Statistiques individuelles sur les commandes et paiements d’un client

📦 2. Gestion des Commandes
Création d’une commande associée à un client

Champs de la commande :

Désignation

Quantité

Prix unitaire

Statut de la commande : payé-non livré, livré-non payé, livré-payé

Date de commande

Date prévue de livraison (si applicable)

Date réelle de livraison (si livrée)

Affichage d’un tableau de toutes les commandes, paginé et filtrable

Actions disponibles :

Modifier une commande

Supprimer une commande

Voir le détail d'une commande

Générer une facture (si commande livrée)

🧾 3. Gestion des Factures
Génération d'une facture à partir d'une commande livrée

Affichage du tableau de toutes les factures générées

Affichage du nom complet du client lié à chaque facture

Possibilité de :

Prévisualiser une facture (en cours ou déjà fait)

Télécharger la facture en PDF (en option à ajouter)

Exporter la liste en CSV

Statistiques générales accessibles via un bouton dédié

📊 4. Statistiques commerciales
Statistiques générales :

Nombre total de clients

Nombre total de commandes

Répartition par statut de commande

Chiffre d'affaires global

Évolution des ventes dans le temps

Statistiques individuelles :

Historique des commandes par client

Total des commandes et montants par client

🔍 5. Fonctionnalités UX/UI importantes
Interface basée sur Angular + Bootstrap 5

Pagination, tri, et filtres dans les tableaux

Affichage dynamique du nom du client (à partir de son id)

Recherche multi-critères dans tous les modules

Affichage conditionnel (ex. : bouton "🧾 Générer facture" uniquement si la commande est livrée)

Navigation fluide entre les pages grâce au routing Angular

Séparation claire des composants (add, edit, list, info) pour chaque entité

🔗 6. Architecture technique
Frontend : Angular + Bootstrap 5

Backend : Spring Boot (avec architecture Service + Impl, DTO, ResponseEntity)

Base de données : relationnelle (ex. : MySQL ou PostgreSQL)

Modèles principaux :
<img width="1828" height="848" alt="Capture d’écran 2025-07-26 202029" src="https://github.com/user-attachments/assets/d7720bbe-1ee5-4b8a-8ef4-399dab6b75bb" />

Client

Commande

Facture

