-- phpMyAdmin SQL Dump
-- version 2.10.2
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Waktu pembuatan: 08. Mei 2015 jam 23:44
-- Versi Server: 5.0.45
-- Versi PHP: 5.2.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- Database: `spk_umsu`
-- 

-- --------------------------------------------------------

-- 
-- Struktur dari tabel `admin`
-- 

CREATE TABLE `admin` (
  `id` varchar(10) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data untuk tabel `admin`
-- 

INSERT INTO `admin` VALUES ('AD001', 'tari', 'tari');
INSERT INTO `admin` VALUES ('AD002', 'lidia', 'lidia');

-- --------------------------------------------------------

-- 
-- Struktur dari tabel `dosen`
-- 

CREATE TABLE `dosen` (
  `nidn` varchar(20) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `tempat_lahir` varchar(30) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `alamat` text NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  PRIMARY KEY  (`nidn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data untuk tabel `dosen`
-- 

INSERT INTO `dosen` VALUES ('1', 'Sudrajat S.E', 'Medan', '1982-05-11', 'Jl. xxxxxxxxxxxxxxxx', '0811122222');
INSERT INTO `dosen` VALUES ('2', 'Irma Syahrani S.E', 'Medan', '1983-05-10', 'Jl. xxxxxxxxxxxxxxx', '08221211121');
INSERT INTO `dosen` VALUES ('3', 'Sulaiman S.E', 'Medan', '1977-04-10', 'Jl. xxxxxxxxxxxxxxxx', '081922322322');
INSERT INTO `dosen` VALUES ('4', 'Asni Syafitri S.E', 'Medan', '1982-05-04', 'Jl. xxxxxxxxxxxxxxxxxxxx', '0819222772');

-- --------------------------------------------------------

-- 
-- Struktur dari tabel `hasil_kinerja`
-- 

CREATE TABLE `hasil_kinerja` (
  `nidn` int(11) NOT NULL,
  `nilai` double NOT NULL,
  `kinerja` varchar(20) NOT NULL,
  PRIMARY KEY  (`nidn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data untuk tabel `hasil_kinerja`
-- 

INSERT INTO `hasil_kinerja` VALUES (1, 59.0126189982742, 'Buruk');
INSERT INTO `hasil_kinerja` VALUES (2, 100, 'Sangat Baik');
INSERT INTO `hasil_kinerja` VALUES (3, 79.3295703998475, 'Baik');
INSERT INTO `hasil_kinerja` VALUES (4, 0, 'Sangat Buruk');

-- --------------------------------------------------------

-- 
-- Struktur dari tabel `kinerja_dosen`
-- 

CREATE TABLE `kinerja_dosen` (
  `nidn` varchar(10) NOT NULL,
  `nilai` double NOT NULL,
  `kinerja` varchar(20) NOT NULL,
  PRIMARY KEY  (`nidn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data untuk tabel `kinerja_dosen`
-- 


-- --------------------------------------------------------

-- 
-- Struktur dari tabel `kriteria`
-- 

CREATE TABLE `kriteria` (
  `id` varchar(10) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `bobot` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data untuk tabel `kriteria`
-- 

INSERT INTO `kriteria` VALUES ('KR001', 'Kemampuan Mengajar', 2);
INSERT INTO `kriteria` VALUES ('KR002', 'Kehadiran', 2);
INSERT INTO `kriteria` VALUES ('KR003', 'Ketepatan Waktu', 2);
INSERT INTO `kriteria` VALUES ('KR004', 'Kemampuan Memotivasi Mahasiswa', 2);
INSERT INTO `kriteria` VALUES ('KR005', 'Kemampuan Penggunaan Media Pembelajaran', 2);

-- --------------------------------------------------------

-- 
-- Struktur dari tabel `kriteria_dosen`
-- 

CREATE TABLE `kriteria_dosen` (
  `nidn` varchar(15) NOT NULL,
  `id_kriteria` varchar(10) NOT NULL,
  `id_himpunan` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data untuk tabel `kriteria_dosen`
-- 

INSERT INTO `kriteria_dosen` VALUES ('1', 'KR002', 'SB005');
INSERT INTO `kriteria_dosen` VALUES ('1', 'KR001', 'SB003');
INSERT INTO `kriteria_dosen` VALUES ('3', 'KR001', 'SB001');
INSERT INTO `kriteria_dosen` VALUES ('2', 'KR002', 'SB004');
INSERT INTO `kriteria_dosen` VALUES ('2', 'KR001', 'SB001');
INSERT INTO `kriteria_dosen` VALUES ('2', 'KR003', 'SB011');
INSERT INTO `kriteria_dosen` VALUES ('1', 'KR003', 'SB012');
INSERT INTO `kriteria_dosen` VALUES ('3', 'KR002', 'SB005');
INSERT INTO `kriteria_dosen` VALUES ('4', 'KR001', 'SB008');
INSERT INTO `kriteria_dosen` VALUES ('4', 'KR002', 'SB010');
INSERT INTO `kriteria_dosen` VALUES ('4', 'KR003', 'SB013');
INSERT INTO `kriteria_dosen` VALUES ('3', 'KR003', 'SB012');
INSERT INTO `kriteria_dosen` VALUES ('1', 'KR004', 'SB018');
INSERT INTO `kriteria_dosen` VALUES ('1', 'KR005', 'SB022');
INSERT INTO `kriteria_dosen` VALUES ('2', 'KR004', 'SB016');
INSERT INTO `kriteria_dosen` VALUES ('2', 'KR005', 'SB021');
INSERT INTO `kriteria_dosen` VALUES ('3', 'KR004', 'SB017');
INSERT INTO `kriteria_dosen` VALUES ('3', 'KR005', 'SB021');
INSERT INTO `kriteria_dosen` VALUES ('4', 'KR005', 'SB025');
INSERT INTO `kriteria_dosen` VALUES ('4', 'KR004', 'SB019');

-- --------------------------------------------------------

-- 
-- Struktur dari tabel `subkriteria`
-- 

CREATE TABLE `subkriteria` (
  `id` varchar(10) NOT NULL,
  `id_kriteria` varchar(10) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `bobot` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 
-- Dumping data untuk tabel `subkriteria`
-- 

INSERT INTO `subkriteria` VALUES ('SB001', 'KR001', 'Sangat Baik', 5);
INSERT INTO `subkriteria` VALUES ('SB002', 'KR001', 'Baik', 4);
INSERT INTO `subkriteria` VALUES ('SB003', 'KR001', 'Cukup', 3);
INSERT INTO `subkriteria` VALUES ('SB004', 'KR002', 'Sangat Baik', 5);
INSERT INTO `subkriteria` VALUES ('SB005', 'KR002', 'Baik', 4);
INSERT INTO `subkriteria` VALUES ('SB006', 'KR002', 'Cukup', 3);
INSERT INTO `subkriteria` VALUES ('SB007', 'KR001', 'Buruk', 2);
INSERT INTO `subkriteria` VALUES ('SB008', 'KR001', 'Sangat Buruk', 1);
INSERT INTO `subkriteria` VALUES ('SB009', 'KR002', 'Buruk', 2);
INSERT INTO `subkriteria` VALUES ('SB010', 'KR002', 'Sangat Buruk', 1);
INSERT INTO `subkriteria` VALUES ('SB011', 'KR003', 'Sangat Baik', 5);
INSERT INTO `subkriteria` VALUES ('SB012', 'KR003', 'Baik', 4);
INSERT INTO `subkriteria` VALUES ('SB013', 'KR003', 'Cukup', 3);
INSERT INTO `subkriteria` VALUES ('SB014', 'KR003', 'Buruk', 2);
INSERT INTO `subkriteria` VALUES ('SB015', 'KR003', 'Sangat Buruk', 1);
INSERT INTO `subkriteria` VALUES ('SB016', 'KR004', 'Sangat Baik', 5);
INSERT INTO `subkriteria` VALUES ('SB017', 'KR004', 'Baik', 4);
INSERT INTO `subkriteria` VALUES ('SB018', 'KR004', 'Cukup', 3);
INSERT INTO `subkriteria` VALUES ('SB019', 'KR004', 'Buruk', 2);
INSERT INTO `subkriteria` VALUES ('SB020', 'KR004', 'Sangat Buruk', 1);
INSERT INTO `subkriteria` VALUES ('SB021', 'KR005', 'Sangat Baik', 5);
INSERT INTO `subkriteria` VALUES ('SB022', 'KR005', 'Baik', 4);
INSERT INTO `subkriteria` VALUES ('SB023', 'KR005', 'Cukup', 3);
INSERT INTO `subkriteria` VALUES ('SB024', 'KR005', 'Buruk', 2);
INSERT INTO `subkriteria` VALUES ('SB025', 'KR005', 'Sangat Buruk', 1);
