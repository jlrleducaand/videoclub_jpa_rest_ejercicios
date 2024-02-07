create table if not exists categoria
(
    id_categoria         bigint auto_increment
        primary key,
    nombre               varchar(255) null,
    ultima_actualizacion datetime(6)  null
);

create table if not exists idioma
(
    id_idioma            bigint auto_increment
        primary key,
    nombre               varchar(255) null,
    ultima_actualizacion datetime(6)  null
);

create table if not exists pelicula
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
    id_idioma                  bigint         not null,
    id_idioma_original         bigint         null,
    constraint FK3e6ndurdgkc1qxq221r50i4ny
        foreign key (id_idioma) references idioma (id_idioma),
    constraint FKc6ih5f7kvuomg8expij334wif
        foreign key (id_idioma_original) references idioma (id_idioma)
);

create table if not exists pelicula_categoria
(
    id_pelicula  bigint not null,
    id_categoria bigint not null,
    primary key (id_pelicula, id_categoria),
    constraint FK5str8bg5ud479tyhsk4tsawcc
        foreign key (id_pelicula) references pelicula (id_pelicula),
    constraint FKihinh62000sduyaij8nvrf1c2
        foreign key (id_categoria) references categoria (id_categoria)
);

create table if not exists tarjeta
(
    numero    bigint auto_increment
        primary key,
    caducidad date not null
);

create table if not exists socio
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

create table if not exists tutorial
(
    id_tutorial       bigint auto_increment
        primary key,
    descripcion       varchar(256) null,
    fecha_publicacion datetime(6)  not null,
    public            bit          null,
    titulo            varchar(50)  null
);

create table if not exists comentario
(
    id                   bigint auto_increment
        primary key,
    texto                varchar(255) null,
    tutorial_id_tutorial bigint       null,
    tutorial_id_fk       bigint       not null,
    constraint FK27081oehrtaf8cqq4ak8ijc2e
        foreign key (tutorial_id_tutorial) references tutorial (id_tutorial),
    constraint FK_TUTO
        foreign key (tutorial_id_fk) references tutorial (id_tutorial)
);

create index title_Index
    on tutorial (titulo);

