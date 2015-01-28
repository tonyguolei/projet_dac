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

-- Projects, Bonus, Comments, FUnds
INSERT INTO Project VALUES (1, 2, 850.00, 'User-friendly Git interface', 'As you know, git is a powerful merging tool. However it is a huge pain if you''re not an expert user (meaning you must know 785 commands, do the good one at the right time with the correct arguments). For all the other normal humans, attempts to use it easily ends in catastrophic local/remote repository destruction.

I believe that in 2015, we are perfectly able to make a powerful merging tool that doesn''t require users to spend dozens hours of learning. This is why I intend to release a new software, that will wrap Git into a more user friendly graphical interface.

How will your money be used ?
- 10% of the pledges will go in the first prototyping of the software.
- 90% of the pledges will be used to cover productivity issues due to the current merging tool that is impossible to use.', '2015-01-23 17:21:18.0', '2015-02-26 00:00:00.0', 'git,repository,merge,branch', false, false);

INSERT INTO Bonus VALUES (4,1,10.00,'A free license once it is completed.','You will receive a license of the software once it is completed.');

INSERT INTO Bonus VALUES (5,1,25.00,'Early access','You will be able to test the software during conception. We''ll listen to the feedback of the alpha-tester in order to make the best merging tool ever.');

INSERT INTO Comment VALUES (10,3,1,'You don''t need so many commands ... Are you posting that because of what we discussed last time ? It was very simple stuff, though. To upload your code, just do branch->fetch->rebase->checkout->fetch->merge->commit->pull->push->pull request. If it has failed at any step, you irreversibly destroyed your local repository. Simply clone a new repository elsewhere, merge by hand, and restart the process until nobody else push something at the same time.

I can''t see how it could easier.','2015-01-24 08:17:53.0');

INSERT INTO Comment VALUES (11,2,1,'This is why I currently work at 1AM with you guys. It takes 2 hours to merge 5min of code on daytime.','2015-01-24 15:17:53.0');

INSERT INTO Project VALUES (2, 6, 25.00, 'Potato cake', 'Because internet love potatoes, and because everybody love cakes, I intend to give to the world an amazing potato cake !

What will I do with your money ?
- 25€ : make a 1kg potato cake, and release the recipe under GNU GPLv3.
- 50€ : organic version of the potato cake.
- 100€ : trying several potato cake recipes. Each one will of course be released under GNU GPLv3.
- 5000€ : I will rent a party hall, and people who gave 10€ or more will be invited !', '2015-01-07 17:21:18.0', '2015-03-05 23:59:59.0', 'potato,cake,swag', false, false);

INSERT INTO Bonus VALUES (1,2,5.00,'Your name in the copyright','I will put your name as a co-author on all the GNU GPL licences of the recipes.');
INSERT INTO Bonus VALUES (2,2,50.00,'Receive a cake at home (France only)','I will bake you a cake of your choice and send it to you with Chronopost ! I can write special text on it for you.');
INSERT INTO Bonus VALUES (3,2,2000.00,'Cook of the day (France only)','I will go to your home, or anywhere you want me to go, and I will bake potato cakes for your party all the day !');

INSERT INTO Fund VALUES (2,8,2,50.00);
INSERT INTO Fund VALUES (3,5,2,100.00);

INSERT INTO Comment VALUES (3,2,2,'The cake is a lie !','2015-01-13 17:21:18.0');
INSERT INTO Comment VALUES (2,3,2,'The cake is a lie !','2015-01-09 05:15:00.0');
INSERT INTO Comment VALUES (6,4,2,'The cake is a lie !','2015-01-24 00:56:14.0');
INSERT INTO Comment VALUES (1,5,2,'The cake is a lie !','2015-01-08 12:24:59.0');
INSERT INTO Comment VALUES (4,7,2,'The cake is a lie !','2015-01-17 15:47:47.0');
INSERT INTO Comment VALUES (5,8,2,'The cake is a lie !','2015-01-21 19:32:16.0');

INSERT INTO Project VALUES (3, 8, 500.00, 'Improving my DAC team', 'I have been managing a team of 3rd year students for a project at Ensimag, and I can now tell that they aren''t ready at all for the professionnal work. It is not only for themselves that I''m asking your help : they might destroy the reputation of our school...

I intend to use your pledge to train them, so they can come back to an acceptable level.

What will I do with your money ?
- 500€ : teach the very basic stuff (not use Windows & its ISO-xxx charset, learn how to use git).
- 1000€ : teach what is a deadline and how to respect it : we don''t start a job 6h before it is due.
- 1500€ : 1 year of antidepressant for the chef.
- 2500€ : offers procedure to change their names (I still can''t spell/pronounce most of them : it would be more convenient if they had usable firsnames/lastnames)
- 5000€ : yoga courses for the project manager.', '2014-12-23 17:21:18.0', '2015-02-26 00:00:00.0', 'formation,teach', false, false);

INSERT INTO Project VALUES (4, 3, 1000000.00, 'New iPhone/MacBook', 'I have been waiting for years to replace my old devices. I need your help to get the new revolutionnary versions !',
    '2014-12-23 17:21:18.0', '2015-02-26 00:00:00.0', 'iphone,macbook,apple,addict', false, false);

INSERT INTO Comment VALUES (12,7,4,'Good luck bro ! Apple rocks !','2015-01-25 15:47:47.0');

INSERT INTO Bonus VALUES (6,4,100.00,'I will iFriend you on iFacebook','Sorry I can''t do better, there''s nothing in an Apple Store under this price.');

INSERT INTO Bonus VALUES (7,4,1000.00,'I will send you nice photos of them !','I would be so proud to get them ! The bare minimum for me is to show them to people who helped me to get it.');

INSERT INTO Bonus VALUES (8,4,100000.00,'Right to pet them (see conditions).','You will be allowed to touch them. Traveling cost aren''t included, and you must present an ICertificate that you are Mac-compatible (I can''t risk that you damage the screen).');

INSERT INTO Fund VALUES (1,7,4,28.00);

-- Notifications
INSERT INTO Notification VALUES (1,2,1,'Your windows VM has just made a blue screen of death ! (IRQL_NOT_LESS_OR_EQUAL)','2015-01-26 11:22:33.0',false);

-- Memorise Project
INSERT INTO MemoriseProject VALUES (1,7,4);
INSERT INTO MemoriseProject VALUES (2,7,2);

INSERT INTO MemoriseProject VALUES (3,2,2);

INSERT INTO MemoriseProject VALUES (4,4,2);
INSERT INTO MemoriseProject VALUES (5,5,2);

INSERT INTO Project VALUES (5, 4, 15000.00, 'The cat car', '## The concept
Climate change is threatening our world. But there is a solution: *the cat car*. The world’s first animal-powered vehicle!

My original prototype was hunger based. But sometimes, cats aren’t hungry! My new model uses a rear-fixed dog system. But dogs cost money. Your money. By donating to this project, you’ll be like an investor, except without taking all my property!

All aboard the cat car! And all aboard helping our nation’s global warming.

## My promotional video
[](https://www.youtube.com/watch?v=4MhBJ0APIC0)
', '2015-01-28 11:46:00', '2015-03-01 00:00:00.0', 'cat,car', true, false);

INSERT INTO Bonus VALUES (9, 5, 5.00, 'A thank you e-mail', 'A customized mail with your name in it which says thank you!');
INSERT INTO Bonus VALUES (10, 5, 50.00, 'A t-shirt', 'A cat-car t-shirt!');
INSERT INTO Bonus VALUES (11, 5, 100.00, 'A thank you e-mail and a t-shirt', 'Why choose when you can get both?');

INSERT INTO Fund VALUES (4, 4, 5, 1500.00);

INSERT INTO PrivateMessage VALUES (1, 1, 2, '#### Welcome to SelfStarter!

Thank you for creating your account! What to do now? Well you could start by [checking the projects list](index.jsp?nav=projects) to see if you find anything likeable and start funding it!

If you want to get into the real thing, you can also [create a new project](index.jsp?nav=createproject): if it looks good enough, people will send you the money you need to realize it!', false);
INSERT INTO PrivateMessage VALUES (2, 1, 3, '#### Welcome to SelfStarter!

Thank you for creating your account! What to do now? Well you could start by [checking the projects list](index.jsp?nav=projects) to see if you find anything likeable and start funding it!

If you want to get into the real thing, you can also [create a new project](index.jsp?nav=createproject): if it looks good enough, people will send you the money you need to realize it!', false);
INSERT INTO PrivateMessage VALUES (3, 1, 4, '#### Welcome to SelfStarter!

Thank you for creating your account! What to do now? Well you could start by [checking the projects list](index.jsp?nav=projects) to see if you find anything likeable and start funding it!

If you want to get into the real thing, you can also [create a new project](index.jsp?nav=createproject): if it looks good enough, people will send you the money you need to realize it!', false);
INSERT INTO PrivateMessage VALUES (4, 1, 5, '#### Welcome to SelfStarter!

Thank you for creating your account! What to do now? Well you could start by [checking the projects list](index.jsp?nav=projects) to see if you find anything likeable and start funding it!

If you want to get into the real thing, you can also [create a new project](index.jsp?nav=createproject): if it looks good enough, people will send you the money you need to realize it!', false);
INSERT INTO PrivateMessage VALUES (5, 1, 6, '#### Welcome to SelfStarter!

Thank you for creating your account! What to do now? Well you could start by [checking the projects list](index.jsp?nav=projects) to see if you find anything likeable and start funding it!

If you want to get into the real thing, you can also [create a new project](index.jsp?nav=createproject): if it looks good enough, people will send you the money you need to realize it!', false);
INSERT INTO PrivateMessage VALUES (6, 1, 7, '#### Welcome to SelfStarter!

Thank you for creating your account! What to do now? Well you could start by [checking the projects list](index.jsp?nav=projects) to see if you find anything likeable and start funding it!

If you want to get into the real thing, you can also [create a new project](index.jsp?nav=createproject): if it looks good enough, people will send you the money you need to realize it!', false);
INSERT INTO PrivateMessage VALUES (7, 1, 8, '#### Welcome to SelfStarter!

Thank you for creating your account! What to do now? Well you could start by [checking the projects list](index.jsp?nav=projects) to see if you find anything likeable and start funding it!

If you want to get into the real thing, you can also [create a new project](index.jsp?nav=createproject): if it looks good enough, people will send you the money you need to realize it!', false);
