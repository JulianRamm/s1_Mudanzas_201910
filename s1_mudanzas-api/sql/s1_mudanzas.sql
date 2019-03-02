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

insert into UsuarioEntity (id, login, password, nombre, apellido, correoElectronico, ciudadDeOrigen) values (100, 'luismigolondo', 'Qwerty2@', 'Luis Miguel', 'Gomez Londono', 'luismigolondo@gmail.com', 'Manizales');
insert into UsuarioEntity (id, login, password, nombre, apellido, correoElectronico, ciudadDeOrigen) values (200, 'juliaosorio', 'Qwerty3@', 'Julian', 'Osorio', 'juliano@gmail.com', 'Bogota');