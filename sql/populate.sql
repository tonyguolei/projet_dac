-- Add some test values to the database

-- Admin account
INSERT INTO User VALUES (1, "admin@dac.com", "dac", DEFAULT, DEFAULT, DEFAULT, 1);

-- User accounts
INSERT INTO User VALUES(2,'sauluelv@ensimag.grenoble-inp.fr','sauluelv',47.8,0,0,0);
INSERT INTO User VALUES(3,'posva13@gmail.com','posva',13,0,0,0);
INSERT INTO User VALUES(4,'simon@jacquin.me','simon',16,0,0,0);
INSERT INTO User VALUES(5,'tonyguolei@gmail.com','tonyguolei',123,0,0,0);
INSERT INTO User VALUES(6,'mugnier.benjamin@gmail.com','mugnier',55,0,0,0);
INSERT INTO User VALUES(7,'guillaume28.perrin@gmail.com','guillaume28',28,0,0,0);
INSERT INTO User VALUES(8,'gnuforfreedom@gmail.com','/dev/random',852,0,0,0);

-- Projects
--INSERT INTO project VALUES (0, 1, 850.00, 'User-friendly Git interface', 'As you know, git is a powerful merging tool. However it is a huge pain if you''re not an expert user (meaning you must know 785 commands, do the good one at the right time with the correct arguments). For all the other normal humans, attempts to use it easily ends in catastrophic local/remote repository destruction.
--
--I believe that in 2015, we are perfectly able to make a powerful merging tool that doesn''t require users to spend dozens hours of learning. This is why I intend to release a new software, that will wrap Git into a more user friendly graphical interface.
--
--How will your money be used ? 
--- 10% of the pledges will go in the first prototyping of the software.
--- 90% of the pledges will be used to cover productivity issues due to the current merging tool that is impossible to use.', '2015-01-23 17:21:18.0', '2015-02-26 00:00:00.0', 'git,repository,merge,branch', false, false);