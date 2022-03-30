create table if not exists subscribers(
    id int not null primary key AUTO_INCREMENT,
    username varchar(256) not null,
    password varchar(256) not null,
    email varchar(256) not null,
    email_sent bit DEFAULT 0,
    is_admin bit DEFAULT 0,
    created_at datetime,
    updated_at datetime
);