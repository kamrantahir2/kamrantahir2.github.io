-- We are going to create a table if it doesn't already exist

-- Spring converts all camelCase words to underscore so we need to follow accordingly meaning
-- in our Photo class our file_name field is called fileName which Spring will auto-convert
-- to file_name
create table if not exists photoz (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    file_name varchar(255),
    content_type varchar(255),
    data binary (100000000)
);