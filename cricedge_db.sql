-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 25, 2019 at 08:37 AM
-- Server version: 10.1.40-MariaDB
-- PHP Version: 7.3.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cricedge_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `CustomerID` int(11) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Password` varchar(45) DEFAULT NULL,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `CompanyName` varchar(45) DEFAULT NULL,
  `PhoneNumber` varchar(45) DEFAULT NULL,
  `Address1` varchar(45) DEFAULT NULL,
  `Address2` varchar(45) DEFAULT NULL,
  `City` varchar(45) DEFAULT NULL,
  `Country` varchar(45) DEFAULT NULL,
  `State` varchar(45) DEFAULT NULL,
  `Zip` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`CustomerID`, `Email`, `Password`, `FirstName`, `LastName`, `CompanyName`, `PhoneNumber`, `Address1`, `Address2`, `City`, `Country`, `State`, `Zip`) VALUES
(3, 'admin', 'admin', 'Hung', 'Ho', '', '7789272070', '13688 100 Ave', '2702', 'Surrey', 'Canada', 'BC', 'V3T0G5');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `OrderID` varchar(20) NOT NULL,
  `OrderDate` date DEFAULT NULL,
  `Status` varchar(20) DEFAULT NULL,
  `TotalPrice` double(5,2) DEFAULT NULL,
  `CustomerID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`OrderID`, `OrderDate`, `Status`, `TotalPrice`, `CustomerID`) VALUES
('6732407193', '2019-07-24', 'Order Processing', 224.00, 3),
('8632407192', '2019-07-24', 'Order Processing', 313.60, 3);

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `OrderID` varchar(20) NOT NULL,
  `ProductID` int(11) NOT NULL,
  `Price` double(5,2) NOT NULL,
  `Size` varchar(5) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `order_details`
--

INSERT INTO `order_details` (`OrderID`, `ProductID`, `Price`, `Size`, `Quantity`) VALUES
('8632407192', 2, 280.00, 'S', 1),
('6732407193', 5, 80.00, 'S', 1),
('6732407193', 1, 60.00, 'M', 2);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ProductID` int(11) NOT NULL,
  `ProductName` varchar(45) DEFAULT NULL,
  `Price` double(5,2) DEFAULT NULL,
  `Description` text,
  `Image` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`ProductID`, `ProductName`, `Price`, `Description`, `Image`) VALUES
(1, 'Masuri Legacy Plus Helmet - Black', 60.00, 'The Masuri Legacy helmet, a result of Masuri\'s commitment to produce a lightweight helmet at a great price is an affordable club level helmet for cricketers of all ages and abilities.', '/resources/images/Masuri-Legacy-Plus-Helmet-Black-1.jpg'),
(2, 'adidas XT 2.0 Cricket Bat - White', 280.00, 'Power through every innings with the adidas XT 2.0 Cricket Bat – White, a full-profiled bat with extreme run-scoring potential.', '/resources/images/adidas-XT-20-Cricket-Bat-White-1.jpg'),
(3, 'adidas XT 2.0 RH Batting Gloves - White', 48.75, 'Dominate from the crease knowing your hands are safe with adidas XT 2.0 RH Batting Gloves, featuring a classic rounded finger construction for top protection throughout any game of cricket.', '/resources/images/adidas-XT-20-RH-Batting-Gloves-White-1.jpg'),
(4, 'adidas XT 2.0 RH Batting Pads - White', 80.00, 'Defend your legs and play your natural game with adidas XT 2.0 RH Batting Pads, featuring a flex joint knee for improved manoeuvrability.', '/resources/images/adidas-XT-20-RH-Batting-Pads-White-1.jpg'),
(5, 'adidas XT 1.0 Wicket Keeping Gloves - White', 80.00, 'Make big catches in your cricket match with adidas XT 1.0 Wicket Keeping Gloves, featuring a rubber octo palm for ultimate grip on the ball.', '/resources/images/adidas-XT-10-Wicket-Keeping-Gloves-White-1.jpg'),
(6, 'adidas XT 1.0 Wicket Keeping Pads - White', 48.00, 'Show your skills and keep your legs safe with adidas XT 1.0 Wicket Keeping Pads, featuring a modern, lightweight design for maximum manoeuvrability behind the stumps during any game of cricket.', '/resources/images/adidas-XT-10-Wicket-Keeping-Pads-White-1.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`CustomerID`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`OrderID`),
  ADD KEY `CustomerID` (`CustomerID`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD KEY `OrderID` (`OrderID`),
  ADD KEY `ProductID` (`ProductID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ProductID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customers`
--
ALTER TABLE `customers`
  MODIFY `CustomerID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `ProductID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`CustomerID`) REFERENCES `customers` (`CustomerID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
