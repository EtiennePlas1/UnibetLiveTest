create sequence hibernate_sequence start with 1 increment by 1;
create table bet (id bigint not null, mise decimal(19,2), state integer, date timestamp, name integer, customer_id bigint, selection_id bigint not null, primary key (id));
create table customer (id bigint not null, balance decimal(19,2), login integer, primary key (id));
create table event (id bigint not null, name integer, start_date timestamp, primary key (id));
create table market (id bigint not null, name integer, event_id bigint, primary key (id));
create table selection (id bigint not null, current_odd decimal(19,2), name integer, result integer, state integer, market_id bigint, primary key (id));
alter table bet add constraint FKg2yf7bwf1vb6m323gvkncnbgd foreign key (customer_id) references customer;
alter table bet add constraint FK8dvxx0tp896m1m42caoi8bgwe foreign key (selection_id) references selection;
alter table market add constraint FKkdwvtgsitfuyvtchmmhgqyldj foreign key (event_id) references event;
alter table selection add constraint FKaonlls5dgbbvoglj7ic7xf10c foreign key (market_id) references market;
create sequence hibernate_sequence start with 1 increment by 1;
create table bet (id bigint not null, mise decimal(19,2), state integer, date timestamp, name integer, customer_id bigint, selection_id bigint not null, primary key (id));
create table customer (id bigint not null, balance decimal(19,2), login integer, primary key (id));
create table event (id bigint not null, name integer, start_date timestamp, primary key (id));
create table market (id bigint not null, name integer, event_id bigint, primary key (id));
create table selection (id bigint not null, current_odd decimal(19,2), name integer, result integer, state integer, market_id bigint, primary key (id));
alter table bet add constraint FKg2yf7bwf1vb6m323gvkncnbgd foreign key (customer_id) references customer;
alter table bet add constraint FK8dvxx0tp896m1m42caoi8bgwe foreign key (selection_id) references selection;
alter table market add constraint FKkdwvtgsitfuyvtchmmhgqyldj foreign key (event_id) references event;
alter table selection add constraint FKaonlls5dgbbvoglj7ic7xf10c foreign key (market_id) references market;
create sequence hibernate_sequence start with 1 increment by 1;
create table bet (id bigint not null, mise decimal(19,2), state integer, date timestamp, name integer, customer_id bigint, selection_id bigint not null, primary key (id));
create table customer (id bigint not null, balance decimal(19,2), login integer, primary key (id));
create table event (id bigint not null, name integer, start_date timestamp, primary key (id));
create table market (id bigint not null, name integer, event_id bigint, primary key (id));
create table selection (id bigint not null, current_odd decimal(19,2), name integer, result integer, state integer, market_id bigint, primary key (id));
alter table bet add constraint FKg2yf7bwf1vb6m323gvkncnbgd foreign key (customer_id) references customer;
alter table bet add constraint FK8dvxx0tp896m1m42caoi8bgwe foreign key (selection_id) references selection;
alter table market add constraint FKkdwvtgsitfuyvtchmmhgqyldj foreign key (event_id) references event;
alter table selection add constraint FKaonlls5dgbbvoglj7ic7xf10c foreign key (market_id) references market;
create sequence hibernate_sequence start with 1 increment by 1;
create table bet (id bigint not null, mise decimal(19,2), state integer, date timestamp, name integer, customer_id bigint, selection_id bigint not null, primary key (id));
create table customer (id bigint not null, balance decimal(19,2), login integer, primary key (id));
create table event (id bigint not null, name integer, start_date timestamp, primary key (id));
create table market (id bigint not null, name integer, event_id bigint, primary key (id));
create table selection (id bigint not null, current_odd decimal(19,2), name integer, result integer, state integer, market_id bigint, primary key (id));
alter table bet add constraint FKg2yf7bwf1vb6m323gvkncnbgd foreign key (customer_id) references customer;
alter table bet add constraint FK8dvxx0tp896m1m42caoi8bgwe foreign key (selection_id) references selection;
alter table market add constraint FKkdwvtgsitfuyvtchmmhgqyldj foreign key (event_id) references event;
alter table selection add constraint FKaonlls5dgbbvoglj7ic7xf10c foreign key (market_id) references market;
