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
import com.ubosque.DTO.Productos;

public class ProductoDAO {

	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> productos;
	MongoCollection<Document> proveedores;
	ConnectionString connectionString;
	MongoClientSettings settings;

	public ProductoDAO() {
		try {
			connectionString = new ConnectionString(
					"mongodb+srv://admin:admin@cluster0.ykb33.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
			settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
			mongoClient = MongoClients.create(settings);
			database = mongoClient.getDatabase("db_productos");

			productos = database.getCollection("productos");
			proveedores = database.getCollection("proveedores");

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

	public ArrayList<Productos> findProducto(int codigo) {
		ArrayList<Productos> listaProductos = new ArrayList<>();
		try {

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("codigo_producto", codigo);

			ArrayList<Document> docProducto = productos.find(whereQuery).into(new ArrayList<>());

			for (Document i : docProducto) {
				Productos producto = new Productos();
				producto.setCodigoProducto(i.getInteger("codigo_producto"));
				producto.setIvaCompra(i.getDouble("ivacompra"));
				producto.setNitProveedor(i.getInteger("nitproveedor"));
				producto.setNombreProducto(i.getString("nombre_producto"));
				producto.setPrecioCompra(i.getDouble("precio_compra"));
				producto.setPrecioVenta(i.getDouble("precio_venta"));

				listaProductos.add(producto);
			}

			return listaProductos;
		} catch (Exception e) {
			e.getMessage();
		}
		return listaProductos;
	}

	public void createProduct(Productos producto) {

		try {

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("nitproveedor", producto.getNitProveedor());

			ArrayList<Document> docproveedores = proveedores.find(whereQuery).into(new ArrayList<>());

			if (docproveedores.isEmpty() || docproveedores.size() == 0) {

				System.out.println(
						"El producto " + producto.getNombreProducto() + " , no coinside con proveedores cargados");

			} else {

				Document documento = new Document("_id", new ObjectId());

				documento.append("codigo_producto", producto.getCodigoProducto());
				documento.append("ivacompra", producto.getIvaCompra());
				documento.append("nitproveedor", producto.getNitProveedor());
				documento.append("nombre_producto", producto.getNombreProducto());
				documento.append("precio_compra", producto.getPrecioCompra());
				documento.append("precio_venta", producto.getPrecioVenta());

				productos.insertOne(documento);
			}

			this.cerrar();

		} catch (Exception e) {
			this.cerrar();
			e.getMessage();

		}
	}

	public void deleteProduct() {
		try {

			productos.drop();
			this.cerrar();

		} catch (Exception e) {

			e.getMessage();

		}
	}
}
