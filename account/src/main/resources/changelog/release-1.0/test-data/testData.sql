insert into users (fathers_name, first_name, last_name, type) values
    ('adminich', 'admin', 'adminenko', 'TEACHER');
insert into users (fathers_name, first_name, last_name, type) values
    ('userich', 'user', 'userenko', 'TEACHER');

insert into account (active, email, password, username, user_id)
    select true, 'admin@example.com', '$2a$10$xByp2q7ImdSvb1w/sOuUK.2g.uSBwjZSg2RLy94SzE2iM.ezBROOW', 'admin', users.user_id
    from users where first_name = 'admin';
insert into account (active, email, password, username, user_id)
    select true, 'user@example.com', '$2a$10$xByp2q7ImdSvb1w/sOuUK.2g.uSBwjZSg2RLy94SzE2iM.ezBROOW', 'user', users.user_id
    from users where first_name = 'user';

insert into account_role (account_id, role_id)
    select account.account_id, role.role_id
    from role, account where role.name = 'ADMIN' and account.username = 'admin';
insert into account_role (account_id, role_id)
    select account.account_id, role.role_id
    from role, account where role.name = 'USER' and account.username = 'user';