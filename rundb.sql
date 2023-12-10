-- phpMyAdmin SQL Dump
-- version 5.1.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 09, 2023 at 01:18 PM
-- Server version: 5.7.24
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rundb`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrateur`
--

CREATE TABLE `administrateur` (
  `ID_ADMINISTRATEUR` int(11) NOT NULL,
  `nom` varchar(200) NOT NULL,
  `prenom` varchar(200) NOT NULL,
  `motDePasse` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `administrateur`
--

INSERT INTO `administrateur` (`ID_ADMINISTRATEUR`, `nom`, `prenom`, `motDePasse`, `email`) VALUES
(1, 'ADMIN', 'ADMIN', 'root', 'runadmin@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `ID_Client` int(11) NOT NULL,
  `nom` varchar(200) NOT NULL,
  `prenom` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `motDePasse` varchar(200) NOT NULL,
  `soldeFidelite` int(11) NOT NULL DEFAULT '0',
  `verifSolde` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`ID_Client`, `nom`, `prenom`, `email`, `motDePasse`, `soldeFidelite`, `verifSolde`) VALUES
(1, 'Tadjer', 'Yenni', 'yenni.tadjer@gmail.com', 'run123', 0, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

CREATE TABLE `commande` (
  `ID_Commande` int(11) NOT NULL,
  `ID_Client` int(11) DEFAULT NULL,
  `date_commande` datetime NOT NULL,
  `statut` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `commande`
--

INSERT INTO `commande` (`ID_Commande`, `ID_Client`, `date_commande`, `statut`) VALUES
(1, 1, '2023-12-09 13:30:59', 'en attente'),
(2, 1, '2023-12-09 13:33:55', 'en attente');

-- --------------------------------------------------------

--
-- Table structure for table `details_commande`
--

CREATE TABLE `details_commande` (
  `ID_Detail` int(11) NOT NULL,
  `ID_Commande` int(11) DEFAULT NULL,
  `ID_Produit` int(11) DEFAULT NULL,
  `ID_Variante` int(11) NOT NULL,
  `quantite` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `details_commande`
--

INSERT INTO `details_commande` (`ID_Detail`, `ID_Commande`, `ID_Produit`, `ID_Variante`, `quantite`) VALUES
(1, 2, 3, 12, 1),
(2, 2, 3, 15, 1);

-- --------------------------------------------------------

--
-- Table structure for table `moderateur`
--

CREATE TABLE `moderateur` (
  `ID_Moderateur` int(11) NOT NULL,
  `nom` varchar(200) NOT NULL,
  `prenom` varchar(200) NOT NULL,
  `email` varchar(200) NOT NULL,
  `motDePasse` varchar(200) NOT NULL,
  `ajouterProduit` tinyint(1) DEFAULT NULL,
  `supprimerProduit` tinyint(1) DEFAULT NULL,
  `modifierProduit` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `moderateur`
--

INSERT INTO `moderateur` (`ID_Moderateur`, `nom`, `prenom`, `email`, `motDePasse`, `ajouterProduit`, `supprimerProduit`, `modifierProduit`) VALUES
(7, 'Oumlil', 'Ilyass', 'ilyass@gmail.com', 'ilyass', 1, 0, 0),
(8, 'azfazfazfazzaerraz', 'azfazfazf', 'ilkalzedaz@hotmail.fr', 'fazfazfazfaz', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

CREATE TABLE `produit` (
  `ID_Produit` int(11) NOT NULL,
  `nom` varchar(200) NOT NULL,
  `marque` varchar(200) DEFAULT NULL,
  `description` text NOT NULL,
  `prix` varchar(255) NOT NULL,
  `urlPicture` text NOT NULL,
  `motsCles` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `produit`
--

INSERT INTO `produit` (`ID_Produit`, `nom`, `marque`, `description`, `prix`, `urlPicture`, `motsCles`) VALUES
(1, 'New Balance 530 ', 'New Balance', 'La sneaker 530 remet au goût du jour l\'une de nos chaussures de running classiques. Cette chaussure décontractée combine style casual et technologie moderne. L\'amorti ABZORB assure un confort supérieur.\r\n\r\nDonnez une touche rétro à votre démarche avec la sneaker 530 .', '120', 'https://www.kicksdon.com/cdn/shop/products/4c3008d66e63cbdda8a339b322caae8b.jpg?v=1662026183', 'Homme\r\nFemme\r\nBlanc\r\nBleu\r\nArgent'),
(2, 'New Balance 530', 'New Balance', 'La basket New Balance 530 reprend le style quotidien et le marie aux technologies modernes. Dotée d\'un amorti ABZORB sous le pied pour absorber les chocs, cette chaussure offre un confort supérieur où que votre journée vous mène.', '130', 'https://nb.scene7.com/is/image/NB/mr530ow_nb_02_i?$pdpflexf2$&qlt=80&fmt=webp&wid=440&hei=440', 'Homme\r\nFemme\r\nBeige\r\nCrème\r\nBlanc'),
(3, 'New Balance 530', 'New Balance', 'La basket 530 est un retour à l\'un de nos modèles classiques de running. La 530 reprend le style quotidien et le marie aux technologies modernes. L\'amorti ABZORB sous le pied accentue le confort. Donnez une touche rétro avec la 530.', '130', 'https://nb.scene7.com/is/image/NB/mr530ci_nb_02_i?$pdpflexf2$&qlt=80&fmt=webp&wid=440&hei=440', 'Homme\r\nFemme\r\nBleu\r\nBlanc\r\nGris'),
(4, 'New Balance 1906R', 'New Balance', 'L\'emblématique basket pour homme New Balance 1906R est de retour dans un nouveau style. Nommée d\'après l\'année de création de New Balance et modernisée avec une empeigne inspirée des modèles de chaussures de running des années 2000, cette chaussure occupe encore une place de choix dans le monde de la course. Sa semelle extérieure anti-chocs N-ergy intègre la technologie Stability Web pour mieux soutenir la voûte plantaire, tandis qu\'une semelle intermédiaire ACTEVA LITE et l’amorti au talon ABZORB SBS assurent un confort et une stabilité exceptionnels. Le modèle 1906R intemporel est une chaussure de running élégante et performante.', '160', 'https://nb.scene7.com/is/image/NB/m1906rv_nb_02_i?$pdpflexf2$&qlt=80&fmt=webp&wid=440&hei=440', 'Homme\r\nGris'),
(5, 'New Balance 1906R', 'New Balance', 'L\'emblématique basket pour homme New Balance 1906R est de retour dans un nouveau style. Nommée d\'après l\'année de création de New Balance et modernisée avec une empeigne inspirée des modèles de chaussures de running des années 2000, cette chaussure occupe encore une place de choix dans le monde de la course. Sa semelle extérieure anti-chocs N-ergy intègre la technologie Stability Web pour mieux soutenir la voûte plantaire, tandis qu\'une semelle intermédiaire ACTEVA LITE et l’amorti au talon ABZORB SBS assurent un confort et une stabilité exceptionnels. Le modèle 1906R intemporel est une chaussure de running élégante et performante.', '160', 'https://nb.scene7.com/is/image/NB/m1906rw_nb_02_i?$pdpflexf2$&qlt=80&fmt=webp&wid=440&hei=440', 'Homme\r\nBeige\r\nMarron'),
(6, 'New Balance 1906R', 'New Balance', 'La basket New Balance 1906R allie style et fonctionnalitÃ© dans un modÃ¨le imbattable. Ce retour aux baskets des annÃ©es 2000 comprend une semelle intermÃ©diaire ABZORB et un rembourrage au talon ABZORB SBS pour plus de confort et de stabilitÃ©, tandis que la semelle extÃ©rieure absorbant les chocs N-ergy est dotÃ©e d\'un Stability Web pour un meilleur soutien de la voÃ»te plantaire. L?empeigne synthÃ©tique et en maille Ã©lÃ©gante complÃ¨te le look.', '160', 'https://nb.scene7.com/is/image/NB/m1906rch_nb_02_i?$pdpflexf2$&qlt=80&fmt=webp&wid=440&hei=440', 'Homme Beige'),
(7, 'Adidas Response CL', 'Adidas', 'Sois prêt à tout. Cette chaussure adidas Response CL présente une structure intégrée inspirée des modèles de trail running. La semelle intermédiaire douce et légère t\'assure un confort optimal même pendant les sorties les plus intenses. Sérieusement, qui a dit que sortir demandait un gros effort ?', '120', 'https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/46cef9827aea4caf8d8dadaa00b99693_9366/Chaussure_Response_CL_Blanc_GZ1562_01_standard.jpg', 'Homme\r\nFemme\r\nBlanc\r\nBeige'),
(8, 'Adidas Response CL ', 'Adidas ', 'Sois prÃªt Ã  tout. Cette chaussure adidas Response CL prÃ©sente une structure intÃ©grÃ©e inspirÃ©e des modÃ¨les de trail running. La semelle intermÃ©diaire douce et lÃ©gÃ¨re t\'assure un confort optimal mÃªme pendant les sorties les plus intenses. SÃ©rieusement, qui a dit que sortir demandait un gros effort ?', '125', 'https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/6044dd9dea9948f49524adaa00b9338b_9366/Chaussure_Response_CL_Gris_GZ1561_01_standard.jpg', 'Homme\r\nGris'),
(9, 'Adidas Response CL', 'Adidas', 'Sois prêt à tout. Cette chaussure adidas Response CL présente une structure intégrée inspirée des modèles de trail running. La semelle intermédiaire douce et légère t\'assure un confort optimal même pendant les sorties les plus intenses. Sérieusement, qui a dit que sortir demandait un gros effort ?', '120', 'https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/983ee5ab486a44fe9a30a0df64487b7a_9366/Chaussure_Response_CL_Beige_ID4594_01_standard.jpg', 'Femme\r\nBeige'),
(10, 'Adidas Response CL', 'Adidas', 'Sois prêt à tout. Cette chaussure adidas Response CL présente une structure intégrée inspirée des modèles de trail running. La semelle intermédiaire douce et légère t\'assure un confort optimal même pendant les sorties les plus intenses. Sérieusement, qui a dit que sortir demandait un gros effort ?', '120', 'https://www.impact-premium.com/30977-large_default/ig3377-adidas.jpg', 'Homme\r\nNoir \r\nBlanc\r\nGris'),
(11, 'Nike P-6000', 'Nike', 'MÃ©langeant des Ã©lÃ©ments d\'anciens modÃ¨les Pegasus, la Nike P-6000 est confectionnÃ©e en mesh respirant et prÃ©sente des revÃªtements horizontaux et verticaux pour un look running venu tout droit des annÃ©es 2000 aussi remarquable qu\'un majestueux cheval ailÃ©. ConÃ§ue Ã  partir des modÃ¨les Nike Pegasus 25 et 2006, son esthÃ©tique running s\'associe Ã  un confort optimal tout au long de la journÃ©e pour conquÃ©rir la rue.', '120', 'https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/316d0cbc-53c5-4d01-8f41-a7bebc82d621/chaussure-p-6000-GSS28x.png', 'Femme\r\nNoir\r\nGris\r\nArgent\r\nBlanc'),
(12, 'Nike Vomero 5', 'Nike', 'Bouscule les codes avec la Vomero 5. Ce modèle indispensable, qui associe complexité et profondeur mais qui va avec tout, fait son grand retour. Superposant des empiècements en tissu, cuir et plastique, c\'est l\'une des sneakers les plus cools de la saison.', '230', 'https://static.nike.com/a/images/t_prod_ss/w_640,c_limit,f_auto/26828627-d96a-4ea9-aeca-d39e1b469d8d/date-de-sortie-de-la-vomero%C2%A05-«%E2%80%8F%C2%A0pink-oxford-and-plum-eclipse%C2%A0»-pour-femme-fv1166-200.jpg', 'Femme\r\nRose\r\nMarron\r\nNoir'),
(13, 'Nike ACG Air Zoom Gaiadome GORE-TEX', 'Nike', 'Ces bottes vous entraînent vers l\'aventure.Fabriquées à partir de matières résistantes, elles intègrent la technologie GORE-TEX et de larges ergots adaptés aux pentes escarpées.En deux mots, ce modèle a été pensé pour toutes vos aventures, qu\'il s\'agisse d\'une randonnée en montagne ou de vos explorations quotidiennes.', '225', 'https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/50382e91-d56e-4343-833a-11a69d5c3d55/chaussures-acg-air-zoom-gaiadome-gore-tex-34L414.png', 'Homme\r\nBleu\r\nRose\r\nRandonnée'),
(14, 'Nike Air Max Plus Utility', 'Nike', 'Inspirée par la plage mais conçue pour la ville, la Nike Air Max Plus Utility revient dans une version plus robuste, parfaite pour tes aventures urbaines. L\'empeigne en maille respirante intègre un nouveau renfort latéral en daim robuste. Un jeu supplémentaire de lacets avec fermeture à stoppeur apporte la touche finale, tout en assurant une tenue sûre et un bon maintien. Les unités Max Air visibles à l\'avant-pied et au talon créent une expérience Tuned Air qui allie confort et style rebelle.', '200', 'https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/074029cc-a851-4dce-9612-de9ed6f02ac8/chaussure-air-max-plus-utility-pour-kZZ2vm.png', 'Blanc\r\nHomme'),
(15, 'Nike Dunk Low', 'Nike', 'La Dunk est historiquement la chaussure préférée des équipes universitaires. Le pack « Be True To Your School » rend hommage à cet héritage en faisant revivre la première campagne promotionnelle de cette icône. Les couleurs représentent les grandes universités, tandis que le cuir impeccable ajoute la touche d\'éclat parfaite pour illuminer ta voie vers la victoire. Alors, lace tes chaussures et laisse parler ta fierté. C\'est parti ?', '120', 'https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/81a06f7b-a32c-4fde-96fa-9de036608760/chaussures-dunk-low-retro-pour-p6gmkm.png', 'Femme\r\nBleu\r\nBlanc'),
(16, 'Nike Chaussettes', 'Nike', 'Donnez le meilleur de vous-même pendant vos entraînements avec les chaussettes Nike Everyday Cushioned. La semelle en molleton épais vous offre un maximum de confort pour les exercices de jambes et de musculation, tandis que la bande côtelée située au niveau de la voûte plantaire enveloppe le milieu du pied pour créer une bonne sensation de maintien.', '15', 'https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/17f774fb-a2ba-46cb-b6bf-7a798594f5ee/chaussettes-de-training-everyday-cushioned-d0lBx3.png', 'Homme\r\nFemme\r\nBlanc'),
(17, 'Chaussettes Nike', 'Nike', 'Donnez le meilleur de vous-même pendant vos entraînements avec les chaussettes Nike Everyday Cushioned. La semelle en molleton épais vous offre un maximum de confort pour les exercices de jambes et de musculation, tandis que la bande côtelée située au niveau de la voûte plantaire enveloppe le milieu du pied pour créer une bonne sensation de maintien.', '15', 'https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco/a868b225-7777-43ea-9761-2e79739bac56/chaussettes-de-training-everyday-cushioned-d0lBx3.png', 'Homme\r\nFemme\r\nNoir'),
(18, 'Chaussettes Adidas', 'Adidas', 'Ces chaussettes mi-mollet adidas sont conçues en coton et polyester pour plus de douceur. Offrant un excellent soutien de la voûte plantaire, elles sont matelassées au talon et à l\'avant-pied pour un confort optimal et présentent des coutures liées pour éviter les frottements.\r\n\r\nCe produit est composé à 50 % minimum d\'un mélange de matériaux recyclés et renouvelables.', '25', 'https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/b017ab7db04e471b87acaf27009ef16b_9366/Chaussettes_matelassees_Sportswear_6_paires_Noir_IC1316_03_standard.jpg', 'Femme\r\nHommes\r\nNoir'),
(19, 'Chaussettes Adidas', 'Adidas', 'Ces chaussettes mi-mollet adidas sont conçues en coton et polyester pour plus de douceur. Offrant un excellent soutien de la voûte plantaire, elles sont matelassées au talon et à l\'avant-pied pour un confort optimal et présentent des coutures liées pour éviter les frottements.\r\n\r\nCe produit est composé à 50 % minimum d\'un mélange de matériaux recyclés et renouvelables.', '25', 'https://assets.adidas.com/images/h_840,f_auto,q_auto,fl_lossy,c_fill,g_auto/22dbc8f581d64c9e9aa3af10009c5ef5_9366/Chaussettes_matelassees_Sportswear_6_paires_Blanc_HT3453_03_standard.jpg', 'Femme\r\nHomme\r\nBlanc'),
(20, 'Chaussettes New Balance', 'New Balance', 'Élément intemporel de toute garde-robe, les chaussettes New Balance NB Everyday Ankle 3 Pairs sont incontournables. Ces socquettes en coton rembourré léger arborent un logo NB classique au style remarquable. Chaque pack contient 3 paires de socquettes.', '13', 'https://nb.scene7.com/is/image/NB/las33933bk_nb_03_i?$pdpflexf2$&qlt=80&fmt=webp&wid=440&hei=440', 'Homme\r\nFemme\r\nNoir'),
(21, 'Chaussettes New Balance ', 'New Balance', 'Élément intemporel de toute garde-robe, les chaussettes New Balance NB Everyday Ankle 3 Pairs sont incontournables. Ces socquettes en coton rembourré léger arborent un logo NB classique au style remarquable. Chaque pack contient 3 paires de socquettes.', '13', 'https://nb.scene7.com/is/image/NB/las33933wt_nb_03_i?$pdpflexf2$&qlt=80&fmt=webp&wid=440&hei=440', 'Homme\r\nFemme\r\nBlanc');

-- --------------------------------------------------------

--
-- Table structure for table `variantes_produit`
--

CREATE TABLE `variantes_produit` (
  `ID_Variante` int(11) NOT NULL,
  `ID_Produit` int(11) DEFAULT NULL,
  `pointure` varchar(10) NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `variantes_produit`
--

INSERT INTO `variantes_produit` (`ID_Variante`, `ID_Produit`, `pointure`, `stock`) VALUES
(1, 1, '36', 13),
(2, 1, '37', 54),
(3, 1, '38', 7),
(4, 1, '39', 3),
(5, 1, '40', 20),
(6, 1, '42', 2),
(7, 2, '36', 1),
(8, 2, '40', 6),
(9, 2, '41', 3),
(10, 2, '37', 3),
(11, 2, '39', 5),
(12, 3, '36', 3),
(13, 3, '37', 5),
(14, 3, '39', 1),
(15, 3, '40', 6),
(16, 3, '41', 3),
(17, 4, '39', 9),
(18, 4, '40', 7),
(19, 4, '41', 6),
(20, 4, '42', 13),
(21, 4, '43', 10),
(22, 4, '44', 8),
(23, 4, '45', 5),
(24, 4, '46', 2),
(25, 4, '47', 1),
(26, 5, '39', 9),
(27, 5, '40', 7),
(28, 5, '41', 6),
(29, 5, '42', 13),
(30, 5, '43', 10),
(31, 5, '44', 8),
(32, 5, '45', 5),
(33, 5, '46', 2),
(34, 5, '47', 1),
(35, 6, '39', 9),
(36, 6, '40', 7),
(37, 6, '41', 6),
(38, 6, '42', 13),
(39, 6, '43', 10),
(40, 6, '44', 8),
(41, 6, '45', 5),
(42, 6, '46', 2),
(43, 6, '47', 1),
(44, 8, '39', 9),
(45, 8, '40', 7),
(46, 8, '41', 6),
(47, 8, '42', 13),
(48, 8, '43', 10),
(49, 8, '44', 8),
(50, 8, '45', 5),
(51, 8, '46', 2),
(52, 8, '47', 1),
(53, 10, '39', 9),
(54, 10, '40', 7),
(55, 10, '41', 6),
(56, 10, '42', 13),
(57, 10, '43', 10),
(58, 10, '44', 8),
(59, 10, '45', 5),
(60, 10, '46', 2),
(61, 10, '47', 1),
(62, 13, '39', 9),
(63, 13, '40', 7),
(64, 13, '41', 6),
(65, 13, '42', 13),
(66, 13, '43', 10),
(67, 13, '44', 8),
(68, 15, '45', 5),
(69, 15, '46', 2),
(70, 15, '47', 1),
(71, 15, '39', 9),
(72, 14, '40', 7),
(73, 14, '41', 6),
(74, 14, '42', 13),
(75, 14, '43', 10),
(76, 14, '44', 8),
(77, 14, '45', 5),
(78, 14, '46', 2),
(79, 14, '47', 1),
(80, 7, '36', 9),
(81, 7, '39', 9),
(82, 7, '40', 7),
(83, 7, '41', 6),
(84, 7, '42', 13),
(85, 7, '43', 10),
(86, 7, '44', 8),
(87, 7, '45', 5),
(88, 7, '46', 2),
(89, 7, '47', 1),
(90, 7, '37', 4),
(91, 7, '38', 4),
(92, 9, '36', 5),
(93, 9, '37', 4),
(94, 9, '38', 4),
(95, 9, '39', 8),
(96, 9, '40', 7),
(97, 9, '41', 6),
(98, 11, '36', 5),
(99, 11, '37', 4),
(100, 11, '38', 4),
(101, 11, '39', 8),
(102, 11, '40', 7),
(103, 11, '41', 6),
(104, 12, '36', 5),
(105, 12, '37', 2),
(106, 12, '38', 4),
(107, 12, '39', 8),
(108, 12, '40', 2),
(109, 12, '41', 6),
(110, 15, '36', 5),
(113, 15, '38', 4),
(114, 15, '39', 8),
(115, 15, '40', 7),
(116, 15, '41', 6),
(117, 15, '37', 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `administrateur`
--
ALTER TABLE `administrateur`
  ADD PRIMARY KEY (`ID_ADMINISTRATEUR`),
  ADD UNIQUE KEY `Email` (`email`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`ID_Client`),
  ADD UNIQUE KEY `Email` (`email`);

--
-- Indexes for table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`ID_Commande`),
  ADD KEY `ID_Client` (`ID_Client`);

--
-- Indexes for table `details_commande`
--
ALTER TABLE `details_commande`
  ADD PRIMARY KEY (`ID_Detail`),
  ADD KEY `ID_Commande` (`ID_Commande`),
  ADD KEY `ID_Produit` (`ID_Produit`);

--
-- Indexes for table `moderateur`
--
ALTER TABLE `moderateur`
  ADD PRIMARY KEY (`ID_Moderateur`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`ID_Produit`);

--
-- Indexes for table `variantes_produit`
--
ALTER TABLE `variantes_produit`
  ADD PRIMARY KEY (`ID_Variante`),
  ADD KEY `ID_Produit` (`ID_Produit`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `administrateur`
--
ALTER TABLE `administrateur`
  MODIFY `ID_ADMINISTRATEUR` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `ID_Client` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `commande`
--
ALTER TABLE `commande`
  MODIFY `ID_Commande` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `details_commande`
--
ALTER TABLE `details_commande`
  MODIFY `ID_Detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `moderateur`
--
ALTER TABLE `moderateur`
  MODIFY `ID_Moderateur` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `produit`
--
ALTER TABLE `produit`
  MODIFY `ID_Produit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `variantes_produit`
--
ALTER TABLE `variantes_produit`
  MODIFY `ID_Variante` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=118;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
