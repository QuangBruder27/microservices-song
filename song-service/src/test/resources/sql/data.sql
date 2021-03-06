
create table songs (id integer generated by default as identity (start with 1), artist varchar(255), label varchar(255), released integer not null, title varchar(255), primary key (id));

create table playlists (id integer generated by default as identity (start with 1), isPrivate boolean not null, name varchar(255), ownerId varchar(255), primary key (id))

create table playlist_song (playlist_id integer not null, song_id integer not null, primary key (playlist_id, song_id))

create table user (userId varchar(255) not null, firstName varchar(255), lastName varchar(255), password varchar(255), primary key (userId))

    INSERT INTO songs(id,title,artist,label,released) VALUES (1,'MacArthur Park','Richard Harris','Dunhill Records',1968);
    INSERT INTO songs(id,title,artist,label,released) VALUES (2,'Afternoon Delight','Starland Vocal Band','Windsong',1976);
    INSERT INTO songs(id,title,artist,label,released) VALUES (3,'Muskrat Love','Captain and Tennille','A&M',1976);
    INSERT INTO songs(id,title,artist,label,released) VALUES (4,'Sussudio','Phil Collins','Virgin',1985);
    INSERT INTO songs(id,title,artist,label,released) VALUES (5,'We Built This City','Starship','Grunt/RCA',1985);
    INSERT INTO songs(id,title,artist,label,released) VALUES (6,'Achy Breaky Heart','Billy Ray Cyrus','PolyGram Mercury',1992);
    INSERT INTO songs(id,title,artist,label,released) VALUES (7,'What’s Up?','4 Non Blondes','Interscope',1993);
    INSERT INTO songs(id,title,artist,label,released) VALUES (8,'Who Let the Dogs Out?','Baha Men','S-Curve',2000);
    INSERT INTO songs(id,title,artist,label,released) VALUES (9,'Hello My Humps','Black Eyed Peas','Universal Music',2005);


    INSERT INTO playlists VALUES (1, TRUE,'pl 1', 'mmuster');
    INSERT INTO playlists VALUES (2, FALSE,'pl 2', 'mmuster');
    INSERT INTO playlists VALUES (3, TRUE,'pl 3', 'eschuler');
    INSERT INTO playlists VALUES (4, FALSE,'pl 4', 'eschuler');

    INSERT INTO playlist_song VALUES (1,2)
    INSERT INTO playlist_song VALUES (1,3)
    INSERT INTO playlist_song VALUES (1,4)
    INSERT INTO playlist_song VALUES (2,5)
    INSERT INTO playlist_song VALUES (2,6)
    INSERT INTO playlist_song VALUES (3,1)
    INSERT INTO playlist_song VALUES (3,9)
    INSERT INTO playlist_song VALUES (4,7)
    INSERT INTO playlist_song VALUES (4,3)




