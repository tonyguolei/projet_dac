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
INSERT INTO project VALUES (1, 2, 850.00, 'User-friendly Git interface', 'As you know, git is a powerful merging tool. However it is a huge pain if you''re not an expert user (meaning you must know 785 commands, do the good one at the right time with the correct arguments). For all the other normal humans, attempts to use it easily ends in catastrophic local/remote repository destruction.

I believe that in 2015, we are perfectly able to make a powerful merging tool that doesn''t require users to spend dozens hours of learning. This is why I intend to release a new software, that will wrap Git into a more user friendly graphical interface.

How will your money be used ?
- 10% of the pledges will go in the first prototyping of the software.
- 90% of the pledges will be used to cover productivity issues due to the current merging tool that is impossible to use.', '2015-01-23 17:21:18.0', '2015-02-26 00:00:00.0', 'git,repository,merge,branch', false, false);

INSERT INTO comment VALUES (10,3,1,'You don''t need so many commands ... Are you posting that because of what we discussed last time ? It was very simple stuff, though. To upload your code, just do branch->fetch->rebase->checkout->fetch->merge->commit->pull->push->pull request. If it has failed at any step, you irreversibly destroyed your local repository. Simply clone a new repository elsewhere, merge by hand, and restart the process until nobody else push something at the same time.

I can''t see how it could easier.','2015-01-24 08:17:53.0');

INSERT INTO comment VALUES (11,2,1,'This is why I currently work at 1AM with you guys. It takes 2 hours to merge 5min of code on daytime.','2015-01-24 15:17:53.0');

INSERT INTO project VALUES (2, 6, 25.00, 'Potato cake', 'Because internet love potatoes, and because everybody love cakes, I intend to give to the world an amazing potato cake !

What will I do with your money ?
- 25€ : make a 1kg potato cake, and release the recipe under GNU GPLv3.
- 50€ : organic version of the potato cake.
- 100€ : trying several potato cake recipes. Each one will of course be released under GNU GPLv3.
- 5000€ : I will rent a party hall, and people who gave 10€ or more will be invited !', '2015-01-07 17:21:18.0', '2015-03-05 23:59:59.0', 'potato,cake,swag', false, false);

INSERT INTO comment VALUES (1,2,2,'The cake is a lie !','2015-01-13 17:21:18.0');
INSERT INTO comment VALUES (2,3,2,'The cake is a lie !','2015-01-09 05:15:00.0');
INSERT INTO comment VALUES (3,4,2,'The cake is a lie !','2015-01-24 00:56:14.0');
INSERT INTO comment VALUES (4,5,2,'The cake is a lie !','2015-01-08 12:24:59.0');
INSERT INTO comment VALUES (5,6,2,'The cake is a lie !','2015-01-17 15:47:47.0');
INSERT INTO comment VALUES (7,8,2,'The cake is a lie !','2015-01-21 19:32:16.0');

INSERT INTO project VALUES (3, 8, 500.00, 'Improving my DAC team', 'I have been managing a team of 3rd year students for a project at Ensimag, and I can now tell that they aren''t ready at all for the professionnal work. It is not only for themselves that I''m asking your help : they might destroy the reputation of our school...

I intend to use your pledge to train them, so they can come back to an acceptable level.

What will I do with your money ?
- 500€ : teach the very basic stuff (not use Windows & its ISO-xxx charset, learn how to use git).
- 1000€ : teach what is a deadline and how to respect it : we don''t start a job 6h before it is due.
- 1500€ : 1 year of antidepressant for the chef.
- 2500€ : offers procedure to change their names (I still can''t spell/pronounce most of them : it would be more convenient if they had usable firsnames/lastnames)
- 5000€ : yoga courses for the project manager.', '2014-12-23 17:21:18.0', '2015-02-26 00:00:00.0', 'formation,teach', false, false);

INSERT INTO project VALUES (4, 3, 1000000.00, 'New iPhone/MacBook', 'I have been waiting for years to replace my old devices. I need your help to get the new revolutionnary versions !',
    '2014-12-23 17:21:18.0', '2015-02-26 00:00:00.0', 'iphone,macbook,apple,addict', false, false)
