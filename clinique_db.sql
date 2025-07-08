-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 06 juin 2025 à 21:25
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `clinique_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `patients`
--

CREATE TABLE `patients` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `date_naissance` date DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `patients`
--

INSERT INTO `patients` (`id`, `nom`, `prenom`, `date_naissance`, `telephone`, `email`) VALUES
(2, 'hamidi', 'salma', '2025-04-16', '0123456789', 'salma@gmail.com'),
(3, 'azirar', 'assia', '2025-04-24', '012345657679', 'assia@gmail.com'),
(4, 'guendouz', 'ayman', '2007-03-30', '1234567890', 'testa@gmail.com'),
(5, 'souhaila', 'omri', '2025-05-11', '1234567890', 'souhaila@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `rendezvous`
--

CREATE TABLE `rendezvous` (
  `id` int(11) NOT NULL,
  `patient_id` int(11) DEFAULT NULL,
  `date_rdv` date DEFAULT NULL,
  `heure_rdv` time DEFAULT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `rendezvous`
--

INSERT INTO `rendezvous` (`id`, `patient_id`, `date_rdv`, `heure_rdv`, `description`) VALUES
(4, 2, '2025-04-16', '14:00:00', 'zzz'),
(5, 3, '2025-04-25', '15:00:00', 'testt'),
(6, 4, '2025-04-26', '13:00:00', 'tetete'),
(7, 5, '2025-05-25', '15:00:00', 'test');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateurs`
--

INSERT INTO `utilisateurs` (`id`, `nom`, `email`, `mot_de_passe`) VALUES
(1, 'test', 'test@gmail.com', '8622f0f69c91819119a8acf60a248d7b36fdb7ccf857ba8f85cf7f2767ff8265'),
(2, 'ayman', 'ayman@gmail.com', '3dfd61feec80895f3192d238dec64e9cc33bd108d227a0444d9ec5181865d6e7'),
(3, 'toto', 'toto@gmail.com', '9a45271efef868a31ebbd528c407c678c33d8982871d92da3a766c1283c12f69'),
(4, 'salam', 'salam@gmail.com', 'cd61258d3d07562711953b8a948628128c5af18af078cfeb6adad9895cd40166'),
(5, 'ss', 'ss@gmail.com', 'e5a564247cb41d4b66ee87e72259c12e3112f3eec338934389b6ddd4fe2488f0'),
(6, 'az', 'az@gmail.com', 'dddf364690dc6cf89df70917e7b5df87936ab6ee942faa995227246e6c69390a'),
(7, 'assiaaz', 'assiaaz@gmail.com', 'd192b7c7b7f8380532c1e668675d4ee1df28fba121c32866091e949151b3258f'),
(8, 'souhaila', 'souhaila@gmail.com', '54706556ee279c51beaf6cb6ec40e7a1e5073e2c6fd9c727e88aebac79cb1b08'),
(9, 'azirar', 'azirar@gmail.com', 'c0d1ddc2d7d7d16bfca5aff8252b8ea8126bf7d5854df52f0df587d4c2cb4483'),
(10, 'azirar', 'azirar1@gmail.com', 'c0d1ddc2d7d7d16bfca5aff8252b8ea8126bf7d5854df52f0df587d4c2cb4483');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `rendezvous`
--
ALTER TABLE `rendezvous`
  ADD PRIMARY KEY (`id`),
  ADD KEY `patient_id` (`patient_id`);

--
-- Index pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `patients`
--
ALTER TABLE `patients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `rendezvous`
--
ALTER TABLE `rendezvous`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `rendezvous`
--
ALTER TABLE `rendezvous`
  ADD CONSTRAINT `rendezvous_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
