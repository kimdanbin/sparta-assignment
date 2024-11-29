create table user (
                      id bigint not null auto_increment,
                      email varchar(255),
                      name varchar(255),
                      created_at datetime(6),
                      modified_at datetime(6),
                      primary key (id)
);

create table currency (
                          id bigint not null auto_increment,
                          currency_name varchar(255),
                          exchange_rate decimal(38,2),
                          symbol varchar(255),
                          created_at datetime(6),
                          modified_at datetime(6),
                          primary key (id)
);

create table exchange (
                          id bigint not null auto_increment,
                          user_id bigint,
                          to_currency_id bigint,
                          amount_after_exchange decimal(38,2),
                          amount_in_krw decimal(38,2),
                          status varchar(255),
                          created_at datetime(6),
                          modified_at datetime(6),
                          primary key (id)
)