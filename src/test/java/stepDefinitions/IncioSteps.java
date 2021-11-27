package stepDefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IncioSteps {

     WebDriver driver;

    static class GlobalVals{
        static Double Dolares1;
        static Double Soles1;
        static Double Dolares2 ;
        static Double Soles2;
    }

   @Given("iniciar chrome browser")
    public void iniciar_chrome_browser() {

       System.setProperty("webdriver.chrome.driver","D://AUTOMATIZACIONInteliJ//Drivers//chromedriver.exe");
       driver=new ChromeDriver();


 }

    @When("iniciar web requerida")
    public void inicio_con_llenado_informacio() {

        String ImagenInicio ="/html/body/div[1]/header/div/a/img";

        System.out.println("-----------------------------------------------'");
        System.out.println("Iniciando Web");
        System.out.println("-----------------------------------------------'");

        driver.get("https://www.perurail.com/");
        driver.manage().window().maximize();


        boolean status = driver.findElement(By.xpath(ImagenInicio)).isDisplayed();
        Assert.assertEquals(true ,status);

        System.out.println("-----------------------------------------------'");
        System.out.println("Fin de inicio Web");
        System.out.println("-----------------------------------------------'");
 }

    @Then("Ingresamos información de destino {string} ruta {string} seleccionar Tren {string} y solo ida {string} fecha de salida {string} fecha de retorno {string}")
    public void ingresamos_información_de_destino_ruta_seleccionar_tren_y_solo_ida_fecha_de_salida_fecha_de_retorno(String strDestino, String strRuta, String strTren, String strSoloIda, String strfecSalida, String strRetorno) throws ParseException {
        String strXpathDestino ="//*[@id='destinoSelect']";
        String strXpathRuta="//*[@id='rutaSelect']";
        String strXpathTren="//*[@id='cbTrenSelect']";
        String StrIdSalida="salida";
        //*[@id="ui-datepicker-div"]/table/tbody/tr[5]/td[2]/a
        //*[@id="ui-datepicker-div"]/table/tbody/tr[4]/td[7]/a
        String strIdRegreso="regreso";
        String strXpathRtrip="//*[@id='radioset']/div[2]";

        System.out.println("-----------------------------------------------'");
        System.out.println("ingresa Destino"+strDestino);
        System.out.println("-----------------------------------------------'");

        driver.findElement(By.xpath(strXpathDestino+"/option[text()='"+strDestino+"']")).click();

        System.out.println("-----------------------------------------------'");
        System.out.println("ingresa Ruta"+strRuta);
        System.out.println("-----------------------------------------------'");
        driver.findElement(By.xpath(strXpathRuta+"/option[text()='"+strRuta+"']")).click();

        if ( !strTren.trim().equals("Ignore")) {
            System.out.println("-----------------------------------------------'");
            System.out.println("ingresa Tren"+strTren);
            System.out.println("-----------------------------------------------'");
            driver.findElement(By.xpath(strXpathTren+"/option[text()='"+strTren+"']")).click();
        }
        if (strSoloIda.equals("SI")){
            System.out.println("-----------------------------------------------'");
            System.out.println("Selecciona solo Ida");
            System.out.println("-----------------------------------------------'");
            driver.findElement(By.xpath(strXpathRtrip)).click();
        }

        System.out.println("-----------------------------------------------'");
        System.out.println("ingresa Fecha Salida"+strfecSalida);
        System.out.println("-----------------------------------------------'");

        String date_dd_MM_yyyy[] = (strfecSalida.split(" ")[0]).split("/");


        driver.findElement(By.id(StrIdSalida)).click();

        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Date dateSalida = formato.parse(strfecSalida);




        int difA = dateSalida.getYear() - date.getYear();
        int difM = difA * 12 + dateSalida.getMonth() - date.getMonth();

        if (strTren.equals("Andean Explorer, A Belmond Train")){
            difM=difM-1;

        }


        for (int i = 0; i < difM; i++){
            driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[2]")).click();
        }

        Integer intMes = Integer.parseInt(date_dd_MM_yyyy[1]);
        intMes=intMes-1;

        driver.findElement(By.xpath("//td[contains(@data-month,"+intMes+")]/*[contains(text(),"+date_dd_MM_yyyy[0]+")]")).click();


        System.out.println("-----------------------------------------------'");

          if (!strRetorno.trim().equals("Ignore")) {
              System.out.println("-----------------------------------------------'");
              System.out.println("ingresa Fecha Retorno"+strRetorno);
              System.out.println("-----------------------------------------------'");
              driver.findElement(By.id(strIdRegreso)).click();

              String date_dd_MM_yyyyR[] = (strRetorno.split(" ")[0]).split("/");
              Date dateRetorno = formato.parse(strfecSalida);

              int difA1 = dateRetorno.getYear() - dateSalida.getYear();
              int difM1 = difA * 12 + dateRetorno.getMonth() - dateSalida.getMonth();

              for (int i = 0; i < difM; i++){
                  driver.findElement(By.xpath("//*[@id='ui-datepicker-div']/div/a[2]")).click();
              }

              Integer intMes1 = Integer.parseInt(date_dd_MM_yyyyR[1]);
              intMes1=intMes1-1;

              driver.findElement(By.xpath("//td[contains(@data-month,"+intMes1+")]/*[contains(text(),"+date_dd_MM_yyyyR[0]+")]")).click();



          }

    }

    @Then("selecciona boton Buscar")
    public void selecciona_boton_Buscar() {
        driver.findElement(By.id("btn_search")).click();
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("imagenIda")));
        boolean status = driver.findElement(By.id("imagenIda")).isDisplayed();
        Assert.assertEquals(true ,status);
    }

    @Then("Seleccioanar Boletos")
    public void Seleccioanar_Boletos() {

        driver.findElement(By.id("div_2020020946_12")).click();
        String strCosto1=driver.findElement(By.xpath("//*[@id='div_2020020946_12']/div[4]/div")).getText();

        String precio[] = strCosto1.split("\n");
        GlobalVals.Dolares1= Double.parseDouble(precio[0].substring(3).trim());
        GlobalVals.Soles1=Double.parseDouble(precio[1].substring(2).trim());

        driver.findElement(By.id("div_2020021921_13")).click();
        String strCosto2=driver.findElement(By.xpath("//*[@id='div_2020021921_13']/div[5]")).getText();

        String precio2[] = strCosto2.split("\n");
        GlobalVals.Dolares2=Double.parseDouble(precio2[0].substring(3).trim());
        GlobalVals.Soles2=Double.parseDouble(precio2[1].substring(2).trim());

 }

    @Then("Selecciona boton Continuar")
    public void selecciona_boton_Continuar() {
        driver.findElement(By.xpath("//*[@id='formTrenSeleccionar']/div/div/input")).click();
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formPasajero1-nomPasajero")));
        boolean status = driver.findElement(By.id("formPasajero1-nomPasajero")).isDisplayed();
        Assert.assertEquals(true ,status);

    }
    @Then("Ingresar Información de Pasajeros")
    public void informacion_pasajeros(){
       driver.findElement(By.id("formPasajero1-nomPasajero")).sendKeys("Luis");
       driver.findElement(By.id("formPasajero1-apePasajero")).sendKeys("Miranda");
       driver.findElement(By.id("formPasajero1-fecNacimiento")).click();
       driver.findElement(By.xpath("//*[@id='calendario_anio']"+"/option[text()='1986']")).click();
       driver.findElement(By.xpath("//*[@id='calendario_mes']"+"/option[text()='MARCH']")).click();
       driver.findElement(By.xpath("//*[contains(@href,'17')]")).click();
       driver.findElement(By.xpath("//*[@id='formPasajero1-idPais']"+"/option[text()='Peru']")).click();
       driver.findElement(By.xpath("//*[@id='formPasajero1-idDocumentoIdentidad']"+"/option[text()='Identification Card']")).click();
       driver.findElement(By.id("formPasajero1-numDocumentoIdentidad")).sendKeys("43738569");

       driver.findElement(By.xpath("//*[@id='formPasajero1-idSexo']"+"/option[text()='Male']")).click();
       driver.findElement(By.id("formPasajero1-numTelefono")).sendKeys("979702185");
       driver.findElement(By.id("formPasajero1-desEmail")).sendKeys("miguel054@gmail.com");
       driver.findElement(By.id("formPasajero1-desEmailConfirmacion")).sendKeys("miguel054@gmail.com");

        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formPasajero1-nomPasajero")));
        driver.findElement(By.xpath("//*[@id='formPasajeroRegistrar']/div[2]/div[1]")).click();

        //----------Pasajero2

        driver.findElement(By.id("formPasajero2-nomPasajero")).sendKeys("Miguel");
        driver.findElement(By.id("formPasajero2-apePasajero")).sendKeys("Miranda");
        driver.findElement(By.id("formPasajero2-fecNacimiento")).click();
        driver.findElement(By.xpath("//*[@id='calendario_anio']"+"/option[text()='1995']")).click();
        driver.findElement(By.xpath("//*[@id='calendario_mes']"+"/option[text()='MARCH']")).click();
        driver.findElement(By.xpath("//*[contains(@href,'26')]")).click();
        driver.findElement(By.xpath("//*[@id='formPasajero2-idPais']"+"/option[text()='Peru']")).click();
        driver.findElement(By.xpath("//*[@id='formPasajero2-idDocumentoIdentidad']"+"/option[text()='Identification Card']")).click();
        driver.findElement(By.id("formPasajero2-numDocumentoIdentidad")).sendKeys("45869578");

        driver.findElement(By.xpath("//*[@id='formPasajero2-idSexo']"+"/option[text()='Male']")).click();
        driver.findElement(By.id("formPasajero2-numTelefono")).sendKeys("979702185");
        driver.findElement(By.id("formPasajero2-desEmail")).sendKeys("miguel054@gmail.com");
        driver.findElement(By.id("formPasajero2-desEmailConfirmacion")).sendKeys("miguel054@gmail.com");


    }
    @Then("Selecciona boton ContinuarPago")
    public void selecciona_boton_ContinuarPago() {
        driver.findElement(By.xpath("//*[@id='enviarPago']")).click();
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("text_form_submit_payment")));
        boolean status = driver.findElement(By.id("text_form_submit_payment")).isDisplayed();
        Assert.assertEquals(true ,status);


    }

    @Then("Validar monto de pago")
    public void validar_monto_de_pago() {


       String totalIdaDol= GlobalVals.Dolares1*2+"0";
       String Actual1 = driver.findElement(By.xpath("//*[@id='compra']/div/div[2]/div[5]/span")).getText();
       Assert.assertEquals(totalIdaDol, Actual1);

       String totalRetDol= GlobalVals.Dolares2*2+"0";
       Assert.assertEquals(totalRetDol, driver.findElement(By.xpath("//*[@id='compra']/div/div[3]/div[5]/span")).getText());

        String totalDol=GlobalVals.Dolares1*2+GlobalVals.Dolares2*2+"0";
        String totalSol=GlobalVals.Soles1*2+GlobalVals.Soles2*2+"";

        String totalDol2=driver.findElement(By.xpath("//*[@id='compra']/div/div[4]/div[1]/div[1]/span[2]")).getText().substring(4);
        String totalSol2=driver.findElement(By.xpath("//*[@id='compra']/div/div[4]/div[1]/div[2]/span")).getText().substring(3);

        Assert.assertEquals(totalDol, totalDol2);
        Assert.assertEquals(totalSol, totalSol2);


    }

    @Then("Cerrar browser")
    public void cerrar_browser() {

        driver.quit();

   }

}
