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
insert into UsuarioEntity (id, login, password, nombre, apellido, correoElectronico, ciudadDeOrigen) values (10002, 'juliaosorio', 'Qwerty3@', 'Julian', 'Osorio', 'juliano@gmail.com', 'Bogota');

insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, titularCuenta, fechaVencimiento, usuario_id) values (10003, 'Tarjeta primaria', '123456789102', 951, 'Luis Miguel Gomez L', '02/10/2020', 10001); 
insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, titularCuenta, fechaVencimiento, usuario_id) values (10004, 'Tarjeta secundaria', '123456789103', 952, 'Luis Miguel Gomez L', '02/10/2020', 10001); 
insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, titularCuenta, fechaVencimiento, usuario_id) values (10005, 'Tarjeta primaria', '123456789104', 953, 'Julian Osorio', '02/10/2021', 10002); 
insert into TarjetaDeCreditoEntity (id, nombreTarjeta, numeroSerial, codigoSeguridad, titularCuenta, fechaVencimiento, usuario_id) values (10006, 'Tarjeta secundaria', '123456789105', 954, 'Julian Osorio', '02/10/2021', 10002);

insert into ViajesEntity (id, conductor_id, lugarSalida, lugarLlegada, tiempo, gastoGasolina, clima, horaPartida, horaLlegada) values(10001, 10001, 'medellin', 'bogota', 14, 69, 'Templado','2008-01-01 00:00:01', '2008-01-01 14:00:01' );
insert into ViajesEntity (id, conductor_id, lugarSalida, lugarLlegada, tiempo, gastoGasolina, clima, horaPartida, horaLlegada) values(10002, 10002, 'bogota', 'medellin', 15, 420, 'Templado','2069-01-01 00:00:01', '2069-01-01 15:00:01' );

insert into CargaEntity (id, viajes_id, usuario_id, datosEnvio, volumen, lugarSalida, lugarLlegada, fechaEstimadaLlegada, fechaEnvio, observaciones) values(10001, 10001, 10001, 'todo es rosado', 69, 'la casa de la esquina', 'la casa de la otra esquina','01/03/2005','01/03/2005', 'esta es una observacion');
insert into CargaEntity (id, viajes_id, usuario_id, datosEnvio, volumen, lugarSalida, lugarLlegada, fechaEstimadaLlegada, fechaEnvio, observaciones) values(10002, 10001, 10001, 'haber', 69, 'allá', 'la casa de la otra esquina','01/03/2006','01/03/2006', 'observacion');
insert into CargaEntity (id, viajes_id, usuario_id, datosEnvio, volumen, lugarSalida, lugarLlegada, fechaEstimadaLlegada, fechaEnvio, observaciones) values(10003, 10002, 10002, 'U are gay', 420, 'En el puente', 'Debajo del puente','01/03/2007','01/03/2007', 'cuidadito papá');
insert into CargaEntity (id, viajes_id, usuario_id, datosEnvio, volumen, lugarSalida, lugarLlegada, fechaEstimadaLlegada, fechaEnvio, observaciones) values(10004, 10002, 10002, 'no u', 420, 'En mi casa', 'A la casa a donde me paso','01/03/2008','01/03/2008', 'el contenido son mis cosas');