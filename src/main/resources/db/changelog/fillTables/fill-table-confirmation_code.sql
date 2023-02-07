--liquibase formatted sql
--changeset liquibase:60b5af61-e62e-4c49-9885-07cf87bc7def


INSERT INTO `confirmation_code` VALUES
        (1,'0DJ5VW;E',1), (2,'WP`w7\"]T',2), (3,'0d*S]uRv',3),
        (4,'3^5^D _B',4),(5,'w>(u=NtV',5),(6,'{tI/F R^',6),
        (7,'lHUzVJ(.',7),(8,'H{Msmu6$',8),(9,'DNBFeDF>',9),
        (10,'!=tM8&h[',10),(11,'lY#z-D/*',11),(12,'jk*P;?nS',12),
        (13,'tJNSp7YW',13),(14,'P7$X\%A[g',14),(15,'Hq\'eRan4',15),
        ( 16,'3A::Q\\2X',16), (17,'. Q&WvUb',17), (18,'<W^~=YtY',18),
        (19,'|Xd+0Z7u',19),(20, 'jU[\'tQMI',20),(21, '\}o@s?fb\'',21),
        (22, 'rLv|_z`@', 22), (23,'Kff4dbpG',23), (24,'CVy/NLo/',24),
        (25,'\,K\,pgL\"\"', 25), (26,'Ak/\'.tRx',26), (27,'lvMmVggJ',27),
        (28,'dZ<IkF7f',28),(29,'#>0>(5{v',29),(30,'_=|mRuLF',30),
        (31,'\\mhv~+H9',31),(32,'d/7*oUz:',32),(33,'-w/y2fi,',33),
        (34, 'vU#~\(_U4', 34),(35,'IePwSdl!', 35),
        (36,'\>J\.>GcUk', 36);


--rollback DELETE FROM confirmation_code  WHERE id BETWEEN 1 AND 36;