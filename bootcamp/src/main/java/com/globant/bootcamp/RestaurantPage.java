package com.globant.bootcamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class RestaurantPage {

	@FindBy(how = How.XPATH, using = "//div[@class=\"price total-price\"]")
	WebElement lblTotalPrice;

	@FindBy(how = How.ID, using = "order")
	WebElement btnOrder;

	WebDriver driver;

	public RestaurantPage(WebDriver driver) {

		this.driver = driver;

		PageFactory.initElements(driver, this);

		if (!SeleniumUtils.isPresentByPresenceOfElement(btnOrder, driver)) {
			throw new IllegalStateException("Page did not load.");
		}
	}

	public String tomarPrecio() {

		String precioPedidoAgregado = driver.findElement(By.xpath("//div[@class=\"price total-price\"]"))
				.getAttribute("innerHTML");

		return precioPedidoAgregado;

	}

	public String tomarDireccion() {
		String DireccionPedido = driver.findElement(By.xpath("//span[@title=\"Nicaragua 1600\"]"))
				.getAttribute("innerText");
		String direccion = DireccionPedido.replace("Delivery para ", "");

		System.out.println("La direccion es: " + direccion);
		return direccion;
	}

	public PopUpModalLogin confirmarPedido() {

		btnOrder.click();

		WebElement loginIframe = SeleniumUtils.isPresentUsingPresenceWE(By.xpath("//div[@class=\"tcontent\"]/iframe"),
				driver);
		driver.switchTo().frame(loginIframe);

		return new PopUpModalLogin(driver);
	}

}// class