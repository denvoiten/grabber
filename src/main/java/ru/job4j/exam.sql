CREATE TABLE company(
    id integer NOT NULL,
    name character varying,
    CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person(
    id integer NOT NULL,
    name character varying,
    company_id integer references company(id),
    CONSTRAINT person_pkey PRIMARY KEY (id)
);

INSERT INTO company(id, name) VALUES(1, 'IBM');
INSERT INTO company(id, name) VALUES(2, 'Meta');
INSERT INTO company(id, name) VALUES(3, 'VK');
INSERT INTO company(id, name) VALUES(4, 'Google');
INSERT INTO company(id, name) VALUES(5, 'Hooli');

INSERT INTO person(id, name, company_id) VALUES(1, 'Maxim', 1);
INSERT INTO person(id, name, company_id) VALUES(2, 'Sergey', 2);
INSERT INTO person(id, name, company_id) VALUES(3, 'Alex', 4);
INSERT INTO person(id, name, company_id) VALUES(4, 'Dmitry', 2);
INSERT INTO person(id, name, company_id) VALUES(5, 'Olga', 2);
INSERT INTO person(id, name, company_id) VALUES(6, 'Oleg', 3);
INSERT INTO person(id, name, company_id) VALUES(7, 'Anna', 1);
INSERT INTO person(id, name, company_id) VALUES(8, 'Sveta', 4);
INSERT INTO person(id, name, company_id) VALUES(9, 'Ivan', 3);
INSERT INTO person(id, name, company_id) VALUES(10, 'Kostya', 4);
INSERT INTO person(id, name, company_id) VALUES(11, 'Gilfoyle', 5);

SELECT p.name, c.name
FROM person p, company c
WHERE company_id != 5
AND p.company_id = c.id

SELECT c.name, COUNT(*)
FROM person p, company c
WHERE p.company_id = c.id
GROUP BY c.name
HAVING COUNT(*) = (SELECT max(persons)
FROM(SELECT company_id, count(*) persons FROM person
GROUP BY company_id) x);
