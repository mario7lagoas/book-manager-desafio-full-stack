create table book (
                      id bigint not null auto_increment,
                      data_atualizacao datetime(6),
                      data_cadastro datetime(6),
                      guid varchar(255),
                      author varchar(255),
                      description varchar(255),
                      title varchar(255),
                      year integer,
                      primary key (id)
) engine=InnoDB;

create table usuario (
                         id bigint not null auto_increment,
                         data_atualizacao datetime(6),
                         data_cadastro datetime(6),
                         guid varchar(255),
                         email varchar(50),
                         password varchar(150),
                         user_name varchar(255),
                         primary key (id)
) engine=InnoDB;

create index IDX_GUID_BOOK
    on book (guid);

create index IDX_TITLE_BOOK
    on book (title);

create index IDX_GUID_USU
    on usuario (guid)

