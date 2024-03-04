create table actor
(
    id_actor             bigint auto_increment
        primary key,
    apellidos            varchar(255) null,
    nombre               varchar(255) null,
    ultima_actualizacion datetime(6)  null
);

create table categoria
(
    id_categoria         bigint auto_increment
        primary key,
    nombre               varchar(255) null,
    ultima_actualizacion datetime(6)  null,
    constraint UK_4gux6k5lb69tln4x3u9k3evph
        unique (nombre)
);

create table categoriadto
(
    id_categoria         bigint auto_increment
        primary key,
    size                 int          null,
    nombre               varchar(255) null,
    ultima_actualizacion datetime(6)  null
);

create table idioma
(
    id_idioma            bigint auto_increment
        primary key,
    nombre               varchar(255) null,
    ultima_actualizacion datetime(6)  null
);

create table pelicula
(
    id_pelicula                bigint auto_increment
        primary key,
    anyo_lanzamiento           datetime(6)    null,
    caracteristicas_especiales varchar(255)   null,
    clasificacion              varchar(255)   null,
    descripcion                varchar(255)   null,
    duracion                   int            not null,
    duracion_alquiler          int            null,
    rental_rate                decimal(38, 2) null,
    replacement_cost           decimal(38, 2) null,
    titulo                     varchar(255)   null,
    ultima_actualizacion       datetime(6)    null,
    id_idioma                  bigint         null,
    id_idioma_original         bigint         null,
    sdf                        varbinary(255) null,
    constraint FK3e6ndurdgkc1qxq221r50i4ny
        foreign key (id_idioma) references idioma (id_idioma),
    constraint FKc6ih5f7kvuomg8expij334wif
        foreign key (id_idioma_original) references idioma (id_idioma)
);

create table actor_peliculas
(
    actores_id_actor      bigint not null,
    peliculas_id_pelicula bigint not null,
    primary key (actores_id_actor, peliculas_id_pelicula),
    constraint FKjxk7wd3wuwqymi6u1e6mc9cwn
        foreign key (actores_id_actor) references actor (id_actor),
    constraint FKrccmvra47hp0iip2rfks2mu2
        foreign key (peliculas_id_pelicula) references pelicula (id_pelicula)
);

create table pelicula_categoria
(
    categoria_id bigint not null,
    pelicula_id  bigint not null,
    primary key (categoria_id, pelicula_id),
    constraint FK4o1cr1tsj0317edxjf1qlm31e
        foreign key (categoria_id) references categoria (id_categoria),
    constraint FK6flrfkotc4k2wlde82wtxji9p
        foreign key (categoria_id) references categoriadto (id_categoria),
    constraint FKbkoesoaqju12tw9lsf3r87rsj
        foreign key (pelicula_id) references pelicula (id_pelicula)
);

create table post
(
    id    bigint auto_increment
        primary key,
    title varchar(255) null
);

create table tag
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table post_tag
(
    tag_id  bigint not null,
    post_id bigint not null,
    primary key (tag_id, post_id),
    constraint FKac1wdchd2pnur3fl225obmlg0
        foreign key (tag_id) references tag (id),
    constraint FKc2auetuvsec0k566l0eyvr9cs
        foreign key (post_id) references post (id)
);

create table tarjeta
(
    numero    bigint auto_increment
        primary key,
    caducidad date not null
);

create table socio
(
    dni        varchar(9)   not null
        primary key,
    apellidos  varchar(255) null,
    nombre     varchar(255) null,
    id_tarjeta bigint       null,
    constraint UK_7a5twu9ps6qob6s1axnyl7adv
        unique (id_tarjeta),
    constraint FKiixvdlq6oevx0vbf0v5bwijl0
        foreign key (id_tarjeta) references tarjeta (numero)
);

create table tutorial
(
    id_tutorial       bigint auto_increment
        primary key,
    descripcion       varchar(256) null,
    fecha_publicacion datetime(6)  not null,
    public            bit          null,
    titulo            varchar(50)  null
);

create table comentario
(
    id             bigint auto_increment
        primary key,
    texto          varchar(255) null,
    tutorial_id_fk bigint       not null,
    constraint FK_TUTO
        foreign key (tutorial_id_fk) references tutorial (id_tutorial)
);

create index title_Index
    on tutorial (titulo);


