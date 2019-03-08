delete from AgendaEntity;
delete from CargaEntity;
delete from ConductorEntity;
delete from DiaEntity;
delete from DireccionEntity;
delete from OfertaEntity;
delete from ProveedorEntity;
delete from SubastaEntity;
delete from TarjetaDeCreditoEntity;
delete from UsuarioEntity;
delete from VehiculoEntity;
delete from ViajesEntity;

insert into UsuarioEntity (id, login, password, nombre, apellido, correoElectronico, ciudadDeOrigen) values (10001, 'luismigolondo', 'Qwerty2@', 'Luis Miguel', 'Gomez Londono', 'luismigolondo@gmail.com', 'Manizales');
insert into UsuarioEntity (id, login, password, nombre, apellido, correoElectronico, ciudadDeOrigen) values (10002, 'julianosorio', 'Qwerty3@', 'Julian', 'Osorio', 'juliano@gmail.com', 'Bogota');

insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, fechaVencimiento, usuario_id) values (10003, 'Tarjeta primaria', '123456789102', 951, '02/10/2020', 10001); 
insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, fechaVencimiento, usuario_id) values (10004, 'Tarjeta secundaria', '123456789103', 952, '02/10/2020', 10001); 
insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, fechaVencimiento, usuario_id) values (10005, 'Tarjeta primaria', '123456789104', 953, '02/10/2021', 10002); 
insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, fechaVencimiento, usuario_id) values (10006, 'Tarjeta secundaria', '123456789105', 954, '02/10/2021', 10002);

/*  @Andres Gonzalez   
    Para el query de subasta: pon a los usuarios de 
    id 10001, y 10002 en cada subasta... ponle dos 
    subastas a cada uno. Usuario se llama usuario_id*/

insert into SubastaEntity (id, valorFinal, valorInicial, usuario_id, proveedor_id) values (10001, 300000, 100000, 10001, 10001);
insert into SubastaEntity (id, valorFinal, valorInicial, usuario_id, proveedor_id) values (10002, 500000, 200000, 10001, 10002);
insert into SubastaEntity (id, valorFinal, valorInicial, usuario_id, proveedor_id) values (10003, 200000, 50000, 10002, 10002);


insert into OfertaEntity (id, valor, comentario, proveedor_id, subasta_id) values (10001, 150000, 10001, 10001);
insert into OfertaEntity (id, valor, comentario, proveedor_id, subasta_id) values (10002, 500000, 10002, 10002);
insert into OfertaEntity (id, valor, comentario, proveedor_id, subasta_id) values (10003, 400000, 10002, 10001);
insert into OfertaEntity (id, valor, comentario, proveedor_id, subasta_id) values (10004, 50000, 10001, 10002);



/*  @Julian Osorio   
    Para el query de carga: pon a los usuarios de 
    id 10001, y 10002 en cada subasta... ponle dos 
    cargas a cada uno. Usuario se llama usuario_id*/
