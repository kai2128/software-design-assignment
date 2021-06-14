-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 08, 2021 at 03:30 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

drop DATABASE if EXISTS  `flight_management`;
create DATABASE `flight_management`;
ALTER DATABASE flight_management CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `flight_management`;


--
-- Database: `flight_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `prefix` varchar(1) NOT NULL DEFAULT 'A',
  `id` int(3) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `a_username` (`username`,`phone`,`email`),
  UNIQUE KEY `a_username_2` (`username`),
  UNIQUE KEY `a_prefix` (`prefix`,`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`prefix`, `id`, `username`, `name`, `phone`, `email`, `password`) VALUES
('A', 001, 'test', 'testAdmin', '0111234567', 'test@test.com', 'test'),
('A', 002, 'admin', 'admin', '012345678', 'admin@flight.com', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
CREATE TABLE IF NOT EXISTS `bookings` (
  `id` int(5) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `flight_code` varchar(6) NOT NULL,
  `c_id` int(3) UNSIGNED ZEROFILL NOT NULL,
  `card_number` varchar(50) NOT NULL,
  `total_price` double(10,2) NOT NULL,
  `book_status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_book_flight` (`flight_code`),
  KEY `fk_book_client` (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`id`, `flight_code`, `c_id`, `card_number`, `total_price`, `book_status`) VALUES
(00002, 'AA1234', 001, '1234-1111-1111-1111', 230.00, 'BOOKED'),
(00003, 'AA1234', 001, '1234-1234-1234-1234', 230.00, 'BOOKED'),
(00004, 'AA1234', 002, '1234-1234-1234-4321', 230.00, 'CANCELLED');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `prefix` varchar(1) DEFAULT 'C',
  `id` int(3) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `c_username` (`username`,`phone`,`email`),
  UNIQUE KEY `c_username_2` (`username`),
  UNIQUE KEY `c_prefix` (`prefix`,`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`prefix`, `id`, `username`, `name`, `phone`, `email`, `password`) VALUES
('C', 001, '1', '1', '012345667', 'client@test.com', '1'),
('C', 002, 'testUser', 'yuk han', '123456', 'han@com', 'test');

-- --------------------------------------------------------

--
-- Table structure for table `flight`
--

DROP TABLE IF EXISTS `flight`;
CREATE TABLE IF NOT EXISTS `flight` (
  `code` varchar(6) NOT NULL,
  `dep_time` datetime NOT NULL,
  `arr_time` datetime NOT NULL,
  `dep_loc` varchar(50) NOT NULL,
  `arr_loc` varchar(50) NOT NULL,
  `price` double(10,2) NOT NULL,
  `flight_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`code`),
  UNIQUE KEY `flight_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `flight`
--

INSERT INTO `flight` (`code`, `dep_time`, `arr_time`, `dep_loc`, `arr_loc`, `price`, `flight_status`) VALUES
('AA1234', '2021-04-07 23:59:00', '2021-04-08 04:30:00', 'kl', 'kk', 230.00, NULL),
('AA1324', '2021-05-24 08:00:00', '2021-05-24 10:00:00', 'Kuala Lumpur', 'Sarawak', 300.00, 'CANCELLED'),
('AA3334', '2021-06-05 07:30:00', '2021-06-06 07:30:00', 'kl', 'kk', 230.00, 'CANCELLED');

--
-- Triggers `flight`
--
DROP TRIGGER IF EXISTS `after_ins_flight`;
DELIMITER $$
CREATE TRIGGER `after_ins_flight` AFTER INSERT ON `flight` FOR EACH ROW INSERT INTO flight_seat(flight_code, seat_code, is_avail) VALUES 
                                                                     (new.code,'A0', 1),
                                                                     (new.code,'A1', 1),
                                                                     (new.code,'A2', 1),
                                                                     (new.code,'A3', 1),
                                                                     (new.code,'A4', 1),
                                                                     (new.code,'A5', 1),
    (new.code,'A6', 1),
    (new.code,'A7', 1),
    (new.code,'A8', 1),
    (new.code,'A9', 1),
    (new.code,'B0', 1),
    (new.code,'B1', 1),
    (new.code,'B2', 1),
    (new.code,'B3', 1),
    (new.code,'B4', 1),
    (new.code,'B5', 1),
    (new.code,'B6', 1),
    (new.code,'B7', 1),
    (new.code,'B8', 1),
    (new.code,'B9', 1),
    (new.code,'C0', 1),
    (new.code,'C1', 1),
    (new.code,'C2', 1),
    (new.code,'C3', 1),
    (new.code,'C4', 1),
    (new.code,'C5', 1),
    (new.code,'C6', 1),
    (new.code,'C7', 1),
    (new.code,'C8', 1),
    (new.code,'C9', 1),
    (new.code,'D0', 1),
    (new.code,'D1', 1),
    (new.code,'D2', 1),
    (new.code,'D3', 1),
    (new.code,'D4', 1),
    (new.code,'D5', 1),
    (new.code,'D6', 1),
    (new.code,'D7', 1),
    (new.code,'D8', 1),
    (new.code,'D9', 1)
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `flight_seat`
--

DROP TABLE IF EXISTS `flight_seat`;
CREATE TABLE IF NOT EXISTS `flight_seat` (
  `flight_code` varchar(6) NOT NULL,
  `seat_code` varchar(2) NOT NULL,
  `is_avail` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`flight_code`,`seat_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `flight_seat`
--

INSERT INTO `flight_seat` (`flight_code`, `seat_code`, `is_avail`) VALUES
('AA1234', 'A0', 0),
('AA1234', 'A1', 1),
('AA1234', 'A2', 1),
('AA1234', 'A3', 0),
('AA1234', 'A4', 0),
('AA1234', 'A5', 1),
('AA1234', 'A6', 1),
('AA1234', 'A7', 1),
('AA1234', 'A8', 1),
('AA1234', 'A9', 1),
('AA1234', 'B0', 1),
('AA1234', 'B1', 1),
('AA1234', 'B2', 1),
('AA1234', 'B3', 1),
('AA1234', 'B4', 1),
('AA1234', 'B5', 1),
('AA1234', 'B6', 1),
('AA1234', 'B7', 1),
('AA1234', 'B8', 1),
('AA1234', 'B9', 1),
('AA1234', 'C0', 1),
('AA1234', 'C1', 1),
('AA1234', 'C2', 1),
('AA1234', 'C3', 1),
('AA1234', 'C4', 1),
('AA1234', 'C5', 1),
('AA1234', 'C6', 1),
('AA1234', 'C7', 1),
('AA1234', 'C8', 0),
('AA1234', 'C9', 1),
('AA1234', 'D0', 1),
('AA1234', 'D1', 1),
('AA1234', 'D2', 1),
('AA1234', 'D3', 1),
('AA1234', 'D4', 1),
('AA1234', 'D5', 1),
('AA1234', 'D6', 1),
('AA1234', 'D7', 1),
('AA1234', 'D8', 1),
('AA1234', 'D9', 1),
('AA1324', 'A0', 1),
('AA1324', 'A1', 1),
('AA1324', 'A2', 1),
('AA1324', 'A3', 1),
('AA1324', 'A4', 1),
('AA1324', 'A5', 1),
('AA1324', 'A6', 1),
('AA1324', 'A7', 1),
('AA1324', 'A8', 1),
('AA1324', 'A9', 1),
('AA1324', 'B0', 1),
('AA1324', 'B1', 1),
('AA1324', 'B2', 1),
('AA1324', 'B3', 1),
('AA1324', 'B4', 1),
('AA1324', 'B5', 1),
('AA1324', 'B6', 1),
('AA1324', 'B7', 1),
('AA1324', 'B8', 1),
('AA1324', 'B9', 1),
('AA1324', 'C0', 1),
('AA1324', 'C1', 1),
('AA1324', 'C2', 1),
('AA1324', 'C3', 1),
('AA1324', 'C4', 1),
('AA1324', 'C5', 1),
('AA1324', 'C6', 1),
('AA1324', 'C7', 1),
('AA1324', 'C8', 1),
('AA1324', 'C9', 1),
('AA1324', 'D0', 1),
('AA1324', 'D1', 1),
('AA1324', 'D2', 1),
('AA1324', 'D3', 1),
('AA1324', 'D4', 1),
('AA1324', 'D5', 1),
('AA1324', 'D6', 1),
('AA1324', 'D7', 1),
('AA1324', 'D8', 1),
('AA1324', 'D9', 1),
('AA3334', 'A0', 1),
('AA3334', 'A1', 1),
('AA3334', 'A2', 1),
('AA3334', 'A3', 1),
('AA3334', 'A4', 1),
('AA3334', 'A5', 1),
('AA3334', 'A6', 1),
('AA3334', 'A7', 1),
('AA3334', 'A8', 1),
('AA3334', 'A9', 1),
('AA3334', 'B0', 1),
('AA3334', 'B1', 1),
('AA3334', 'B2', 1),
('AA3334', 'B3', 1),
('AA3334', 'B4', 1),
('AA3334', 'B5', 1),
('AA3334', 'B6', 1),
('AA3334', 'B7', 1),
('AA3334', 'B8', 1),
('AA3334', 'B9', 1),
('AA3334', 'C0', 1),
('AA3334', 'C1', 1),
('AA3334', 'C2', 1),
('AA3334', 'C3', 1),
('AA3334', 'C4', 1),
('AA3334', 'C5', 1),
('AA3334', 'C6', 1),
('AA3334', 'C7', 1),
('AA3334', 'C8', 1),
('AA3334', 'C9', 1),
('AA3334', 'D0', 1),
('AA3334', 'D1', 1),
('AA3334', 'D2', 1),
('AA3334', 'D3', 1),
('AA3334', 'D4', 1),
('AA3334', 'D5', 1),
('AA3334', 'D6', 1),
('AA3334', 'D7', 1),
('AA3334', 'D8', 1),
('AA3334', 'D9', 1);

-- --------------------------------------------------------

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
CREATE TABLE IF NOT EXISTS `passenger` (
  `book_id` int(5) UNSIGNED ZEROFILL NOT NULL,
  `p_name` varchar(50) NOT NULL,
  `p_ic` varchar(50) NOT NULL,
  `p_email` varchar(50) NOT NULL,
  `p_phone` varchar(50) NOT NULL,
  `selected_seat` varchar(2) NOT NULL,
  `ticket_price` double(10,2) NOT NULL,
  PRIMARY KEY (`book_id`,`p_ic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `passenger`
--

INSERT INTO `passenger` (`book_id`, `p_name`, `p_ic`, `p_email`, `p_phone`, `selected_seat`, `ticket_price`) VALUES
(00002, 'hahaha', '001129-12-0316', 'elainelowjingyi00@gmail.com', '0123456789', 'A0', 230.00),
(00003, 'qwer', '123456-12-1234', 'afafed@effas.com', '0123456789', 'A4', 115.00),
(00003, 'qwe', '123456-23-1234', 'elafkna@gmail.com', '0123456789', 'A3', 115.00),
(00004, 'Sam Tan', '701002-05-2563', 'sam@gmail.com', '0165871452', 'C8', 230.00);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `fk_book_client	` FOREIGN KEY (`c_id`) REFERENCES `client` (`id`),
  ADD CONSTRAINT `fk_book_flight` FOREIGN KEY (`flight_code`) REFERENCES `flight` (`code`);

--
-- Constraints for table `flight_seat`
--
ALTER TABLE `flight_seat`
  ADD CONSTRAINT `fk_seat_flight` FOREIGN KEY (`flight_code`) REFERENCES `flight` (`code`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `passenger`
--
ALTER TABLE `passenger`
  ADD CONSTRAINT `fk_pass_book` FOREIGN KEY (`book_id`) REFERENCES `bookings` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
