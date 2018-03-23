package com.globant.bootcamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestValidarDatosPedido {
	WebDriver driver;

	public TestValidarDatosPedido() {

	}

	private static final Logger LOG;

	static {
		LOG = LogManager.getLogger(TestValidarDatosPedido.class);
	}

	@Test
	public void ValidarDatos() {

		driver.get("https://www.pedidosya.com/");
		HomePagePedidosYa PYhome = new HomePagePedidosYa(driver);
		PYhome.ClickearUruguay();

		PYHomePageUy pyUy = new PYHomePageUy(driver);

		PopUpConfirmarUbicacion PopUpUbicacion = pyUy.IngresarDatos();

		PYSugerenciasPage sugerencias_page = PopUpUbicacion.ConfirmarUbicacionPopUp();

		String precioEnSugerencias = sugerencias_page.tomarPrecio();

		PopUpAgregarPedido addPedido_page = sugerencias_page.ClickearPedido(); // Clickeo el pedido en las sugerencias
		// y creo el PO del PopUp de agregar el pedido

		RestaurantPage restaurant_page = addPedido_page.ConfirmarPedido(); //
		LOG.info("CONFIRMÓ PEDIDO");

		/** VALIDO LA DIRECCION Y EL PRECIO **/

		Assert.assertEquals(restaurant_page.tomarPrecio(), precioEnSugerencias);
		Assert.assertEquals(restaurant_page.tomarDireccion(), "Nicaragua 1600"); // Verifico la dirección y el precio

		PopUpModalLogin popup_login = restaurant_page.confirmarPedido(); // Le doy a continuar

		ConfirmarPedidoPage confirmar_pedido = popup_login.sendDataToLogin(); // ingreso el correo, pass, y me logueo

		Assert.assertEquals(confirmar_pedido.tomarNombre(), "Cameh"); // actual - expected

	}

	@BeforeMethod
	public void prepareTest() {

		LOG.info("Preparing");
		driver = new ChromeDriver();

	}

	@AfterMethod
	public void cleanup() {
		driver.quit();
	}

	@BeforeClass
	private void prepareClass() {
		LOG.info("Preparing class");

		WebDriverManager.chromedriver().setup();

	}

}// class
