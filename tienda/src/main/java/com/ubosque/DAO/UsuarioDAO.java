package com.ubosque.DAO;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.internal.bulk.DeleteRequest;
import com.ubosque.DTO.Usuarios;

public class UsuarioDAO {

	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> usuarios;
	ConnectionString connectionString;
	MongoClientSettings settings;

	public UsuarioDAO() {
		try {
			connectionString = new ConnectionString(
					"mongodb+srv://admin:admin@cluster0.ykb33.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
			settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
			mongoClient = MongoClients.create(settings);
			database = mongoClient.getDatabase("db_usuarios");

			usuarios = database.getCollection("usuarios");
			System.out.println("Conexión exitosa");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void cerrar() {
		try {
			mongoClient.close();

			System.out.println("Conexión cerrada");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public boolean crear(Usuarios usuario) {
		try {

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("cedulaUsuario", usuario.getCedulaUsuario());

			ArrayList<Document> docusuarios = usuarios.find(whereQuery).into(new ArrayList<>());

			if (docusuarios.size() == 0 || docusuarios.isEmpty()) {
				Document documento = new Document("_id", new ObjectId());
				documento.append("cedulaUsuario", usuario.getCedulaUsuario());
				documento.append("emailUsuario", usuario.getEmailUsuario());
				documento.append("nombreUsuario", usuario.getNombreUsuario());
				documento.append("password", usuario.getPassword());
				documento.append("usuario", usuario.getUsuario());

				usuarios.insertOne(documento);
				System.out.println("Usuario Creado");
			} else {
				System.out.println("El usuario ya existe");
				System.out.println("Usuario No Creado");
				this.cerrar();
				return false;
			}

			this.cerrar();

		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
			return false;
		}
		return true;
	}

	public boolean update(Usuarios usuario, int cedula) {
		try {
			Document documento = new Document();
			documento.append("cedulaUsuario", usuario.getCedulaUsuario());
			documento.append("emailUsuario", usuario.getEmailUsuario());
			documento.append("nombreUsuario", usuario.getNombreUsuario());
			documento.append("password", usuario.getPassword());
			documento.append("usuario", usuario.getUsuario());

			Document filtro = new Document("cedulaUsuario", cedula);
			UpdateResult estado = usuarios.replaceOne(filtro, documento);

			this.cerrar();
			System.out.print("Usuario Creado");
		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
			return false;
		}
		return true;
	}

	public boolean delete(int cedula_usuario) {
		try {
			DeleteResult estado = usuarios.deleteOne(new Document("cedulaUsuario", cedula_usuario));

			if (estado != null) {
				System.out.print("Usuario Borrado");
			} else {
				System.out.print("Usuario No Borrado");
			}

			this.cerrar();
			System.out.print("Usuario Creado");
		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
			return false;
		}
		return true;
	}

	public ArrayList<Usuarios> listarUsuarios() {
		ArrayList<Usuarios> ListaUsuarios = new ArrayList<Usuarios>();
		try {
			ArrayList<Document> docusuarios = usuarios.find().into(new ArrayList<>());
			for (Document documento : docusuarios) {
				Usuarios usuario = new Usuarios();
				usuario.setCedulaUsuario(documento.getInteger("cedulaUsuario"));
				usuario.setEmailUsuario(documento.getString("emailUsuario"));
				usuario.setNombreUsuario(documento.getString("nombreUsuario"));
				usuario.setPassword(documento.getString("password"));
				usuario.setUsuario(documento.getString("usuario"));

				ListaUsuarios.add(usuario);
			}
			System.out.print("Listando Usuarios");
			this.cerrar();
		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
		}
		return ListaUsuarios;
	}

	public ArrayList<Usuarios> listarUsuario(int cedula_usuario) {
		ArrayList<Usuarios> ListaUsuario = new ArrayList<Usuarios>();
		try {

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("cedulaUsuario", cedula_usuario);

			ArrayList<Document> docusuarios = usuarios.find(whereQuery).into(new ArrayList<>());
			for (Document documento : docusuarios) {
				Usuarios usuario = new Usuarios();
				usuario.setCedulaUsuario(documento.getInteger("cedulaUsuario"));
				usuario.setEmailUsuario(documento.getString("emailUsuario"));
				usuario.setNombreUsuario(documento.getString("nombreUsuario"));
				usuario.setPassword(documento.getString("password"));
				usuario.setUsuario(documento.getString("usuario"));

				ListaUsuario.add(usuario);
			}
			System.out.print("Listando Usuarios");
			this.cerrar();
		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
		}
		return ListaUsuario;
	}

	public int login(Usuarios usuario) {

		int cedula = 0;

		try {
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("usuario", usuario.getUsuario());
			whereQuery.put("password", usuario.getPassword());

			ArrayList<Document> docusuarios = usuarios.find(whereQuery).limit(1).into(new ArrayList<>());
			for (Document documento : docusuarios) {
				cedula = documento.getInteger("cedulaUsuario");
			}

			System.out.println(usuario.getUsuario());
			System.out.println(usuario.getPassword());
			if (usuario.getUsuario().equals("admininicial") && usuario.getPassword().equals("admin123456")) {

				return 1;
			}

			if (docusuarios.size() == 0) {
				return 0;
			}

			this.cerrar();

			System.out.println(docusuarios);
			System.out.println(docusuarios.size());
		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
			return 0;
		}

		return cedula;
	}
}
