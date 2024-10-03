
drop table if exists users cascade;
drop table if exists films cascade ;
drop table if exists genres cascade ;
create table if not exists users
(
    id   serial4 primary key,
    name varchar(255) not null unique,
    email varchar(255) not null,
    login varchar(255) not null unique,
    birthdate year
    friends

    );

create table if not exists films(
id	serial4	primary key,
    title	varchar(255) not null,
    description	varchar(500),
    release_year	year
   mpa_rating );

create table if not exists genres
(
    genre_id	int primary key,
    genre_name	varchar (144)
    );

create table if not exists MovieGenres
    movie_id	int foreign key
    genre_id	int foreign key

-- rating
-- friends(подвтержден)
-- likes