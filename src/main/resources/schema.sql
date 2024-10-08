drop table if exists users cascade;
drop table if exists films cascade;
drop table if exists mpa_ratings cascade;
drop table if exists friends cascade;
drop table if exists likes cascade;
drop table if exists movies cascade;
drop table if exists movie_genres cascade;
drop table if exists genres cascade;

create table if not exists users
(
    id       serial primary key,
    name     varchar(255) not null unique,
    email    varchar(255) not null,
    login    varchar(255) not null unique,
    birthday date         not null
);
create table if not exists friends
(
    first_user_id     int references users (id) on delete cascade,
    second_user_id    int references users (id) on delete cascade,
    friendship_status varchar(20) check ( friendship_status in ('дружба подтверждена', 'дружба не подтверждена')),
    primary key (first_user_id, second_user_id)
);

create table if not exists mpa_ratings
(
    id          serial primary key,
    name    varchar(15)  not null unique ,
    description varchar(100) not null

);
create table if not exists genres
(
    id   serial primary key,
   name varchar(144) not null
);
create table if not exists films
(
    id            serial primary key,
    title         varchar(255) not null,
    description   varchar(500),
    release_year  date,
    duration      int,
    rating_mpa_id int references mpa_ratings (id) on delete cascade
);


create table if not exists films_genres
(
    film_id  int references films (id) on delete cascade,
    genre_id int references genres (id) on delete cascade,
    primary key (film_id, genre_id)
);

create table if not exists likes
(
    user_id int references users (id) on delete cascade,
    film_id int references films (id) on delete cascade,
    primary key (user_id, film_id)
);

