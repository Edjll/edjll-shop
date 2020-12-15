-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Дек 15 2020 г., 11:22
-- Версия сервера: 8.0.15
-- Версия PHP: 7.1.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `shop`
--
CREATE DATABASE IF NOT EXISTS `shop` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `shop`;

DELIMITER $$
--
-- Процедуры
--
CREATE DEFINER=`spring`@`localhost` PROCEDURE `allChildrenCategory` (IN `categoryId` INT)  begin
    with recursive tmp (id, name, description, parent_id) as (
        select     id,
                   name,
                   description,
                   parent_id
        from       product_category
        where      parent_id = 19
        union all
        select     p.id,
                   p.name,
                   p.description,
                   p.parent_id
        from       product_category p
                       inner join tmp
                                  on p.parent_id = tmp.id
    )
    select id, description, name, parent_id from tmp
    union all
    select id, description, name, parent_id from product_category where id = categoryId;
end$$

CREATE DEFINER=`spring`@`localhost` PROCEDURE `maxCount` (IN `productId` INT, OUT `maxCount` INT)  begin
    select
            (select count(*)
             from (select product.id
                   from sale_product join product
                                          on product.product_data_id = productId
                                              and sale_product.product_id = product.id
                   group by product.id
                   having product.id not in (select sales.p
                                             from sale_product join (select sales.sp as sp, refund.id as rf, sales.p
                                                                     from refund right join (select sale_product.id as sp, product.id as p
                                                                                             from sale_product join product
                                                                                                                    on product.product_data_id = productId and sale_product.product_id = product.id) as sales
                                                                                            on refund.sale_product_id = sales.sp) as sales
                                                                    on sale_product.id = sales.sp
                                                                        and rf is null)) as sales)
            +
            (select count(*)
             from sale_product right join (select product.id as id
                                           from product join product_data
                                                             on product_data.id = productId
                                                                 and product.product_data_id = product_data.id) as products
                                          on sale_product.product_id = products.id
             group by sale_product.id
             having sale_product.id is null) into maxCount;
end$$

CREATE DEFINER=`spring`@`localhost` PROCEDURE `sellableProducts` (IN `productId` INT)  begin
    select * from product where id in (
    select
        (
            select product.id
            from sale_product join product
                                   on product.product_data_id = productId
                                       and sale_product.product_id = product.id
            group by product.id
            having product.id not in (select sales.p
                                      from sale_product join (select sales.sp as sp, refund.id as rf, sales.p
                                                              from refund right join (select sale_product.id as sp, product.id as p
                                                                                      from sale_product join product
                                                                                                             on product.product_data_id = productId and sale_product.product_id = product.id) as sales
                                                                                     on refund.sale_product_id = sales.sp) as sales
                                                             on sale_product.id = sales.sp
                                                                 and rf is null)
        )
    union all
    (
        select product.id
        from product
        where product.product_data_id = productId
          and product.id not in (select ps.id as p
                                 from sale_product join (select product.id as id
                                                         from product join product_data
                                                                           on product_data.id = productId
                                                                               and product.product_data_id = product_data.id) as ps
                                                        on sale_product.product_id = ps.id)
    ));
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Структура таблицы `attribute`
--

CREATE TABLE `attribute` (
  `id` bigint(20) NOT NULL,
  `description` varchar(150) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `attribute_category_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `attribute`
--

INSERT INTO `attribute` (`id`, `description`, `name`, `attribute_category_id`) VALUES
(1, 'Занимаемое пространство', 'Объём', 1),
(2, 'Масса на объём', 'Плотность', 1),
(3, 'Цвет вещества', 'Цвет', 1),
(4, 'Отрезок, соединяющий две точки на окружности и проходящий через центр окружности, а также длина этого отрезка', 'Диаметр', 1),
(5, 'Макроскопически однородный металлический материал, состоящий из смеси двух или большего числа химических элементов', 'Сплав', 1),
(6, 'Макроскопически однородный металлический материал, состоящий из смеси двух или большего числа химических элементов', 'Сплав2', 1),
(14, '321', '321', 3),
(15, '432', '432', 3),
(16, '765', '765', NULL),
(18, '234', '234', 1),
(21, '123', '123', 1);

-- --------------------------------------------------------

--
-- Структура таблицы `attribute_category`
--

CREATE TABLE `attribute_category` (
  `id` bigint(20) NOT NULL,
  `name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `attribute_category`
--

INSERT INTO `attribute_category` (`id`, `name`) VALUES
(1, 'Общее'),
(2, '123'),
(3, '321');

-- --------------------------------------------------------

--
-- Структура таблицы `attribute_product_category`
--

CREATE TABLE `attribute_product_category` (
  `product_category_id` bigint(20) NOT NULL,
  `attribute_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `attribute_product_category`
--

INSERT INTO `attribute_product_category` (`product_category_id`, `attribute_id`) VALUES
(1, 1),
(1, 2),
(2, 4),
(2, 5);

-- --------------------------------------------------------

--
-- Структура таблицы `attribute_value`
--

CREATE TABLE `attribute_value` (
  `id` bigint(20) NOT NULL,
  `attribute_id` bigint(20) NOT NULL,
  `value_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `attribute_value`
--

INSERT INTO `attribute_value` (`id`, `attribute_id`, `value_id`) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 1, 3),
(4, 2, 4),
(5, 3, 5),
(6, 5, 6),
(7, 4, 7),
(8, 6, 6),
(41, 4, 29);

-- --------------------------------------------------------

--
-- Структура таблицы `basket`
--

CREATE TABLE `basket` (
  `id` bigint(20) NOT NULL,
  `count` int(11) NOT NULL,
  `product_data_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `basket`
--

INSERT INTO `basket` (`id`, `count`, `product_data_id`, `user_id`) VALUES
(47, 1, 2, 1),
(54, 1, 3, 1),
(55, 1, 1, 1),
(56, 3, 14, 1),
(57, 4, 15, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `city`
--

CREATE TABLE `city` (
  `id` bigint(20) NOT NULL,
  `name` varchar(30) NOT NULL,
  `country_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `city`
--

INSERT INTO `city` (`id`, `name`, `country_id`) VALUES
(1, 'Энгельс', 1),
(2, 'Саратов', 1);

-- --------------------------------------------------------

--
-- Структура таблицы `country`
--

CREATE TABLE `country` (
  `id` bigint(20) NOT NULL,
  `name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `country`
--

INSERT INTO `country` (`id`, `name`) VALUES
(1, 'Россия'),
(2, 'США'),
(4, 'Канада');

-- --------------------------------------------------------

--
-- Структура таблицы `delivery`
--

CREATE TABLE `delivery` (
  `id` bigint(20) NOT NULL,
  `address` varchar(50) NOT NULL,
  `status_delivery` int(11) DEFAULT NULL,
  `sale_product_id` bigint(20) NOT NULL,
  `storage_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `delivery`
--

INSERT INTO `delivery` (`id`, `address`, `status_delivery`, `sale_product_id`, `storage_id`) VALUES
(1, 'Политех', 1, 4, 1),
(2, 'Политех', 0, 5, 1),
(3, 'Политех', 2, 6, 1),
(4, 'Политех', 0, 7, 1),
(5, 'Политех', 0, 8, 1),
(6, 'Политех', 0, 9, 1),
(7, 'Политех', 0, 10, 1),
(8, 'Политех', 0, 11, 1),
(9, 'Политех', 0, 12, 1),
(10, 'Политех', 1, 13, 1),
(11, 'Политех', 0, 14, 1),
(12, 'Политех', 0, 15, 1),
(13, 'Политех', 0, 16, 1),
(14, 'Политех', 0, 17, 1),
(15, 'Политех', 1, 18, 1),
(16, 'Политех', 2, 19, 1),
(17, 'Политех', 0, 20, 1),
(18, 'Политех', 0, 21, 1),
(19, 'Политех', 0, 22, 1),
(20, 'Политех', 0, 23, 1),
(21, 'Политех', 0, 24, 1),
(22, 'Политех', 0, 25, 1),
(23, 'Политех', 0, 26, 1),
(24, 'Политех', 0, 27, 1),
(25, 'Политех', 0, 28, 1),
(26, 'Политех', 0, 29, 1),
(27, 'Политех', 0, 30, 1),
(28, 'Политех', 0, 31, 1),
(29, 'Политех', 0, 32, 1),
(30, 'Политех', 0, 33, 1),
(31, 'Политех', 0, 34, 1),
(32, 'Политех', 0, 35, 1),
(33, 'Политех', 0, 36, 1),
(34, 'Политех', 0, 37, 1),
(35, 'Политех', 0, 38, 1),
(36, 'Политех', 0, 39, 1),
(37, 'Политех', 0, 40, 1),
(38, 'Политех', 0, 41, 1),
(39, 'Политех', 0, 42, 1),
(40, 'Политех', 0, 43, 1),
(41, 'Политех', 0, 44, 1),
(42, 'Политех', 0, 45, 1),
(43, 'Политех', 0, 46, 1),
(44, 'Политех', 0, 47, 1),
(45, 'Политех', 0, 48, 1),
(46, 'Политех', 0, 49, 1),
(47, 'Политех', 0, 50, 1),
(48, 'Политех', 0, 51, 1),
(54, 'Политех', 0, 58, 1),
(55, 'Политех', 0, 59, 1),
(56, 'Политех', 0, 60, 1),
(57, 'Политех', 0, 61, 1),
(58, 'Политех', 0, 62, 1),
(59, 'Политех', 0, 63, 1),
(60, 'Политех', 0, 64, 1),
(61, 'Политех', 0, 65, 1),
(62, 'Политех', 0, 66, 1),
(63, 'Политех', 0, 67, 1),
(64, 'Политех', 1, 68, 1),
(65, 'Политех', 2, 69, 1),
(66, 'Политех', 0, 70, 1),
(67, 'Политех', 0, 71, 1),
(68, 'Политех', 0, 72, 1),
(69, 'Политех', 1, 73, 1),
(70, 'Политех', 2, 74, 1),
(71, 'Политех', 2, 75, 1),
(72, 'Политех', 1, 76, 1),
(73, 'Политех', 0, 77, 1),
(74, 'Политех', 0, 78, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `email`
--

CREATE TABLE `email` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `employee`
--

CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL,
  `dismissed` bit(1) NOT NULL,
  `employment_contract` varchar(255) NOT NULL,
  `passpot` varchar(10) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `employee`
--

INSERT INTO `employee` (`id`, `dismissed`, `employment_contract`, `passpot`, `user_id`) VALUES
(1, b'0', '521521521', '12445215', 1),
(2, b'0', '5325325', '12521', 2);

-- --------------------------------------------------------

--
-- Структура таблицы `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(5);

-- --------------------------------------------------------

--
-- Структура таблицы `image`
--

CREATE TABLE `image` (
  `id` bigint(20) NOT NULL,
  `filename` varchar(255) NOT NULL,
  `manufacturer_id` bigint(20) DEFAULT NULL,
  `product_data_id` bigint(20) DEFAULT NULL,
  `promotion_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `image`
--

INSERT INTO `image` (`id`, `filename`, `manufacturer_id`, `product_data_id`, `promotion_id`) VALUES
(1, 'a4d6cd6c-bd00-4f58-a5a1-713f2ef7eaa8.png', 1, NULL, NULL),
(2, '2387bfd7-7209-4513-aeb6-e9651f774cd5.png', NULL, 1, NULL),
(3, 'c697302c-785d-426b-a02b-ab70413eaf76.png', NULL, 1, NULL),
(4, 'f08cc4e2-b36d-4861-81ae-c00a2fc8102f.png', NULL, 2, NULL),
(5, '68d1f536-523a-4a98-990e-8c15d3fc5353.png', NULL, 3, NULL),
(19, 'a886cfe6-54c1-4c96-9c21-aec73517f2c6.jpeg', NULL, NULL, 3),
(20, 'caaf9be4-27c2-4fa3-a26c-029cadd3db6b.png', NULL, 14, NULL),
(21, 'd42e73c4-e606-4266-a064-2846967815ca.png', NULL, 15, NULL),
(22, '50c6ff09-f1aa-412b-9ec2-9b955f4daa39.jpeg', NULL, NULL, 4);

-- --------------------------------------------------------

--
-- Структура таблицы `manufacturer`
--

CREATE TABLE `manufacturer` (
  `id` bigint(20) NOT NULL,
  `description` varchar(300) NOT NULL,
  `name` varchar(40) NOT NULL,
  `country_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `manufacturer`
--

INSERT INTO `manufacturer` (`id`, `description`, `name`, `country_id`) VALUES
(1, 'Крупнейшая американская автомобильная корпорация', 'General Motors', 2);

-- --------------------------------------------------------

--
-- Структура таблицы `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `production_date` datetime(6) NOT NULL,
  `rejection` bit(1) NOT NULL,
  `product_data_id` bigint(20) NOT NULL,
  `supply_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `product`
--

INSERT INTO `product` (`id`, `production_date`, `rejection`, `product_data_id`, `supply_id`) VALUES
(1, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(2, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(3, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(4, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(5, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(6, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(7, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(8, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(9, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(10, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(11, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(12, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(13, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(14, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(15, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(16, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(17, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(18, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(19, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(20, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(21, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(22, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(23, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(24, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(25, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(26, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(27, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(28, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(29, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(30, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(31, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(32, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(33, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(34, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(35, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(36, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(37, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(38, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(39, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(40, '2020-11-30 23:47:00.000000', b'0', 1, 1),
(41, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(42, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(43, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(44, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(45, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(46, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(47, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(48, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(49, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(50, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(51, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(52, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(53, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(54, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(55, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(56, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(57, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(58, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(59, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(60, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(61, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(62, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(63, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(64, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(65, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(66, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(67, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(68, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(69, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(70, '2020-11-30 23:47:00.000000', b'0', 2, 1),
(71, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(72, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(73, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(74, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(75, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(76, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(77, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(78, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(79, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(80, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(81, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(82, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(83, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(84, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(85, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(86, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(87, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(88, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(89, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(90, '2020-12-01 00:39:00.000000', b'0', 3, 2),
(91, '2020-12-13 19:53:00.000000', b'0', 2, 4),
(92, '2020-12-13 19:53:00.000000', b'0', 2, 4),
(93, '2020-12-13 19:53:00.000000', b'0', 2, 4),
(94, '2020-12-13 19:53:00.000000', b'0', 2, 4),
(95, '2020-12-13 19:53:00.000000', b'0', 2, 4),
(96, '2020-12-13 19:53:00.000000', b'0', 2, 4),
(97, '2020-12-13 19:53:00.000000', b'0', 2, 4),
(98, '2020-12-13 19:53:00.000000', b'0', 2, 4),
(99, '2020-12-13 19:53:00.000000', b'0', 2, 4),
(100, '2020-12-13 19:53:00.000000', b'0', 2, 4),
(101, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(102, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(103, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(104, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(105, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(106, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(107, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(108, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(109, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(110, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(111, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(112, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(113, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(114, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(115, '2020-12-13 19:53:00.000000', b'0', 14, 4),
(116, '2020-12-13 19:53:00.000000', b'0', 15, 4),
(117, '2020-12-13 19:53:00.000000', b'0', 15, 4),
(118, '2020-12-13 19:53:00.000000', b'0', 15, 4),
(119, '2020-12-13 19:53:00.000000', b'0', 15, 4),
(120, '2020-12-13 19:53:00.000000', b'0', 15, 4),
(121, '2020-12-13 19:53:00.000000', b'0', 15, 4),
(122, '2020-12-13 19:53:00.000000', b'0', 15, 4),
(123, '2020-12-13 19:53:00.000000', b'0', 15, 4);

-- --------------------------------------------------------

--
-- Структура таблицы `product_attribute`
--

CREATE TABLE `product_attribute` (
  `product_data_id` bigint(20) NOT NULL,
  `attribute_value_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `product_attribute`
--

INSERT INTO `product_attribute` (`product_data_id`, `attribute_value_id`) VALUES
(1, 1),
(15, 1),
(1, 2),
(15, 2),
(2, 3),
(2, 4),
(2, 5),
(3, 6),
(14, 6),
(3, 7),
(14, 41);

-- --------------------------------------------------------

--
-- Структура таблицы `product_category`
--

CREATE TABLE `product_category` (
  `id` bigint(20) NOT NULL,
  `description` varchar(300) NOT NULL,
  `name` varchar(30) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `product_category`
--

INSERT INTO `product_category` (`id`, `description`, `name`, `parent_id`) VALUES
(1, 'Вещества, находящиеся в жидком агрегатном состоянии, занимающем промежуточное положение между твёрдым и газообразным состояниями', 'Жидкости', NULL),
(2, 'Неотъемлемая часть автомобиля', 'Колёса', NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `product_data`
--

CREATE TABLE `product_data` (
  `id` bigint(20) NOT NULL,
  `cost` double DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `shelf_life` int(11) DEFAULT NULL,
  `product_category_id` bigint(20) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `manufacturer_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `product_data`
--

INSERT INTO `product_data` (`id`, `cost`, `description`, `name`, `shelf_life`, `product_category_id`, `country_id`, `manufacturer_id`) VALUES
(1, 3000, '<strong>Mobil 1 FS 0W-40</strong> представляет собой улучшенное полностью синтетическое моторное масло, предназначенное для новейших бензиновых и дизельных двигателей (без бензиновых и дизельных сажевых фильтров) и обеспечивающее высокоэффективные общие эксплуатационные характеристики. Оно обеспечивает эффективность очистки и защиту двигателя от износа. Масло Mobil 1 FS 0W-40 способствует эффективной работе двигателя в любых условиях вождения.', 'Mobil 1 FS 0W-40', 36, 1, 2, 1),
(2, 800, 'Антифриз, красный', 'Prestone', 12, 1, 1, 1),
(3, 2700, 'Неотъемлемая часть автомобиля', 'Диски', 96, 2, 2, 1),
(14, 3600, 'Качественные литые диски', 'Литые диски', 36, 2, 2, 1),
(15, 3000, 'Качественно моторное масло', 'Моторное масло', 36, 1, 1, 1);

-- --------------------------------------------------------

--
-- Структура таблицы `promotion`
--

CREATE TABLE `promotion` (
  `id` bigint(20) NOT NULL,
  `date_end` datetime(6) NOT NULL,
  `date_start` datetime(6) NOT NULL,
  `description` varchar(150) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `promotion`
--

INSERT INTO `promotion` (`id`, `date_end`, `date_start`, `description`, `name`) VALUES
(3, '2021-01-01 03:25:00.000000', '2020-12-14 03:25:00.000000', 'Огромная распродажа', 'Чёрная пятница'),
(4, '2021-01-01 22:22:00.000000', '2020-12-07 22:21:00.000000', 'Распродажа в честь нового года', 'Новогодняя распродажа');

-- --------------------------------------------------------

--
-- Структура таблицы `promotion_product`
--

CREATE TABLE `promotion_product` (
  `id` bigint(20) NOT NULL,
  `discount` int(11) NOT NULL,
  `product_data_id` bigint(20) NOT NULL,
  `promotion_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `promotion_product`
--

INSERT INTO `promotion_product` (`id`, `discount`, `product_data_id`, `promotion_id`) VALUES
(8, 40, 1, 3),
(9, 20, 3, 3),
(10, 70, 15, 4);

-- --------------------------------------------------------

--
-- Структура таблицы `question`
--

CREATE TABLE `question` (
  `id` bigint(20) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `answer_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `question` varchar(255) NOT NULL,
  `question_date` datetime(6) NOT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `question`
--

INSERT INTO `question` (`id`, `answer`, `answer_date`, `email`, `question`, `question_date`, `employee_id`, `user_id`) VALUES
(1, 'И вам!', '2020-12-13 20:41:23.428000', NULL, 'Добрый день.', '2020-12-06 14:09:43.234000', 2, 1),
(2, NULL, NULL, NULL, 'Хорошего дня', '2020-12-13 19:08:25.516000', NULL, 2),
(3, 'И вам!', '2020-12-15 10:17:56.125000', NULL, 'Добрый день', '2020-12-15 10:14:16.853000', 2, 3);

-- --------------------------------------------------------

--
-- Структура таблицы `refund`
--

CREATE TABLE `refund` (
  `id` bigint(20) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `date` datetime(6) NOT NULL,
  `reason` varchar(255) NOT NULL,
  `status_refund` int(11) DEFAULT NULL,
  `type_refund` int(11) DEFAULT NULL,
  `refund_sale_product_id` bigint(20) DEFAULT NULL,
  `sale_product_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `refund`
--

INSERT INTO `refund` (`id`, `comment`, `date`, `reason`, `status_refund`, `type_refund`, `refund_sale_product_id`, `sale_product_id`) VALUES
(1, 'Денег нет, продуктов нет. Прошёл месяц, деньги не завезли. Ну, ладно', '2020-12-06 14:03:22.463000', 'Не подошла вязкость. Денег нет, продукты есть. Прошёл месяц, деньги есть. ОТДАЙТЕ МНЕ МОЁ', 1, 0, 70, 6),
(2, '', '2020-12-06 14:09:05.521000', 'Верните мне продукт', 2, 0, NULL, 19),
(3, NULL, '2020-12-13 19:04:22.441000', 'Не подошёл', 0, 0, NULL, 69),
(4, 'Хорошо', '2020-12-15 10:15:40.489000', 'Не подошёл', 1, 0, 78, 74);

-- --------------------------------------------------------

--
-- Структура таблицы `review`
--

CREATE TABLE `review` (
  `id` bigint(20) NOT NULL,
  `advantages` varchar(255) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `disadvantages` varchar(255) NOT NULL,
  `rating` int(11) NOT NULL,
  `status_review` int(11) DEFAULT NULL,
  `product_data_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `review`
--

INSERT INTO `review` (`id`, `advantages`, `comment`, `date`, `disadvantages`, `rating`, `status_review`, `product_data_id`, `user_id`) VALUES
(1, '5215', '51251', '2020-12-02 01:09:51.876000', '2521', 3, 1, 2, 1),
(2, 'Хороший продукт', '-', '2020-12-13 16:20:14.424000', '-', 2, 1, 1, 1),
(3, 'Неплохой продукт', 'Мне очень понравился', '2020-12-13 19:13:27.474000', 'Не найдено', 5, 0, 15, 2),
(4, 'Не дорого', '-', '2020-12-15 10:18:35.431000', '-', 4, 1, 15, 3);

-- --------------------------------------------------------

--
-- Структура таблицы `sale`
--

CREATE TABLE `sale` (
  `id` bigint(20) NOT NULL,
  `date` datetime NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `sale`
--

INSERT INTO `sale` (`id`, `date`, `user_id`) VALUES
(4, '2020-12-03 14:01:20', 1),
(5, '2020-12-06 20:58:25', 2),
(7, '2020-12-07 10:41:15', 1),
(8, '2020-12-13 15:07:46', 1),
(9, '2020-12-13 15:12:47', 1),
(10, '2020-12-13 18:58:37', 2),
(11, '2020-12-15 10:13:24', 3);

-- --------------------------------------------------------

--
-- Структура таблицы `sale_product`
--

CREATE TABLE `sale_product` (
  `id` bigint(20) NOT NULL,
  `cost` double NOT NULL,
  `product_id` bigint(20) DEFAULT NULL,
  `sale_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `sale_product`
--

INSERT INTO `sale_product` (`id`, `cost`, `product_id`, `sale_id`) VALUES
(4, 3000, 1, 4),
(5, 3000, 2, 4),
(6, 3000, 3, 4),
(7, 3000, 4, 4),
(8, 3000, 5, 4),
(9, 3000, 6, 4),
(10, 3000, 7, 4),
(11, 3000, 8, 4),
(12, 3000, 9, 4),
(13, 3000, 10, 4),
(14, 3000, 11, 4),
(15, 3000, 12, 4),
(16, 2700, 71, 4),
(17, 2700, 72, 4),
(18, 2700, 73, 4),
(19, 2700, 74, 4),
(20, 2700, 75, 4),
(21, 0, 3, 4),
(22, 800, 41, 5),
(23, 800, 42, 5),
(24, 800, 43, 5),
(25, 800, 44, 5),
(26, 800, 45, 5),
(27, 800, 46, 5),
(28, 800, 47, 5),
(29, 800, 48, 5),
(30, 800, 49, 5),
(31, 800, 50, 5),
(32, 800, 51, 5),
(33, 800, 52, 5),
(34, 800, 53, 5),
(35, 800, 54, 5),
(36, 800, 55, 5),
(37, 800, 56, 5),
(38, 800, 57, 5),
(39, 800, 58, 5),
(40, 800, 59, 5),
(41, 800, 60, 5),
(42, 800, 61, 5),
(43, 800, 62, 5),
(44, 800, 63, 5),
(45, 800, 64, 5),
(46, 800, 65, 5),
(47, 800, 66, 5),
(48, 800, 67, 5),
(49, 800, 68, 5),
(50, 800, 69, 5),
(51, 800, 70, 5),
(58, 2700, 74, 7),
(59, 2700, 76, 7),
(60, 2700, 77, 7),
(61, 2700, 78, 7),
(62, 2700, 79, 7),
(63, 2700, 80, 8),
(64, 2700, 81, 9),
(65, 2700, 82, 9),
(66, 2700, 83, 9),
(67, 3000, 13, 10),
(68, 3000, 14, 10),
(69, 3000, 116, 10),
(70, 0, 15, 4),
(71, 3000, 16, 11),
(72, 3000, 17, 11),
(73, 3000, 18, 11),
(74, 3000, 19, 11),
(75, 3000, 20, 11),
(76, 3000, 21, 11),
(77, 3000, 22, 11),
(78, 0, 19, 11);

-- --------------------------------------------------------

--
-- Структура таблицы `storage`
--

CREATE TABLE `storage` (
  `id` bigint(20) NOT NULL,
  `description` varchar(150) NOT NULL,
  `name` varchar(40) NOT NULL,
  `city_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `storage`
--

INSERT INTO `storage` (`id`, `description`, `name`, `city_id`) VALUES
(1, 'Для хранения жидкостей', 'Склад №1', 1),
(2, 'Склад для колёс', 'Склад №2', 2);

-- --------------------------------------------------------

--
-- Структура таблицы `supply`
--

CREATE TABLE `supply` (
  `id` bigint(20) NOT NULL,
  `date` datetime(6) NOT NULL,
  `storage_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `supply`
--

INSERT INTO `supply` (`id`, `date`, `storage_id`) VALUES
(1, '2020-11-30 23:47:00.000000', 1),
(2, '2020-12-01 00:39:00.000000', 1),
(4, '2020-12-13 19:53:00.000000', 1);

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(40) NOT NULL,
  `last_name` varchar(40) NOT NULL,
  `password` varchar(255) NOT NULL,
  `patronymic` varchar(40) DEFAULT NULL,
  `phone` varchar(15) NOT NULL,
  `registration_date` datetime(6) NOT NULL,
  `city_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`id`, `email`, `first_name`, `last_name`, `password`, `patronymic`, `phone`, `registration_date`, `city_id`) VALUES
(1, 'adm.test.edjll@ya.ru', 'Admin', 'Edjll', '$2a$08$B3OmCBQAycOVe/jh0pCAeejv51dYngKdFhdvXuLvj8PjMp/ne.VKK', '', '81234567890', '2020-12-01 18:53:28.856000', 1),
(2, 'supp.test.edjll@ya.ru', 'Support', 'Edjll', '$2a$08$eEqQv/HioeUd9siNgEQwaewX51XH3.McVtKpYh5w7pETXW3asaepu', '', '352523456', '2020-12-06 20:58:03.446000', 1),
(3, 'aleksey.test.edjll@ya.ru', 'Алексей', 'Зойбрей', '$2a$08$IxE0BqV8p2DdC2fM5qSEneSbC7g.Cyc1a6xGZgvvWCkfTop/Nn0iW', 'Иванович', '65436754765', '2020-12-14 20:22:08.968000', 1);

-- --------------------------------------------------------

--
-- Структура таблицы `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role`) VALUES
(1, 0),
(1, 1),
(1, 2),
(2, 0),
(2, 1),
(3, 0);

-- --------------------------------------------------------

--
-- Структура таблицы `value`
--

CREATE TABLE `value` (
  `id` bigint(20) NOT NULL,
  `unit` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `value`
--

INSERT INTO `value` (`id`, `unit`, `value`) VALUES
(1, 'л', '4'),
(2, 'кг/м3', '1100'),
(3, 'л', '3'),
(4, 'кг/м3', '900'),
(5, ' ', 'Красный'),
(6, ' ', 'Алюминий'),
(7, 'см', '90'),
(11, '321', '321'),
(12, '123', '123'),
(21, '865865', '865'),
(29, 'см', '100');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `attribute`
--
ALTER TABLE `attribute`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKl1iyqty76dqbt66jdtq0nlo2b` (`attribute_category_id`);

--
-- Индексы таблицы `attribute_category`
--
ALTER TABLE `attribute_category`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `attribute_product_category`
--
ALTER TABLE `attribute_product_category`
  ADD PRIMARY KEY (`product_category_id`,`attribute_id`),
  ADD KEY `FKaaa0oi3tr8t18docrl2rqjd96` (`attribute_id`);

--
-- Индексы таблицы `attribute_value`
--
ALTER TABLE `attribute_value`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK59xqw12tl928rqcdu2h9o6mau` (`attribute_id`),
  ADD KEY `FKop9gqk40f4qr682stvn6g83en` (`value_id`);

--
-- Индексы таблицы `basket`
--
ALTER TABLE `basket`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcs5padmvsv2bm3fxk1l15d451` (`product_data_id`),
  ADD KEY `FKfp7yinn3dh4sy1ia364xp3d9g` (`user_id`);

--
-- Индексы таблицы `city`
--
ALTER TABLE `city`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrpd7j1p7yxr784adkx4pyepba` (`country_id`);

--
-- Индексы таблицы `country`
--
ALTER TABLE `country`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `delivery`
--
ALTER TABLE `delivery`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKnte4s5wl53c54d5l7fytst9vh` (`sale_product_id`),
  ADD KEY `FKbw7rmqkyk8ibjbu7mi2wl1mv8` (`storage_id`);

--
-- Индексы таблицы `email`
--
ALTER TABLE `email`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6lk0xml9r7okjdq0onka4ytju` (`user_id`);

--
-- Индексы таблицы `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKdpgelxe4hfqormqasx3k5mq3s` (`manufacturer_id`),
  ADD KEY `FKfc5xbbti24l8ecgv927lslc7q` (`product_data_id`),
  ADD KEY `FKbm5uhe6t0214y92h8gntrn0br` (`promotion_id`);

--
-- Индексы таблицы `manufacturer`
--
ALTER TABLE `manufacturer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpxwbidpgdjo58391nm3xavg56` (`country_id`);

--
-- Индексы таблицы `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKnhj30yur4yx7g3a2fcmsu5tjc` (`product_data_id`),
  ADD KEY `FKnmdaepvqd4hw12s0w6jc8jk4d` (`supply_id`);

--
-- Индексы таблицы `product_attribute`
--
ALTER TABLE `product_attribute`
  ADD PRIMARY KEY (`product_data_id`,`attribute_value_id`),
  ADD KEY `FKm4rlb208p8hs9h00xmmvpcm71` (`attribute_value_id`);

--
-- Индексы таблицы `product_category`
--
ALTER TABLE `product_category`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlkvy3axep0ba8ccvrvfoyrfu4` (`parent_id`);

--
-- Индексы таблицы `product_data`
--
ALTER TABLE `product_data`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK7sta4v5wk3aau19c5d3fyq8yl` (`product_category_id`),
  ADD KEY `FKmgs1d6ueeh1hr75bu02u63tpf` (`country_id`),
  ADD KEY `FK83ify53tro1so2yhn6p9m0q6d` (`manufacturer_id`);

--
-- Индексы таблицы `promotion`
--
ALTER TABLE `promotion`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `promotion_product`
--
ALTER TABLE `promotion_product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbyetisyewy7laqgqyjhgpbi29` (`product_data_id`),
  ADD KEY `FKeq9krkiyh71kekr3ji9ats5qk` (`promotion_id`);

--
-- Индексы таблицы `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtf3ciytfoinopcu8ncis08kfb` (`employee_id`),
  ADD KEY `FK4ekrlbqiybwk8abhgclfjwnmc` (`user_id`);

--
-- Индексы таблицы `refund`
--
ALTER TABLE `refund`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1lionljh1t8fcs57grj8muowr` (`refund_sale_product_id`),
  ADD KEY `FKqj63gnrustft5n2sx96pco00t` (`sale_product_id`);

--
-- Индексы таблицы `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKnahxfp3dl2pdgjogxpbp4kaao` (`product_data_id`),
  ADD KEY `FKiyf57dy48lyiftdrf7y87rnxi` (`user_id`);

--
-- Индексы таблицы `sale`
--
ALTER TABLE `sale`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKck1t4noryw58a6jcju0pmj38` (`user_id`);

--
-- Индексы таблицы `sale_product`
--
ALTER TABLE `sale_product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKrtwiisrmdqeslt86pacdwwn1o` (`product_id`),
  ADD KEY `FK4dtibi1vwxkx8gjs59nhp0cnq` (`sale_id`);

--
-- Индексы таблицы `storage`
--
ALTER TABLE `storage`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKm746w9flbcph0wudlmiscbpjo` (`city_id`);

--
-- Индексы таблицы `supply`
--
ALTER TABLE `supply`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1l7f2fcmr6pxfvu6ops71ffec` (`storage_id`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK29eqyw0gxw5r4f1ommy11nd9i` (`city_id`);

--
-- Индексы таблицы `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role`);

--
-- Индексы таблицы `value`
--
ALTER TABLE `value`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `attribute`
--
ALTER TABLE `attribute`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT для таблицы `attribute_value`
--
ALTER TABLE `attribute_value`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- AUTO_INCREMENT для таблицы `basket`
--
ALTER TABLE `basket`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT для таблицы `city`
--
ALTER TABLE `city`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `country`
--
ALTER TABLE `country`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `delivery`
--
ALTER TABLE `delivery`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=75;

--
-- AUTO_INCREMENT для таблицы `email`
--
ALTER TABLE `email`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `employee`
--
ALTER TABLE `employee`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `image`
--
ALTER TABLE `image`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT для таблицы `manufacturer`
--
ALTER TABLE `manufacturer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=124;

--
-- AUTO_INCREMENT для таблицы `product_category`
--
ALTER TABLE `product_category`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT для таблицы `product_data`
--
ALTER TABLE `product_data`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT для таблицы `promotion`
--
ALTER TABLE `promotion`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `promotion_product`
--
ALTER TABLE `promotion_product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT для таблицы `question`
--
ALTER TABLE `question`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT для таблицы `refund`
--
ALTER TABLE `refund`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `review`
--
ALTER TABLE `review`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `sale`
--
ALTER TABLE `sale`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT для таблицы `sale_product`
--
ALTER TABLE `sale_product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=79;

--
-- AUTO_INCREMENT для таблицы `storage`
--
ALTER TABLE `storage`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `supply`
--
ALTER TABLE `supply`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT для таблицы `value`
--
ALTER TABLE `value`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `attribute`
--
ALTER TABLE `attribute`
  ADD CONSTRAINT `FKl1iyqty76dqbt66jdtq0nlo2b` FOREIGN KEY (`attribute_category_id`) REFERENCES `attribute_category` (`id`);

--
-- Ограничения внешнего ключа таблицы `attribute_product_category`
--
ALTER TABLE `attribute_product_category`
  ADD CONSTRAINT `FKaaa0oi3tr8t18docrl2rqjd96` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`),
  ADD CONSTRAINT `FKgc6qoa6t3m7jqkdvc2yumsvyw` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`);

--
-- Ограничения внешнего ключа таблицы `attribute_value`
--
ALTER TABLE `attribute_value`
  ADD CONSTRAINT `FK59xqw12tl928rqcdu2h9o6mau` FOREIGN KEY (`attribute_id`) REFERENCES `attribute` (`id`),
  ADD CONSTRAINT `FKop9gqk40f4qr682stvn6g83en` FOREIGN KEY (`value_id`) REFERENCES `value` (`id`);

--
-- Ограничения внешнего ключа таблицы `basket`
--
ALTER TABLE `basket`
  ADD CONSTRAINT `FKcs5padmvsv2bm3fxk1l15d451` FOREIGN KEY (`product_data_id`) REFERENCES `product_data` (`id`),
  ADD CONSTRAINT `FKfp7yinn3dh4sy1ia364xp3d9g` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Ограничения внешнего ключа таблицы `city`
--
ALTER TABLE `city`
  ADD CONSTRAINT `FKrpd7j1p7yxr784adkx4pyepba` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`);

--
-- Ограничения внешнего ключа таблицы `delivery`
--
ALTER TABLE `delivery`
  ADD CONSTRAINT `FKbw7rmqkyk8ibjbu7mi2wl1mv8` FOREIGN KEY (`storage_id`) REFERENCES `storage` (`id`),
  ADD CONSTRAINT `FKnte4s5wl53c54d5l7fytst9vh` FOREIGN KEY (`sale_product_id`) REFERENCES `sale_product` (`id`);

--
-- Ограничения внешнего ключа таблицы `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `FK6lk0xml9r7okjdq0onka4ytju` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Ограничения внешнего ключа таблицы `image`
--
ALTER TABLE `image`
  ADD CONSTRAINT `FKbm5uhe6t0214y92h8gntrn0br` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`),
  ADD CONSTRAINT `FKdpgelxe4hfqormqasx3k5mq3s` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`id`),
  ADD CONSTRAINT `FKfc5xbbti24l8ecgv927lslc7q` FOREIGN KEY (`product_data_id`) REFERENCES `product_data` (`id`);

--
-- Ограничения внешнего ключа таблицы `manufacturer`
--
ALTER TABLE `manufacturer`
  ADD CONSTRAINT `FKpxwbidpgdjo58391nm3xavg56` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`);

--
-- Ограничения внешнего ключа таблицы `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FKnhj30yur4yx7g3a2fcmsu5tjc` FOREIGN KEY (`product_data_id`) REFERENCES `product_data` (`id`),
  ADD CONSTRAINT `FKnmdaepvqd4hw12s0w6jc8jk4d` FOREIGN KEY (`supply_id`) REFERENCES `supply` (`id`);

--
-- Ограничения внешнего ключа таблицы `product_attribute`
--
ALTER TABLE `product_attribute`
  ADD CONSTRAINT `FKg0io88b57ysq19x2xjvb9s4l5` FOREIGN KEY (`product_data_id`) REFERENCES `product_data` (`id`),
  ADD CONSTRAINT `FKm4rlb208p8hs9h00xmmvpcm71` FOREIGN KEY (`attribute_value_id`) REFERENCES `attribute_value` (`id`);

--
-- Ограничения внешнего ключа таблицы `product_category`
--
ALTER TABLE `product_category`
  ADD CONSTRAINT `FKlkvy3axep0ba8ccvrvfoyrfu4` FOREIGN KEY (`parent_id`) REFERENCES `product_category` (`id`);

--
-- Ограничения внешнего ключа таблицы `product_data`
--
ALTER TABLE `product_data`
  ADD CONSTRAINT `FK7sta4v5wk3aau19c5d3fyq8yl` FOREIGN KEY (`product_category_id`) REFERENCES `product_category` (`id`),
  ADD CONSTRAINT `FK83ify53tro1so2yhn6p9m0q6d` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`id`),
  ADD CONSTRAINT `FKmgs1d6ueeh1hr75bu02u63tpf` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`);

--
-- Ограничения внешнего ключа таблицы `promotion_product`
--
ALTER TABLE `promotion_product`
  ADD CONSTRAINT `FKbyetisyewy7laqgqyjhgpbi29` FOREIGN KEY (`product_data_id`) REFERENCES `product_data` (`id`),
  ADD CONSTRAINT `FKeq9krkiyh71kekr3ji9ats5qk` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`);

--
-- Ограничения внешнего ключа таблицы `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `FK4ekrlbqiybwk8abhgclfjwnmc` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKtf3ciytfoinopcu8ncis08kfb` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Ограничения внешнего ключа таблицы `refund`
--
ALTER TABLE `refund`
  ADD CONSTRAINT `FK1lionljh1t8fcs57grj8muowr` FOREIGN KEY (`refund_sale_product_id`) REFERENCES `sale_product` (`id`),
  ADD CONSTRAINT `FKqj63gnrustft5n2sx96pco00t` FOREIGN KEY (`sale_product_id`) REFERENCES `sale_product` (`id`);

--
-- Ограничения внешнего ключа таблицы `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `FKiyf57dy48lyiftdrf7y87rnxi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKnahxfp3dl2pdgjogxpbp4kaao` FOREIGN KEY (`product_data_id`) REFERENCES `product_data` (`id`);

--
-- Ограничения внешнего ключа таблицы `sale`
--
ALTER TABLE `sale`
  ADD CONSTRAINT `FKck1t4noryw58a6jcju0pmj38` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Ограничения внешнего ключа таблицы `sale_product`
--
ALTER TABLE `sale_product`
  ADD CONSTRAINT `FK4dtibi1vwxkx8gjs59nhp0cnq` FOREIGN KEY (`sale_id`) REFERENCES `sale` (`id`),
  ADD CONSTRAINT `FKrtwiisrmdqeslt86pacdwwn1o` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

--
-- Ограничения внешнего ключа таблицы `storage`
--
ALTER TABLE `storage`
  ADD CONSTRAINT `FKm746w9flbcph0wudlmiscbpjo` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`);

--
-- Ограничения внешнего ключа таблицы `supply`
--
ALTER TABLE `supply`
  ADD CONSTRAINT `FK1l7f2fcmr6pxfvu6ops71ffec` FOREIGN KEY (`storage_id`) REFERENCES `storage` (`id`);

--
-- Ограничения внешнего ключа таблицы `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK29eqyw0gxw5r4f1ommy11nd9i` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`);

--
-- Ограничения внешнего ключа таблицы `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
