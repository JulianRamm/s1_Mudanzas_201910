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

/*  @Andres Gonzalez   
    Para el query de subasta: pon a los usuarios de 
    id 10001, y 10002 en cada subasta... ponle dos
    subastas a cada uno. Usuario se llama usuario_id
 @Julian Osorio   
    Para el query de carga: pon a los usuarios de 
    id 10001, y 10002 en cada subasta... ponle dos 
    subastas a cada uno. Usuario se llama usuario_id*/

insert into SubastaEntity (id, valorFinal, valorInicial, usuario_id, proveedor_id) values (11000, 300000, 100000, 10001, 10003);
insert into SubastaEntity (id, valorFinal, valorInicial, usuario_id, proveedor_id) values (12000, 500000, 200000, 10001, 10003);
insert into SubastaEntity (id, valorFinal, valorInicial, usuario_id, proveedor_id) values (13000, 200000, 50000, 10002, 10003);

insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, titularCuenta, fechaVencimiento, usuario_id) values (10003, 'Tarjeta primaria', '123456789102', 951, 'Luis Miguel Gomez L', '02/10/2020', 10001); 
insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, titularCuenta, fechaVencimiento, usuario_id) values (10004, 'Tarjeta secundaria', '123456789103', 952, 'Luis Miguel Gomez L', '02/10/2020', 10001); 
insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, titularCuenta, fechaVencimiento, usuario_id) values (10005, 'Tarjeta primaria', '123456789104', 953, 'Julian Osorio', '02/10/2021', 10002); 
insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, titularCuenta, fechaVencimiento, usuario_id) values (10006, 'Tarjeta secundaria', '123456789105', 954, 'Julian Osorio', '02/10/2021', 10002);

insert into ViajesEntity (id, conductor_id, lugarSalida, lugarLlegada, tiempo, gastoGasolina, clima, horaPartida, horaLlegada) values(10001, 10001, 'medellin', 'bogota', 14, 69, 'Templado','2008-01-01 00:00:01', '2008-01-01 14:00:01' );
insert into ViajesEntity (id, conductor_id, lugarSalida, lugarLlegada, tiempo, gastoGasolina, clima, horaPartida, horaLlegada) values(10002, 10002, 'bogota', 'medellin', 15, 420, 'Templado','2069-01-01 00:00:01', '2069-01-01 15:00:01' );

insert into OfertaEntity (id, valor, comentario, proveedor_id, subasta_id) values (14000, 150000, 'Comentario 1', 10003, 10001);
insert into OfertaEntity (id, valor, comentario, proveedor_id, subasta_id) values (15000, 500000,'Comentario 2', 10003, 10002);
insert into OfertaEntity (id, valor, comentario, proveedor_id, subasta_id) values (16000, 400000,'Comentario 3', 10003, 10001);
insert into OfertaEntity (id, valor, comentario, proveedor_id, subasta_id) values (17000, 50000, 'Comentario 4',10003, 10002);

insert into CargaEntity (id, viajes_id, usuario_id, datosEnvio, volumen, lugarSalida, lugarLlegada, fechaEstimadaLlegada, fechaEnvio, observaciones) values(10001, 10001, 10001, 'todo es rosado', 69, 'la casa de la esquina', 'la casa de la otra esquina','01/03/2005','01/03/2005', 'esta es una observacion');
insert into CargaEntity (id, viajes_id, usuario_id, datosEnvio, volumen, lugarSalida, lugarLlegada, fechaEstimadaLlegada, fechaEnvio, observaciones) values(10002, 10001, 10001, 'haber', 69, 'allá', 'la casa de la otra esquina','01/03/2006','01/03/2006', 'observacion');
insert into CargaEntity (id, viajes_id, usuario_id, datosEnvio, volumen, lugarSalida, lugarLlegada, fechaEstimadaLlegada, fechaEnvio, observaciones) values(10003, 10002, 10002, 'U are gay', 420, 'En el puente', 'Debajo del puente','01/03/2007','01/03/2007', 'cuidadito papá');
insert into CargaEntity (id, viajes_id, usuario_id, datosEnvio, volumen, lugarSalida, lugarLlegada, fechaEstimadaLlegada, fechaEnvio, observaciones) values(10004, 10002, 10002, 'no u', 420, 'En mi casa', 'A la casa a donde me paso','01/03/2008','01/03/2008', 'el contenido son mis cosas');

insert into ProveedorEntity(id, login, password, nombre, logotipo, ciudadOrigen, correoElectronico, telefono, numeroVehiculos, dineroDisponible, calificacion) values (10003, 'df.machado', 'Qwerty2@', 'Daniel Inc', 'png', 'Bogota', 'dfmachado@gmail.com', '7212343', 4 , 5000000, 5);


insert into SubastaEntity(id, valorInicial, valorFinal, proveedor_id, usuario_id) values (10004, 15000, 16000, 10003, 10001);
insert into OfertaEntity(id, comentario, valor, subasta_id, proveedor_id) values ( 10005, 'Oferta Proveedor 1', 150, 10004, 10003);