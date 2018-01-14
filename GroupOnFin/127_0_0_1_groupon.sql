-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 11, 2017 at 12:27 AM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 7.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `groupon`
--

-- --------------------------------------------------------

--
-- Table structure for table `channel`
--

CREATE TABLE `channel` (
  `CID` varchar(255) NOT NULL COMMENT 'ChannelID',
  `Name` varchar(255) NOT NULL COMMENT 'Name of Channel'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `channel_user`
--

CREATE TABLE `channel_user` (
  `CID` int(255) NOT NULL,
  `UID` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `MsgID` bigint(20) DEFAULT NULL COMMENT 'Message ID',
  `RID` int(255) NOT NULL COMMENT 'Reciever''s ID',
  `UID` int(255) NOT NULL COMMENT 'UserID od Sender',
  `Content` varchar(60000) NOT NULL COMMENT 'Message',
  `Priority` tinyint(1) NOT NULL COMMENT 'Has High Priority?',
  `SorG` varchar(1) NOT NULL COMMENT 'SingleOrGroup',
  `DateTime` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`MsgID`, `RID`, `UID`, `Content`, `Priority`, `SorG`, `DateTime`) VALUES
(NULL, 2, 1, 'gtgtcgy', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'helllllllllllllllllllllllllllllllllllllllllllllllllllo', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'hello..', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'hello....', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'Hello.....', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'hello askd', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'hello bo', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'maa ka bhosda', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'hello', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'hello...', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'hello', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'asdas', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'Hi', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'hi', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'hi', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'hi', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'Hih', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'teri maa ka bhosda', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'Aur bhai !', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'as', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'HI', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'Hola', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'DSA', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'ASDA', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'ASA', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'CCDSD', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'CSD', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'FASDS', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'SS\\d', 1, 'S', 'dateTime dateTime'),
(NULL, 1, 2, 'x', 1, 'S', 'dateTime dateTime'),
(NULL, 2, 1, 'hello bro !', 1, 'S', 'dateTime dateTime');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `NID` bigint(20) NOT NULL,
  `Type` varchar(20) NOT NULL COMMENT 'Type of Notification',
  `SUName` varchar(255) NOT NULL COMMENT 'Sender''s Username',
  `RUName` varchar(255) NOT NULL COMMENT 'Receiver''s Username',
  `DateTime` varchar(255) NOT NULL COMMENT 'Date/Time of Notification'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`NID`, `Type`, `SUName`, `RUName`, `DateTime`) VALUES
(1, 'message', 'jatin', 'JAshMe', 'dateTime dateTime'),
(2, 'message', 'JAshMe', 'jatin', 'dateTime dateTime'),
(3, 'message', 'jatin', 'JAshMe', 'dateTime dateTime'),
(4, 'message', 'jatin', 'JAshMe', 'dateTime dateTime'),
(5, 'message', 'JAshMe', 'jatin', 'dateTime dateTime'),
(6, 'message', 'jatin', 'JAshMe', 'dateTime dateTime'),
(7, 'message', 'JAshMe', 'jatin', 'dateTime dateTime'),
(8, 'message', 'JAshMe', 'jatin', 'dateTime dateTime');

-- --------------------------------------------------------

--
-- Table structure for table `userdetails`
--

CREATE TABLE `userdetails` (
  `UID` bigint(20) NOT NULL COMMENT 'UserID',
  `UName` varchar(500) NOT NULL COMMENT 'Username',
  `Pass` varchar(500) NOT NULL COMMENT 'Password',
  `FName` varchar(500) NOT NULL COMMENT 'Full Name',
  `LName` varchar(500) NOT NULL COMMENT 'Last Name',
  `isLoggedIn` tinyint(1) DEFAULT '0' COMMENT 'Tells Whether User is Logged in'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userdetails`
--

INSERT INTO `userdetails` (`UID`, `UName`, `Pass`, `FName`, `LName`, `isLoggedIn`) VALUES
(1, 'JAshMe', 'jyot', 'jyot', 'Mehta', 1),
(2, 'jatin', 'jatin', 'Jatin', 'Tayal', 1),
(3, 'preeto', '123456789', 'Preetam', 'Brazzers', 0),
(4, 'yash', '12345678', 'Yash', 'Chapani', 0),
(5, 'mishiboy', '12345678', 'Divyanshu', 'Mishra', 1);

-- --------------------------------------------------------

--
-- Table structure for table `workspace`
--

CREATE TABLE `workspace` (
  `WID` varchar(255) NOT NULL COMMENT 'WorkID',
  `Name` varchar(500) NOT NULL COMMENT 'Name of Group'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `workspace_channel`
--

CREATE TABLE `workspace_channel` (
  `WID` varchar(255) NOT NULL COMMENT 'WorkID',
  `CID` varchar(255) NOT NULL COMMENT 'ChannelID'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `workspace_user`
--

CREATE TABLE `workspace_user` (
  `WID` varchar(255) NOT NULL COMMENT 'WorkID',
  `UID` varchar(255) NOT NULL COMMENT 'UserID'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `channel`
--
ALTER TABLE `channel`
  ADD PRIMARY KEY (`CID`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`NID`);

--
-- Indexes for table `userdetails`
--
ALTER TABLE `userdetails`
  ADD PRIMARY KEY (`UID`);

--
-- Indexes for table `workspace`
--
ALTER TABLE `workspace`
  ADD PRIMARY KEY (`WID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `NID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `userdetails`
--
ALTER TABLE `userdetails`
  MODIFY `UID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'UserID', AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
