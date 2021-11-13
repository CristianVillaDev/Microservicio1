package com.ubosque.tienda;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ubosque.DAO.ProductoDAO;
import com.ubosque.DTO.Productos;

@RestController
@CrossOrigin
@ComponentScan(basePackages = { "com.ubosque.DAO" })
@RequestMapping("/productos")
public class ProductosController {

	@PostMapping("guardar")
	public int agregarProducto(@RequestParam("file") MultipartFile file) throws IOException {
		int contador2 = 0;

		if (file != null) {

			ProductoDAO p = new ProductoDAO();
			p.deleteProduct();

			// java.nio.file.Path productos =
			// Paths.get("webapps//tiendavirtual//WEB-INF//classes//documentosCSV");
			java.nio.file.Path productos = Paths.get("src//main//resources//documentosCSV");
			String ruta = productos.toFile().getAbsolutePath();

			System.out.println(ruta);

			file.transferTo(new File(ruta + "//" + file.getOriginalFilename()));
			BufferedReader csvReader = new BufferedReader(new FileReader(ruta + "//" + file.getOriginalFilename()));

			String row;
			int contador = 0;

			while ((row = csvReader.readLine()) != null) {

				ProductoDAO productoDAO = new ProductoDAO();

				if (contador == 0) {
					contador++;
				} else {
					contador2++;
					Productos producto = new Productos();

					String[] data = row.split(",");
					producto.setCodigoProducto(Integer.parseInt(data[0].replaceAll("\"", "")));
					producto.setNombreProducto(data[1].replaceAll("\"", ""));
					producto.setNitProveedor(Integer.parseInt(data[2].replaceAll("\"", "")));
					producto.setPrecioCompra(Double.parseDouble(data[3].replaceAll("\"", "")));
					producto.setIvaCompra(Double.parseDouble(data[4].replaceAll("\"", "")));
					producto.setPrecioVenta(Double.parseDouble(data[5].replaceAll("\"", "")));

					productoDAO.createProduct(producto);
				}
			}
			csvReader.close();
		} else {
			System.out.println("No se a cargado correctamente el archivo! ");
		}

		return contador2;
	}
}
